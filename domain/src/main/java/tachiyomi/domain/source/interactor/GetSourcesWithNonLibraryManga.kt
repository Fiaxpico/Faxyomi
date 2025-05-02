package faxyomi.domain.source.interactor

import kotlinx.coroutines.flow.Flow
import faxyomi.domain.source.model.SourceWithCount
import faxyomi.domain.source.repository.SourceRepository

class GetSourcesWithNonLibraryManga(
    private val repository: SourceRepository,
) {

    fun subscribe(): Flow<List<SourceWithCount>> {
        return repository.getSourcesWithNonLibraryManga()
    }
}
