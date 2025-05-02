package faxyomi.domain.manga.interactor

import faxyomi.domain.manga.repository.CustomMangaRepository

class GetCustomMangaInfo(
    private val customMangaRepository: CustomMangaRepository,
) {

    fun get(mangaId: Long) = customMangaRepository.get(mangaId)
}
