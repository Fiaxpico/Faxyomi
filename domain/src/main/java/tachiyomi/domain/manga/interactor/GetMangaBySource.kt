package faxyomi.domain.manga.interactor

import faxyomi.domain.manga.model.Manga
import faxyomi.domain.manga.repository.MangaRepository

class GetMangaBySource(
    private val mangaRepository: MangaRepository,
) {

    suspend fun await(sourceId: Long): List<Manga> {
        return mangaRepository.getMangaBySourceId(sourceId)
    }
}
