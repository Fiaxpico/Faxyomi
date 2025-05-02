package tachiyomi.data.release

import eu.fiax.faxyomi.network.GET
import eu.fiax.faxyomi.network.NetworkHelper
import eu.fiax.faxyomi.network.awaitSuccess
import eu.fiax.faxyomi.network.parseAs
import kotlinx.serialization.json.Json
import tachiyomi.domain.release.model.Release
import tachiyomi.domain.release.service.ReleaseService

class ReleaseServiceImpl(
    private val networkService: NetworkHelper,
    private val json: Json,
) : ReleaseService {

    override suspend fun latest(repository: String): Release {
        return with(json) {
            networkService.client
                .newCall(GET("https://api.github.com/repos/$repository/releases/latest"))
                .awaitSuccess()
                .parseAs<GithubRelease>()
                .let(releaseMapper)
        }
    }
}
