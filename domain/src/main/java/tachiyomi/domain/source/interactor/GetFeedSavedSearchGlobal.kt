package faxyomi.domain.source.interactor

import kotlinx.coroutines.flow.Flow
import faxyomi.domain.source.model.FeedSavedSearch
import faxyomi.domain.source.repository.FeedSavedSearchRepository

class GetFeedSavedSearchGlobal(
    private val feedSavedSearchRepository: FeedSavedSearchRepository,
) {

    suspend fun await(): List<FeedSavedSearch> {
        return feedSavedSearchRepository.getGlobal()
    }

    fun subscribe(): Flow<List<FeedSavedSearch>> {
        return feedSavedSearchRepository.getGlobalAsFlow()
    }
}
