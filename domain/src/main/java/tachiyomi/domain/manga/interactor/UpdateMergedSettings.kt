package faxyomi.domain.manga.interactor

import faxyomi.domain.manga.model.MergeMangaSettingsUpdate
import faxyomi.domain.manga.repository.MangaMergeRepository

class UpdateMergedSettings(
    private val mangaMergeRepository: MangaMergeRepository,
) {

    suspend fun await(mergeUpdate: MergeMangaSettingsUpdate): Boolean {
        return mangaMergeRepository.updateSettings(mergeUpdate)
    }

    suspend fun awaitAll(values: List<MergeMangaSettingsUpdate>): Boolean {
        return mangaMergeRepository.updateAllSettings(values)
    }
}
