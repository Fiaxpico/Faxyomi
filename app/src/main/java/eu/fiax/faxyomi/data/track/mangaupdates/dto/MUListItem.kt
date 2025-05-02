package eu.fiax.faxyomi.data.track.mangaupdates.dto

import eu.fiax.faxyomi.data.database.models.Track
import eu.fiax.faxyomi.data.track.mangaupdates.MangaUpdates.Companion.READING_LIST
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MUListItem(
    val series: MUSeries? = null,
    @SerialName("list_id")
    val listId: Long? = null,
    val status: MUStatus? = null,
    val priority: Int? = null,
)

fun MUListItem.copyTo(track: Track): Track {
    return track.apply {
        this.status = listId ?: READING_LIST
        this.last_chapter_read = this@copyTo.status?.chapter?.toDouble() ?: 0.0
    }
}
