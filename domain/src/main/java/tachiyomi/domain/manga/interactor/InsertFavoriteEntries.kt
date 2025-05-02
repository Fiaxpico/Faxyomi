package faxyomi.domain.manga.interactor

import faxyomi.domain.manga.model.FavoriteEntry
import faxyomi.domain.manga.repository.FavoritesEntryRepository

class InsertFavoriteEntries(
    private val favoriteEntryRepository: FavoritesEntryRepository,
) {

    suspend fun await(entries: List<FavoriteEntry>) {
        return favoriteEntryRepository.insertAll(entries)
    }
}
