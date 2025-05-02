package eu.fiax.faxyomi.ui.reader.loader

import eu.fiax.faxyomi.source.model.Page
import eu.fiax.faxyomi.ui.reader.model.ReaderPage
import eu.fiax.faxyomi.util.storage.EpubFile
import mihon.core.common.archive.ArchiveReader

/**
 * Loader used to load a chapter from a .epub file.
 */
internal class EpubPageLoader(reader: ArchiveReader) : PageLoader() {

    private val epub = EpubFile(reader)

    override var isLocal: Boolean = true

    override suspend fun getPages(): List<ReaderPage> {
        return epub.getImagesFromPages()
            .mapIndexed { i, path ->
                val streamFn = { epub.getInputStream(path)!! }
                ReaderPage(i).apply {
                    stream = streamFn
                    status = Page.State.READY
                }
            }
    }

    override suspend fun loadPage(page: ReaderPage) {
        check(!isRecycled)
    }

    override fun recycle() {
        super.recycle()
        epub.close()
    }
}
