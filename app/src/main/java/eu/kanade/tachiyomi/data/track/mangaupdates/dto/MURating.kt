package eu.fiax.faxyomi.data.track.mangaupdates.dto

import eu.fiax.faxyomi.data.database.models.Track
import kotlinx.serialization.Serializable

@Serializable
data class MURating(
    val rating: Double? = null,
)

fun MURating.copyTo(track: Track): Track {
    return track.apply {
        this.score = rating ?: 0.0
    }
}
