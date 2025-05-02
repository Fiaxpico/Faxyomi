package faxyomi.domain.chapter.interactor

import faxyomi.domain.chapter.repository.ChapterRepository

class DeleteChapters(
    private val chapterRepository: ChapterRepository,
) {

    suspend fun await(chapters: List<Long>) {
        chapterRepository.removeChaptersWithIds(chapters)
    }
}
