package exh.util

import android.content.Context
import eu.fiax.domain.manga.model.toSManga
import eu.fiax.faxyomi.source.model.MangasPage
import eu.fiax.faxyomi.source.online.UrlImportableSource
import exh.GalleryAddEvent
import exh.GalleryAdder
import rx.Observable
import faxyomi.core.common.util.lang.runAsObservable

private val galleryAdder by lazy {
    GalleryAdder()
}

/**
 * A version of fetchSearchManga that supports URL importing
 */
fun UrlImportableSource.urlImportFetchSearchManga(
    context: Context,
    query: String,
    fail: () -> Observable<MangasPage>,
): Observable<MangasPage> =
    when {
        query.startsWith("http://") || query.startsWith("https://") -> {
            runAsObservable {
                galleryAdder.addGallery(context, query, false, this@urlImportFetchSearchManga)
            }
                .map { res ->
                    MangasPage(
                        if (res is GalleryAddEvent.Success) {
                            listOf(res.manga.toSManga())
                        } else {
                            emptyList()
                        },
                        false,
                    )
                }
        }
        else -> fail()
    }

/**
 * A version of fetchSearchManga that supports URL importing
 */
suspend fun UrlImportableSource.urlImportFetchSearchMangaSuspend(
    context: Context,
    query: String,
    fail: suspend () -> MangasPage,
): MangasPage =
    when {
        query.startsWith("http://") || query.startsWith("https://") -> {
            val res = galleryAdder.addGallery(
                context = context,
                url = query,
                fav = false,
                forceSource = this,
            )

            MangasPage(
                if (res is GalleryAddEvent.Success) {
                    listOf(res.manga.toSManga())
                } else {
                    emptyList()
                },
                false,
            )
        }
        else -> fail()
    }
