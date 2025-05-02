package faxyomi.domain.manga.repository

import faxyomi.domain.manga.model.FavoriteEntry
import faxyomi.domain.manga.model.FavoriteEntryAlternative

interface FavoritesEntryRepository {
    suspend fun deleteAll()

    suspend fun insertAll(favoriteEntries: List<FavoriteEntry>)

    suspend fun selectAll(): List<FavoriteEntry>

    suspend fun addAlternative(favoriteEntryAlternative: FavoriteEntryAlternative)
}
