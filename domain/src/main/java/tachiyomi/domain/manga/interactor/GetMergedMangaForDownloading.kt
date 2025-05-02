package faxyomi.domain.manga.interactor

import faxyomi.domain.manga.model.Manga
import faxyomi.domain.manga.repository.MangaMergeRepository

class GetMergedMangaForDownloading(
    private val mangaMergeRepository: MangaMergeRepository,
) {

    suspend fun await(mergeId: Long): List<Manga> {
        return mangaMergeRepository.getMergeMangaForDownloading(mergeId)
    }
}
