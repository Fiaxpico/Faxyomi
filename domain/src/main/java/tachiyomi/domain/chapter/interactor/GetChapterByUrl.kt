package faxyomi.domain.chapter.interactor

import logcat.LogPriority
import faxyomi.core.common.util.system.logcat
import faxyomi.domain.chapter.model.Chapter
import faxyomi.domain.chapter.repository.ChapterRepository

class GetChapterByUrl(
    private val chapterRepository: ChapterRepository,
) {

    suspend fun await(url: String): List<Chapter> {
        return try {
            chapterRepository.getChapterByUrl(url)
        } catch (e: Exception) {
            logcat(LogPriority.ERROR, e)
            emptyList()
        }
    }
}
