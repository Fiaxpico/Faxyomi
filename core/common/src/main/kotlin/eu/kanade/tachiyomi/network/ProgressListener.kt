package eu.fiax.faxyomi.network

interface ProgressListener {
    fun update(bytesRead: Long, contentLength: Long, done: Boolean)
}
