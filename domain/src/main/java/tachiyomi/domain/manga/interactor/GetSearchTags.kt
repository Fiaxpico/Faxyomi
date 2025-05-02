package faxyomi.domain.manga.interactor

import exh.metadata.sql.models.SearchTag
import faxyomi.domain.manga.repository.MangaMetadataRepository

class GetSearchTags(
    private val mangaMetadataRepository: MangaMetadataRepository,
) {

    suspend fun await(mangaId: Long): List<SearchTag> {
        return mangaMetadataRepository.getTagsById(mangaId)
    }
}
