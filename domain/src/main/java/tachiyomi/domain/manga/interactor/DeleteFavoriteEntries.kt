package faxyomi.domain.manga.interactor

import faxyomi.domain.manga.repository.FavoritesEntryRepository

class DeleteFavoriteEntries(
    private val favoriteEntryRepository: FavoritesEntryRepository,
) {

    suspend fun await() {
        return favoriteEntryRepository.deleteAll()
    }
}
