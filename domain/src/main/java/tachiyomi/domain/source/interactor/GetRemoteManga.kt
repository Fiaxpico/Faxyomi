package faxyomi.domain.source.interactor

import eu.fiax.faxyomi.source.model.FilterList
import faxyomi.domain.source.repository.SourcePagingSourceType
import faxyomi.domain.source.repository.SourceRepository

class GetRemoteManga(
    private val repository: SourceRepository,
) {

    fun subscribe(sourceId: Long, query: String, filterList: FilterList): SourcePagingSourceType {
        return when (query) {
            QUERY_POPULAR -> repository.getPopular(sourceId)
            QUERY_LATEST -> repository.getLatest(sourceId)
            else -> repository.search(sourceId, query, filterList)
        }
    }

    companion object {
        const val QUERY_POPULAR = "eu.fiax.domain.source.interactor.POPULAR"
        const val QUERY_LATEST = "eu.fiax.domain.source.interactor.LATEST"
    }
}
