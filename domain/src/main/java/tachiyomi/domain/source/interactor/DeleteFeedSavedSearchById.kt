package faxyomi.domain.source.interactor

import faxyomi.domain.source.repository.FeedSavedSearchRepository

class DeleteFeedSavedSearchById(
    private val feedSavedSearchRepository: FeedSavedSearchRepository,
) {

    suspend fun await(feedSavedSearchId: Long) {
        return feedSavedSearchRepository.delete(feedSavedSearchId)
    }
}
