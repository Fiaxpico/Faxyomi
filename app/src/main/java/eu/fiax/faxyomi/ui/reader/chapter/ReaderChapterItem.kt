package eu.fiax.faxyomi.ui.reader.chapter

import faxyomi.domain.chapter.model.Chapter
import faxyomi.domain.manga.model.Manga
import java.time.format.DateTimeFormatter

data class ReaderChapterItem(
    val chapter: Chapter,
    val manga: Manga,
    val isCurrent: Boolean,
    val dateFormat: DateTimeFormatter,
)
