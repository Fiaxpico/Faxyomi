package eu.fiax.faxyomi.util

import rx.Observable
import faxyomi.core.common.util.lang.awaitSingle

actual suspend fun <T> Observable<T>.awaitSingle(): T = awaitSingle()
