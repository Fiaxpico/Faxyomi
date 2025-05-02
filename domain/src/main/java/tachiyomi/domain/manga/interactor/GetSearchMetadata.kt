package faxyomi.domain.manga.interactor

import exh.metadata.sql.models.SearchMetadata
import faxyomi.domain.manga.repository.MangaMetadataRepository

class GetSearchMetadata(
    private val mangaMetadataRepository: MangaMetadataRepository,
) {

    suspend fun await(mangaId: Long): SearchMetadata? {
        return mangaMetadataRepository.getMetadataById(mangaId)
    }

    suspend fun await(): List<SearchMetadata> {
        return mangaMetadataRepository.getSearchMetadata()
    }
}
