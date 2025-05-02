package faxyomi.domain.manga.interactor

import faxyomi.domain.manga.model.FavoriteEntry
import faxyomi.domain.manga.repository.FavoritesEntryRepository

class GetFavoriteEntries(
    private val favoriteEntryRepository: FavoritesEntryRepository,
) {

    suspend fun await(): List<FavoriteEntry> {
        return favoriteEntryRepository.selectAll()
    }
}
