package faxyomi.domain.manga.interactor

import faxyomi.domain.manga.repository.MangaMergeRepository

class DeleteMergeById(
    private val mangaMergeRepository: MangaMergeRepository,
) {

    suspend fun await(id: Long) {
        return mangaMergeRepository.deleteById(id)
    }
}
