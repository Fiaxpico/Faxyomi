package faxyomi.domain.history.interactor

import faxyomi.domain.history.model.HistoryUpdate
import faxyomi.domain.history.repository.HistoryRepository

class UpsertHistory(
    private val historyRepository: HistoryRepository,
) {

    suspend fun await(historyUpdate: HistoryUpdate) {
        historyRepository.upsertHistory(historyUpdate)
    }

    // SY -->
    suspend fun awaitAll(historyUpdates: List<HistoryUpdate>) {
        historyRepository.upsertHistory(historyUpdates)
    }
    // SY <--
}
