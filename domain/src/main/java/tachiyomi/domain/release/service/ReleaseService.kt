package faxyomi.domain.release.service

import faxyomi.domain.release.model.Release

interface ReleaseService {

    suspend fun latest(repository: String): Release
}
