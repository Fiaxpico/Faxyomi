package eu.fiax.faxyomi.data.coil

import coil3.key.Keyer
import coil3.request.Options
import eu.fiax.domain.manga.model.PagePreview

class PagePreviewKeyer : Keyer<PagePreview> {
    override fun key(data: PagePreview, options: Options): String {
        return data.imageUrl
    }
}
