package eu.fiax.domain.download.interactor

import eu.fiax.faxyomi.data.download.DownloadManager
import faxyomi.core.common.util.lang.withNonCancellableContext
import faxyomi.domain.chapter.model.Chapter
import faxyomi.domain.manga.model.Manga
import faxyomi.domain.source.service.SourceManager

class DeleteDownload(
    private val sourceManager: SourceManager,
    private val downloadManager: DownloadManager,
) {

    suspend fun awaitAll(manga: Manga, vararg chapters: Chapter) = withNonCancellableContext {
        sourceManager.get(manga.source)?.let { source ->
            downloadManager.deleteChapters(chapters.toList(), manga, source)
        }
    }
}
