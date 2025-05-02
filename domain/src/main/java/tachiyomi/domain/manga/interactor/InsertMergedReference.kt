package faxyomi.domain.manga.interactor

import faxyomi.domain.manga.model.MergedMangaReference
import faxyomi.domain.manga.repository.MangaMergeRepository

class InsertMergedReference(
    private val mangaMergedRepository: MangaMergeRepository,
) {

    suspend fun await(reference: MergedMangaReference): Long? {
        return mangaMergedRepository.insert(reference)
    }

    suspend fun awaitAll(references: List<MergedMangaReference>) {
        mangaMergedRepository.insertAll(references)
    }
}
