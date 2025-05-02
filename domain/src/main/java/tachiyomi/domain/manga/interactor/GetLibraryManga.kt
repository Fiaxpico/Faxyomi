package faxyomi.domain.manga.interactor

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.retry
import logcat.LogPriority
import faxyomi.core.common.util.system.logcat
import faxyomi.domain.library.model.LibraryManga
import faxyomi.domain.manga.repository.MangaRepository
import kotlin.time.Duration.Companion.seconds

class GetLibraryManga(
    private val mangaRepository: MangaRepository,
) {

    suspend fun await(): List<LibraryManga> {
        return mangaRepository.getLibraryManga()
    }

    fun subscribe(): Flow<List<LibraryManga>> {
        return mangaRepository.getLibraryMangaAsFlow()
            .retry {
                if (it is NullPointerException) {
                    delay(0.5.seconds)
                    true
                } else {
                    false
                }
            }.catch {
                this@GetLibraryManga.logcat(LogPriority.ERROR, it)
            }
    }
}
