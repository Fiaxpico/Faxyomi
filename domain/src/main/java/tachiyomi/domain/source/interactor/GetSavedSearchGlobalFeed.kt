package faxyomi.domain.source.interactor

import faxyomi.domain.source.model.SavedSearch
import faxyomi.domain.source.repository.FeedSavedSearchRepository

class GetSavedSearchGlobalFeed(
    private val feedSavedSearchRepository: FeedSavedSearchRepository,
) {

    suspend fun await(): List<SavedSearch> {
        return feedSavedSearchRepository.getGlobalFeedSavedSearch()
    }
}
