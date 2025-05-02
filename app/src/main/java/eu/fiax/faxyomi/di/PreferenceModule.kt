package eu.fiax.faxyomi.di

import android.app.Application
import eu.fiax.domain.base.BasePreferences
import eu.fiax.domain.source.service.SourcePreferences
import eu.fiax.domain.sync.SyncPreferences
import eu.fiax.domain.track.service.TrackPreferences
import eu.fiax.domain.ui.UiPreferences
import eu.fiax.faxyomi.core.security.PrivacyPreferences
import eu.fiax.faxyomi.core.security.SecurityPreferences
import eu.fiax.faxyomi.network.NetworkPreferences
import eu.fiax.faxyomi.ui.reader.setting.ReaderPreferences
import eu.fiax.faxyomi.util.system.isDevFlavor
import faxyomi.core.common.preference.AndroidPreferenceStore
import faxyomi.core.common.preference.PreferenceStore
import faxyomi.core.common.storage.AndroidStorageFolderProvider
import faxyomi.domain.backup.service.BackupPreferences
import faxyomi.domain.download.service.DownloadPreferences
import faxyomi.domain.library.service.LibraryPreferences
import faxyomi.domain.storage.service.StoragePreferences
import uy.kohesive.injekt.api.InjektRegistrar

class PreferenceModule(val app: Application) : InjektModule {

    override fun InjektRegistrar.registerInjectables() {
        addSingletonFactory<PreferenceStore> {
            AndroidPreferenceStore(app)
        }
        addSingletonFactory {
            NetworkPreferences(
                preferenceStore = get(),
                verboseLogging = isDevFlavor,
            )
        }
        addSingletonFactory {
            SourcePreferences(get())
        }
        addSingletonFactory {
            SecurityPreferences(get())
        }
        addSingletonFactory {
            PrivacyPreferences(get())
        }
        addSingletonFactory {
            LibraryPreferences(get())
        }
        addSingletonFactory {
            ReaderPreferences(get())
        }
        addSingletonFactory {
            TrackPreferences(get())
        }
        addSingletonFactory {
            DownloadPreferences(get())
        }
        addSingletonFactory {
            BackupPreferences(get())
        }
        addSingletonFactory {
            StoragePreferences(
                folderProvider = get<AndroidStorageFolderProvider>(),
                preferenceStore = get(),
            )
        }
        addSingletonFactory {
            UiPreferences(get())
        }
        addSingletonFactory {
            BasePreferences(app, get())
        }

        addSingletonFactory {
            SyncPreferences(get())
        }
    }
}
