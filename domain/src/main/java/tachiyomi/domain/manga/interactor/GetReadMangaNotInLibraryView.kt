package faxyomi.domain.manga.interactor

import faxyomi.domain.library.model.LibraryManga
import faxyomi.domain.manga.repository.MangaRepository

class GetReadMangaNotInLibraryView(
    private val mangaRepository: MangaRepository,
) {

    suspend fun await(): List<LibraryManga> {
        return mangaRepository.getReadMangaNotInLibraryView()
    }
}
