package faxyomi.domain.history.interactor

import faxyomi.domain.history.model.History
import faxyomi.domain.history.repository.HistoryRepository

class GetHistoryByMangaId(
    private val repository: HistoryRepository,
) {

    suspend fun await(mangaId: Long): List<History> {
        return repository.getByMangaId(mangaId)
    }
}
