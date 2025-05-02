package faxyomi.domain.source.interactor

import faxyomi.domain.source.model.SavedSearch
import faxyomi.domain.source.repository.FeedSavedSearchRepository

class GetSavedSearchBySourceIdFeed(
    private val feedSavedSearchRepository: FeedSavedSearchRepository,
) {

    suspend fun await(sourceId: Long): List<SavedSearch> {
        return feedSavedSearchRepository.getBySourceIdFeedSavedSearch(sourceId)
    }
}
