package faxyomi.domain.manga.interactor

import faxyomi.domain.manga.model.FavoriteEntryAlternative
import faxyomi.domain.manga.repository.FavoritesEntryRepository

class InsertFavoriteEntryAlternative(
    private val favoriteEntryRepository: FavoritesEntryRepository,
) {

    suspend fun await(entry: FavoriteEntryAlternative) {
        return favoriteEntryRepository.addAlternative(entry)
    }
}
