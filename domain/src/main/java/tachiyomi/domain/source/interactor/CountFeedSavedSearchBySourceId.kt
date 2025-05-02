package faxyomi.domain.source.interactor

import faxyomi.domain.source.repository.FeedSavedSearchRepository

class CountFeedSavedSearchBySourceId(
    private val feedSavedSearchRepository: FeedSavedSearchRepository,
) {

    suspend fun await(sourceId: Long): Long {
        return feedSavedSearchRepository.countBySourceId(sourceId)
    }
}
