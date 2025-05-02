package eu.fiax.faxyomi.source.online

import eu.fiax.faxyomi.source.CatalogueSource
import eu.fiax.faxyomi.source.model.MangasPage
import eu.fiax.faxyomi.source.model.SManga
import exh.metadata.metadata.RaisedSearchMetadata

interface FollowsSource : CatalogueSource {
    suspend fun fetchFollows(page: Int): MangasPage

    /**
     * Returns a list of all Follows retrieved by Coroutines
     *
     * @param SManga all smanga found for user
     */
    suspend fun fetchAllFollows(): List<Pair<SManga, RaisedSearchMetadata>>
}
