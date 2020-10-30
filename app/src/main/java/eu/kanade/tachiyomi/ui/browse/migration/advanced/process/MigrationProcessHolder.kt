package eu.kanade.tachiyomi.ui.browse.migration.advanced.process

import android.view.View
import android.widget.PopupMenu
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.bumptech.glide.load.engine.DiskCacheStrategy
import eu.kanade.tachiyomi.R
import eu.kanade.tachiyomi.data.database.DatabaseHelper
import eu.kanade.tachiyomi.data.database.models.Manga
import eu.kanade.tachiyomi.data.glide.GlideApp
import eu.kanade.tachiyomi.data.glide.toMangaThumbnail
import eu.kanade.tachiyomi.databinding.MigrationMangaCardBinding
import eu.kanade.tachiyomi.databinding.MigrationProcessItemBinding
import eu.kanade.tachiyomi.source.Source
import eu.kanade.tachiyomi.source.SourceManager
import eu.kanade.tachiyomi.ui.base.controller.withFadeTransaction
import eu.kanade.tachiyomi.ui.base.holder.BaseFlexibleViewHolder
import eu.kanade.tachiyomi.ui.manga.MangaController
import eu.kanade.tachiyomi.util.lang.launchUI
import eu.kanade.tachiyomi.util.system.getResourceColor
import eu.kanade.tachiyomi.util.view.setVectorCompat
import exh.MERGED_SOURCE_ID
import exh.util.await
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import reactivecircus.flowbinding.android.view.clicks
import uy.kohesive.injekt.api.get
import uy.kohesive.injekt.injectLazy
import java.text.DecimalFormat

class MigrationProcessHolder(
    private val view: View,
    private val adapter: MigrationProcessAdapter
) : BaseFlexibleViewHolder(view, adapter) {
    private val db: DatabaseHelper by injectLazy()
    private val sourceManager: SourceManager by injectLazy()

    private var item: MigrationProcessItem? = null
    private val scope = CoroutineScope(Job() + Dispatchers.Main)
    private val binding = MigrationProcessItemBinding.bind(view)

    init {
        // We need to post a Runnable to show the popup to make sure that the PopupMenu is
        // correctly positioned. The reason being that the view may change position before the
        // PopupMenu is shown.
        binding.migrationMenu.setOnClickListener { it.post { showPopupMenu(it) } }
        binding.skipManga.setOnClickListener { it.post { adapter.removeManga(bindingAdapterPosition) } }
    }

    fun bind(item: MigrationProcessItem) {
        this.item = item
        launchUI {
            val manga = item.manga.manga()
            val source = item.manga.mangaSource()

            binding.migrationMenu.setVectorCompat(
                R.drawable.ic_more_vert_24dp,
                view.context
                    .getResourceColor(R.attr.colorOnPrimary)
            )
            binding.skipManga.setVectorCompat(
                R.drawable.ic_close_24dp,
                view.context.getResourceColor(
                    R
                        .attr.colorOnPrimary
                )
            )
            binding.migrationMenu.isInvisible = true
            binding.skipManga.isVisible = true
            binding.migrationMangaCardTo.resetManga()
            if (manga != null) {
                withContext(Dispatchers.Main) {
                    binding.migrationMangaCardFrom.attachManga(manga, source)
                    binding.migrationMangaCardFrom.root.clicks()
                        .onEach {
                            adapter.controller.router.pushController(
                                MangaController(
                                    manga,
                                    true
                                ).withFadeTransaction()
                            )
                        }.launchIn(scope)
                }

                /*launchUI {
                    item.manga.progress.asFlow().collect { (max, progress) ->
                        withContext(Dispatchers.Main) {
                            migration_manga_card_to.search_progress.let { progressBar ->
                                progressBar.max = max
                                progressBar.progress = progress
                            }
                        }
                    }
                }*/

                val searchResult = item.manga.searchResult.get()?.let {
                    db.getManga(it).executeAsBlocking()
                }
                val resultSource = searchResult?.source?.let {
                    sourceManager.get(it)
                }
                withContext(Dispatchers.Main) {
                    if (item.manga.mangaId != this@MigrationProcessHolder.item?.manga?.mangaId ||
                        item.manga.migrationStatus == MigrationStatus.RUNNING
                    ) {
                        return@withContext
                    }
                    if (searchResult != null && resultSource != null) {
                        binding.migrationMangaCardTo.attachManga(searchResult, resultSource)
                        binding.migrationMangaCardTo.root.clicks()
                            .onEach {
                                adapter.controller.router.pushController(
                                    MangaController(
                                        searchResult,
                                        true
                                    ).withFadeTransaction()
                                )
                            }.launchIn(scope)
                    } else {
                        binding.migrationMangaCardTo.loadingGroup.isVisible = false
                        binding.migrationMangaCardTo.title.text = view.context.applicationContext
                            .getString(R.string.no_alternatives_found)
                    }
                    binding.migrationMenu.isVisible = true
                    binding.skipManga.isVisible = false
                    adapter.sourceFinished()
                }
            }
        }
    }

    private fun MigrationMangaCardBinding.resetManga() {
        loadingGroup.isVisible = true
        thumbnail.setImageDrawable(null)
        title.text = ""
        mangaSourceLabel.text = ""
        mangaChapters.text = ""
        mangaChapters.isVisible = false
        mangaLastChapterLabel.text = ""
    }

    private suspend fun MigrationMangaCardBinding.attachManga(manga: Manga, source: Source) {
        loadingGroup.isVisible = false
        GlideApp.with(view.context.applicationContext)
            .load(manga.toMangaThumbnail())
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .centerCrop()
            .dontAnimate()
            .into(thumbnail)

        title.text = if (manga.title.isBlank()) {
            view.context.getString(R.string.unknown)
        } else {
            manga.originalTitle
        }

        gradient.isVisible = true
        mangaSourceLabel.text = if (source.id == MERGED_SOURCE_ID) {
            db.getMergedMangaReferences(manga.id!!).await().map {
                sourceManager.getOrStub(it.mangaSourceId).toString()
            }.distinct().joinToString()
        } else {
            source.toString()
        }

        val chapters = db.getChapters(manga).executeAsBlocking()
        mangaChapters.isVisible = true
        mangaChapters.text = chapters.size.toString()
        val latestChapter = chapters.maxByOrNull { it.chapter_number }?.chapter_number ?: -1f

        if (latestChapter > 0f) {
            mangaLastChapterLabel.text = root.context.getString(
                R.string.latest_,
                DecimalFormat("#.#").format(latestChapter)
            )
        } else {
            mangaLastChapterLabel.text = root.context.getString(
                R.string.latest_,
                root.context.getString(R.string.unknown)
            )
        }
    }

    private fun showPopupMenu(view: View) {
        val item = adapter.getItem(bindingAdapterPosition) ?: return

        // Create a PopupMenu, giving it the clicked view for an anchor
        val popup = PopupMenu(view.context, view)

        // Inflate our menu resource into the PopupMenu's Menu
        popup.menuInflater.inflate(R.menu.migration_single, popup.menu)

        val mangas = item.manga

        popup.menu.findItem(R.id.action_search_manually).isVisible = true
        // Hide download and show delete if the chapter is downloaded
        if (mangas.searchResult.content != null) {
            popup.menu.findItem(R.id.action_migrate_now).isVisible = true
            popup.menu.findItem(R.id.action_copy_now).isVisible = true
        }

        // Set a listener so we are notified if a menu item is clicked
        popup.setOnMenuItemClickListener { menuItem ->
            adapter.menuItemListener.onMenuItemClick(bindingAdapterPosition, menuItem)
            true
        }

        // Finally show the PopupMenu
        popup.show()
    }
}
