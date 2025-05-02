package mihon.domain.upcoming.interactor

import eu.fiax.faxyomi.source.model.SManga
import kotlinx.coroutines.flow.Flow
import faxyomi.domain.manga.model.Manga
import faxyomi.domain.manga.repository.MangaRepository

class GetUpcomingManga(
    private val mangaRepository: MangaRepository,
) {

    private val includedStatuses = setOf(
        SManga.ONGOING.toLong(),
        SManga.PUBLISHING_FINISHED.toLong(),
    )

    suspend fun subscribe(): Flow<List<Manga>> {
        return mangaRepository.getUpcomingManga(includedStatuses)
    }
}
