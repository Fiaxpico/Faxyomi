package exh.md.similar

import dev.icerock.moko.resources.StringResource
import eu.fiax.domain.manga.model.toSManga
import eu.fiax.faxyomi.network.HttpException
import eu.fiax.faxyomi.source.model.MangasPage
import eu.fiax.faxyomi.source.model.MetadataMangasPage
import eu.fiax.faxyomi.source.online.all.MangaDex
import exh.recs.sources.RecommendationPagingSource
import exh.source.getMainSource
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import faxyomi.data.source.NoResultsException
import faxyomi.domain.manga.model.Manga
import faxyomi.i18n.sy.SYMR

/**
 * MangaDexSimilarPagingSource inherited from the general Pager.
 */
class MangaDexSimilarPagingSource(
    manga: Manga,
    private val mangaDex: MangaDex,
) : RecommendationPagingSource(manga, mangaDex) {

    override val name: String
        get() = "MangaDex"

    override val category: StringResource
        get() = SYMR.strings.similar_titles

    override val associatedSourceId: Long
        get() = mangaDex.getMainSource().id

    override suspend fun requestNextPage(currentPage: Int): MangasPage {
        val mangasPage = coroutineScope {
            try {
                val similarPageDef = async { mangaDex.getMangaSimilar(manga.toSManga()) }
                val relatedPageDef = async { mangaDex.getMangaRelated(manga.toSManga()) }
                val similarPage = similarPageDef.await()
                val relatedPage = relatedPageDef.await()

                MetadataMangasPage(
                    relatedPage.mangas + similarPage.mangas,
                    false,
                    relatedPage.mangasMetadata + similarPage.mangasMetadata,
                )
            } catch (e: HttpException) {
                when (e.code) {
                    404 -> throw NoResultsException()
                    else -> throw e
                }
            }
        }

        return mangasPage.takeIf { it.mangas.isNotEmpty() } ?: throw NoResultsException()
    }
}
