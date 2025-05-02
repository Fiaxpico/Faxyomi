package exh.md.follows

import eu.fiax.faxyomi.source.model.MangasPage
import eu.fiax.faxyomi.source.online.all.MangaDex
import faxyomi.data.source.SourcePagingSource

/**
 * LatestUpdatesPager inherited from the general Pager.
 */
class MangaDexFollowsPagingSource(val mangadex: MangaDex) : SourcePagingSource(mangadex) {

    override suspend fun requestNextPage(currentPage: Int): MangasPage {
        return mangadex.fetchFollows(currentPage)
    }
}
