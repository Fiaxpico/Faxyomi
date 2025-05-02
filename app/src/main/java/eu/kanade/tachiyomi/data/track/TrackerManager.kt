package eu.fiax.faxyomi.data.track

import eu.fiax.faxyomi.data.track.anilist.Anilist
import eu.fiax.faxyomi.data.track.bangumi.Bangumi
import eu.fiax.faxyomi.data.track.kavita.Kavita
import eu.fiax.faxyomi.data.track.kitsu.Kitsu
import eu.fiax.faxyomi.data.track.komga.Komga
import eu.fiax.faxyomi.data.track.mangaupdates.MangaUpdates
import eu.fiax.faxyomi.data.track.mdlist.MdList
import eu.fiax.faxyomi.data.track.myanimelist.MyAnimeList
import eu.fiax.faxyomi.data.track.shikimori.Shikimori
import eu.fiax.faxyomi.data.track.suwayomi.Suwayomi
import kotlinx.coroutines.flow.combine

class TrackerManager {

    companion object {
        const val ANILIST = 2L
        const val KITSU = 3L
        const val KAVITA = 8L

        // SY --> Mangadex from Neko
        const val MDLIST = 60L
        // SY <--
    }

    val mdList = MdList(MDLIST)

    val myAnimeList = MyAnimeList(1L)
    val aniList = Anilist(ANILIST)
    val kitsu = Kitsu(KITSU)
    val shikimori = Shikimori(4L)
    val bangumi = Bangumi(5L)
    val komga = Komga(6L)
    val mangaUpdates = MangaUpdates(7L)
    val kavita = Kavita(KAVITA)
    val suwayomi = Suwayomi(9L)

    val trackers =
        listOf(mdList, myAnimeList, aniList, kitsu, shikimori, bangumi, komga, mangaUpdates, kavita, suwayomi)

    fun loggedInTrackers() = trackers.filter { it.isLoggedIn }

    fun loggedInTrackersFlow() = combine(trackers.map { it.isLoggedInFlow }) {
        it.mapIndexedNotNull { index, isLoggedIn ->
            if (isLoggedIn) trackers[index] else null
        }
    }

    fun get(id: Long) = trackers.find { it.id == id }

    fun getAll(ids: Set<Long>) = trackers.filter { it.id in ids }
}
