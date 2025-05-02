package faxyomi.source.local.image

import com.hippo.unifile.UniFile
import eu.fiax.faxyomi.source.model.SManga
import java.io.InputStream

expect class LocalCoverManager {

    fun find(mangaUrl: String): UniFile?

    // SY -->
    fun update(manga: SManga, inputStream: InputStream, encrypted: Boolean = false): UniFile?
    // SY <--
}
