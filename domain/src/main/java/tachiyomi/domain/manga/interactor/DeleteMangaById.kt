package faxyomi.domain.manga.interactor

import faxyomi.domain.manga.repository.MangaRepository

class DeleteMangaById(
    private val mangaRepository: MangaRepository,
) {

    suspend fun await(id: Long) {
        return mangaRepository.deleteManga(id)
    }
}
