package faxyomi.domain.manga.interactor

import faxyomi.domain.manga.repository.MangaMergeRepository

class DeleteByMergeId(
    private val mangaMergeRepository: MangaMergeRepository,
) {

    suspend fun await(id: Long) {
        return mangaMergeRepository.deleteByMergeId(id)
    }
}
