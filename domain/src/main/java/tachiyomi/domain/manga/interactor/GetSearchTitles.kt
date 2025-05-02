package faxyomi.domain.manga.interactor

import exh.metadata.sql.models.SearchTitle
import faxyomi.domain.manga.repository.MangaMetadataRepository

class GetSearchTitles(
    private val mangaMetadataRepository: MangaMetadataRepository,
) {

    suspend fun await(mangaId: Long): List<SearchTitle> {
        return mangaMetadataRepository.getTitlesById(mangaId)
    }
}
