package faxyomi.domain.source.repository

import androidx.paging.PagingSource
import eu.fiax.faxyomi.source.model.FilterList
import eu.fiax.faxyomi.source.model.SManga
import exh.metadata.metadata.RaisedSearchMetadata
import kotlinx.coroutines.flow.Flow
import faxyomi.domain.source.model.Source
import faxyomi.domain.source.model.SourceWithCount

typealias SourcePagingSourceType = PagingSource<Long, /*SY --> */ Pair<SManga, RaisedSearchMetadata?>/*SY <-- */>

interface SourceRepository {

    fun getSources(): Flow<List<Source>>

    fun getOnlineSources(): Flow<List<Source>>

    fun getSourcesWithFavoriteCount(): Flow<List<Pair<Source, Long>>>

    fun getSourcesWithNonLibraryManga(): Flow<List<SourceWithCount>>

    fun search(sourceId: Long, query: String, filterList: FilterList): SourcePagingSourceType

    fun getPopular(sourceId: Long): SourcePagingSourceType

    fun getLatest(sourceId: Long): SourcePagingSourceType
}
