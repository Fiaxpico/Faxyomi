package exh.md.service

import eu.fiax.faxyomi.network.GET
import eu.fiax.faxyomi.network.awaitSuccess
import eu.fiax.faxyomi.network.parseAs
import exh.md.dto.SimilarMangaDto
import exh.md.utils.MdUtil
import okhttp3.OkHttpClient

class SimilarService(
    private val client: OkHttpClient,
) {
    suspend fun getSimilarManga(mangaId: String): SimilarMangaDto {
        return with(MdUtil.jsonParser) {
            client.newCall(
                GET(
                    "${MdUtil.similarBaseApi}$mangaId.json",
                ),
            ).awaitSuccess().parseAs()
        }
    }
}
