package eu.fiax.faxyomi.ui.reader.viewer

import eu.fiax.faxyomi.data.database.models.toDomainChapter
import eu.fiax.faxyomi.ui.reader.model.ReaderChapter
import faxyomi.domain.chapter.service.calculateChapterGap as domainCalculateChapterGap

fun calculateChapterGap(higherReaderChapter: ReaderChapter?, lowerReaderChapter: ReaderChapter?): Int {
    return domainCalculateChapterGap(
        higherReaderChapter?.chapter?.toDomainChapter(),
        lowerReaderChapter?.chapter?.toDomainChapter(),
    )
}
