package faxyomi.domain.source.interactor

import faxyomi.domain.source.repository.SavedSearchRepository

class DeleteSavedSearchById(
    private val savedSearchRepository: SavedSearchRepository,
) {

    suspend fun await(savedSearchId: Long) {
        return savedSearchRepository.delete(savedSearchId)
    }
}
