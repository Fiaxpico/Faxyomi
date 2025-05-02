package eu.fiax.faxyomi.source.online

import eu.fiax.faxyomi.source.Source

interface RandomMangaSource : Source {
    suspend fun fetchRandomMangaUrl(): String
}
