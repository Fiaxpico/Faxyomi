package exh.recs.sources

import dev.icerock.moko.resources.StringResource
import eu.fiax.faxyomi.network.GET
import eu.fiax.faxyomi.network.NetworkHelper
import eu.fiax.faxyomi.network.awaitSuccess
import eu.fiax.faxyomi.network.parseAs
import eu.fiax.faxyomi.source.CatalogueSource
import eu.fiax.faxyomi.source.model.MangasPage
import eu.fiax.faxyomi.source.model.SManga
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import okhttp3.Headers
import okhttp3.HttpUrl.Companion.toHttpUrl
import faxyomi.data.source.NoResultsException
import faxyomi.domain.manga.model.Manga
import faxyomi.i18n.sy.SYMR
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get
import uy.kohesive.injekt.injectLazy

fun CatalogueSource.isComickSource() = name == "Comick"

class ComickPagingSource(
    manga: Manga,
    private val comickSource: CatalogueSource,
) : RecommendationPagingSource(manga, comickSource) {

    override val name: String
        get() = "Comick"

    override val category: StringResource
        get() = SYMR.strings.community_recommendations

    override val associatedSourceId: Long
        get() = comickSource.id

    private val client by lazy { Injekt.get<NetworkHelper>().client }
    private val json by injectLazy<Json>()
    private val thumbnailBaseUrl = "https://meo.comick.pictures/"

    override suspend fun requestNextPage(currentPage: Int): MangasPage {
        val mangasPage = coroutineScope {
            val headers = Headers.Builder().apply {
                add("Referer", "api.comick.fun/")
                add("User-Agent", "faxyomi ${System.getProperty("http.agent")}")
            }

            // Comick extension populates the URL field with: '/comic/{hid}#'
            val url = "https://api.comick.fun/v1.0${manga.url}".toHttpUrl()
                .newBuilder()
                .addQueryParameter("faxyomi", "true")
                .build()

            val request = GET(url, headers.build())

            val data = with(json) {
                client.newCall(request).awaitSuccess()
                    .parseAs<JsonObject>()
            }

            val recs = data["comic"]!!
                .jsonObject["recommendations"]!!
                .jsonArray
                .map { it.jsonObject["relates"]!! }
                .map { it.jsonObject }

            MangasPage(
                recs.map { rec ->
                    SManga(
                        title = rec["title"]!!.jsonPrimitive.content,
                        url = "/comic/${rec["hid"]!!.jsonPrimitive.content}#",
                        thumbnail_url = thumbnailBaseUrl + rec["md_covers"]!!
                            .jsonArray
                            .map { it.jsonObject["b2key"]!!.jsonPrimitive.content }
                            .first(),
                        // Mark as uninitialized to force fetching missing metadata
                        initialized = false,
                    )
                },
                false,
            )
        }

        return mangasPage.takeIf { it.mangas.isNotEmpty() } ?: throw NoResultsException()
    }
}
