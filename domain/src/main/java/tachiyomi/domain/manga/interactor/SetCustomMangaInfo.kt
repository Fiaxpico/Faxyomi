package faxyomi.domain.manga.interactor

import faxyomi.domain.manga.model.CustomMangaInfo
import faxyomi.domain.manga.repository.CustomMangaRepository

class SetCustomMangaInfo(
    private val customMangaRepository: CustomMangaRepository,
) {

    fun set(mangaInfo: CustomMangaInfo) = customMangaRepository.set(mangaInfo)
}
