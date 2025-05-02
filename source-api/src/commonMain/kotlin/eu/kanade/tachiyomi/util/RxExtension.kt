package eu.fiax.faxyomi.util

import rx.Observable

expect suspend fun <T> Observable<T>.awaitSingle(): T
