package eu.fiax.faxyomi.di

import android.app.Application
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import eu.fiax.domain.track.store.DelayedTrackingStore
import eu.fiax.BuildConfig
import eu.fiax.faxyomi.core.security.SecurityPreferences
import eu.fiax.faxyomi.data.cache.ChapterCache
import eu.fiax.faxyomi.data.cache.CoverCache
import eu.fiax.faxyomi.data.cache.PagePreviewCache
import eu.fiax.faxyomi.data.download.DownloadCache
import eu.fiax.faxyomi.data.download.DownloadManager
import eu.fiax.faxyomi.data.download.DownloadProvider
import eu.fiax.faxyomi.data.saver.ImageSaver
import eu.fiax.faxyomi.data.sync.service.GoogleDriveService
import eu.fiax.faxyomi.data.track.TrackerManager
import eu.fiax.faxyomi.extension.ExtensionManager
import eu.fiax.faxyomi.network.JavaScriptEngine
import eu.fiax.faxyomi.network.NetworkHelper
import eu.fiax.faxyomi.source.AndroidSourceManager
import eu.fiax.faxyomi.util.storage.CbzCrypto
import exh.eh.EHentaiUpdateHelper
import io.requery.android.database.sqlite.RequerySQLiteOpenHelperFactory
import kotlinx.serialization.json.Json
import kotlinx.serialization.protobuf.ProtoBuf
import net.zetetic.database.sqlcipher.SupportOpenHelperFactory
import nl.adaptivity.xmlutil.XmlDeclMode
import nl.adaptivity.xmlutil.core.XmlVersion
import nl.adaptivity.xmlutil.serialization.XML
import faxyomi.core.common.storage.AndroidStorageFolderProvider
import faxyomi.core.common.storage.UniFileTempFileManager
import faxyomi.data.AndroidDatabaseHandler
import faxyomi.data.Database
import faxyomi.data.DatabaseHandler
import faxyomi.data.DateColumnAdapter
import tachiyomi.data.History
import tachiyomi.data.Mangas
import faxyomi.data.StringListColumnAdapter
import faxyomi.data.UpdateStrategyColumnAdapter
import faxyomi.domain.manga.interactor.GetCustomMangaInfo
import faxyomi.domain.source.service.SourceManager
import faxyomi.domain.storage.service.StorageManager
import faxyomi.source.local.image.LocalCoverManager
import faxyomi.source.local.io.LocalSourceFileSystem
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.InjektRegistrar
import uy.kohesive.injekt.api.get
import uy.kohesive.injekt.injectLazy

// SY -->
private const val LEGACY_DATABASE_NAME = "faxyomi.db"
// SY <--

class AppModule(val app: Application) : InjektModule {
    // SY -->
    private val securityPreferences: SecurityPreferences by injectLazy()
    // SY <--

    override fun InjektRegistrar.registerInjectables() {
        addSingleton(app)

        addSingletonFactory<SqlDriver> {
            // SY -->
            if (securityPreferences.encryptDatabase().get()) {
                System.loadLibrary("sqlcipher")
            }

            // SY <--
            AndroidSqliteDriver(
                schema = Database.Schema,
                context = app,
                // SY -->
                name = if (securityPreferences.encryptDatabase().get()) {
                    CbzCrypto.DATABASE_NAME
                } else {
                    LEGACY_DATABASE_NAME
                },
                factory = if (securityPreferences.encryptDatabase().get()) {
                    SupportOpenHelperFactory(CbzCrypto.getDecryptedPasswordSql(), null, false, 25)
                } else if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    // Support database inspector in Android Studio
                    FrameworkSQLiteOpenHelperFactory()
                } else {
                    RequerySQLiteOpenHelperFactory()
                },
                // SY <--
                callback = object : AndroidSqliteDriver.Callback(Database.Schema) {
                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        setPragma(db, "foreign_keys = ON")
                        setPragma(db, "journal_mode = WAL")
                        setPragma(db, "synchronous = NORMAL")
                    }
                    private fun setPragma(db: SupportSQLiteDatabase, pragma: String) {
                        val cursor = db.query("PRAGMA $pragma")
                        cursor.moveToFirst()
                        cursor.close()
                    }
                },
            )
        }
        addSingletonFactory {
            Database(
                driver = get(),
                historyAdapter = History.Adapter(
                    last_readAdapter = DateColumnAdapter,
                ),
                mangasAdapter = Mangas.Adapter(
                    genreAdapter = StringListColumnAdapter,
                    update_strategyAdapter = UpdateStrategyColumnAdapter,
                ),
            )
        }
        addSingletonFactory<DatabaseHandler> { AndroidDatabaseHandler(get(), get()) }

        addSingletonFactory {
            Json {
                ignoreUnknownKeys = true
                explicitNulls = false
            }
        }
        addSingletonFactory {
            XML {
                defaultPolicy {
                    ignoreUnknownChildren()
                }
                autoPolymorphic = true
                xmlDeclMode = XmlDeclMode.Charset
                indent = 2
                xmlVersion = XmlVersion.XML10
            }
        }
        addSingletonFactory<ProtoBuf> {
            ProtoBuf
        }

        addSingletonFactory { UniFileTempFileManager(app) }

        addSingletonFactory { ChapterCache(app, get(), get()) }
        addSingletonFactory { CoverCache(app) }

        addSingletonFactory { NetworkHelper(app, get(), BuildConfig.DEBUG) }
        addSingletonFactory { JavaScriptEngine(app) }

        addSingletonFactory<SourceManager> { AndroidSourceManager(app, get(), get()) }
        addSingletonFactory { ExtensionManager(app) }

        addSingletonFactory { DownloadProvider(app) }
        addSingletonFactory { DownloadManager(app) }
        addSingletonFactory { DownloadCache(app) }

        addSingletonFactory { TrackerManager() }
        addSingletonFactory { DelayedTrackingStore(app) }

        addSingletonFactory { ImageSaver(app) }

        addSingletonFactory { AndroidStorageFolderProvider(app) }
        addSingletonFactory { LocalSourceFileSystem(get()) }
        addSingletonFactory { LocalCoverManager(app, get()) }
        addSingletonFactory { StorageManager(app, get()) }

        // SY -->
        addSingletonFactory { EHentaiUpdateHelper(app) }

        addSingletonFactory { PagePreviewCache(app) }

        addSingletonFactory { GoogleDriveService(app) }
        // SY <--
    }
}

fun initExpensiveComponents(app: Application) {
    // Asynchronously init expensive components for a faster cold start
    ContextCompat.getMainExecutor(app).execute {
        Injekt.get<NetworkHelper>()

        Injekt.get<SourceManager>()

        Injekt.get<Database>()

        Injekt.get<DownloadManager>()

        // SY -->
        Injekt.get<GetCustomMangaInfo>()
        // SY <--
    }
}
