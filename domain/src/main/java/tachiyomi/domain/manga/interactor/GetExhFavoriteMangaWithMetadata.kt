package faxyomi.domain.manga.interactor

import faxyomi.domain.manga.model.Manga
import faxyomi.domain.manga.repository.MangaMetadataRepository

class GetExhFavoriteMangaWithMetadata(
    private val mangaMetadataRepository: MangaMetadataRepository,
) {

    suspend fun await(): List<Manga> {
        return mangaMetadataRepository.getExhFavoriteMangaWithMetadata()
    }
}
