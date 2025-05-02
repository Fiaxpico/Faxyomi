package faxyomi.domain.source.interactor

import faxyomi.domain.source.repository.FeedSavedSearchRepository

class CountFeedSavedSearchGlobal(
    private val feedSavedSearchRepository: FeedSavedSearchRepository,
) {

    suspend fun await(): Long {
        return feedSavedSearchRepository.countGlobal()
    }
}
