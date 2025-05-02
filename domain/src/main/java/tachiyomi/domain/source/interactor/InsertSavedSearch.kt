package faxyomi.domain.source.interactor

import logcat.LogPriority
import logcat.asLog
import faxyomi.core.common.util.system.logcat
import faxyomi.domain.source.model.SavedSearch
import faxyomi.domain.source.repository.SavedSearchRepository

class InsertSavedSearch(
    private val savedSearchRepository: SavedSearchRepository,
) {

    suspend fun await(savedSearch: SavedSearch): Long? {
        return try {
            savedSearchRepository.insert(savedSearch)
        } catch (e: Exception) {
            logcat(LogPriority.ERROR) { e.asLog() }
            null
        }
    }

    suspend fun awaitAll(savedSearch: List<SavedSearch>) {
        try {
            savedSearchRepository.insertAll(savedSearch)
        } catch (e: Exception) {
            logcat(LogPriority.ERROR) { e.asLog() }
        }
    }
}
