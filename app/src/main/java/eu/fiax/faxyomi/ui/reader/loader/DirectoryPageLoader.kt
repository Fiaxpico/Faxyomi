package eu.fiax.faxyomi.ui.reader.loader

import com.hippo.unifile.UniFile
import eu.fiax.faxyomi.source.model.Page
import eu.fiax.faxyomi.ui.reader.model.ReaderPage
import eu.fiax.faxyomi.util.lang.compareToCaseInsensitiveNaturalOrder
import faxyomi.core.common.util.system.ImageUtil

/**
 * Loader used to load a chapter from a directory given on [file].
 */
internal class DirectoryPageLoader(val file: UniFile) : PageLoader() {

    override var isLocal: Boolean = true

    override suspend fun getPages(): List<ReaderPage> {
        return file.listFiles()
            ?.filter { !it.isDirectory && ImageUtil.isImage(it.name) { it.openInputStream() } }
            ?.sortedWith { f1, f2 -> f1.name.orEmpty().compareToCaseInsensitiveNaturalOrder(f2.name.orEmpty()) }
            ?.mapIndexed { i, file ->
                val streamFn = { file.openInputStream() }
                ReaderPage(i).apply {
                    stream = streamFn
                    status = Page.State.READY
                }
            }
            .orEmpty()
    }
}
