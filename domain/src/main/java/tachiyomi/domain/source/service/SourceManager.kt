package faxyomi.domain.source.service

import eu.fiax.faxyomi.source.CatalogueSource
import eu.fiax.faxyomi.source.Source
import eu.fiax.faxyomi.source.online.HttpSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import faxyomi.domain.source.model.StubSource

interface SourceManager {

    val catalogueSources: Flow<List<CatalogueSource>>

    fun get(sourceKey: Long): Source?

    fun getOrStub(sourceKey: Long): Source

    fun getOnlineSources(): List<HttpSource>

    fun getCatalogueSources(): List<CatalogueSource>

    // SY -->
    val isInitialized: StateFlow<Boolean>

    fun getVisibleOnlineSources(): List<HttpSource>

    fun getVisibleCatalogueSources(): List<CatalogueSource>
    // SY <--

    fun getStubSources(): List<StubSource>
}
