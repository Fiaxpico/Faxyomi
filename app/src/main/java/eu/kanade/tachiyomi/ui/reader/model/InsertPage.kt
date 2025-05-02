package eu.fiax.faxyomi.ui.reader.model

class InsertPage(val parent: ReaderPage) : ReaderPage(parent.index, parent.url, parent.imageUrl) {

    override var chapter: ReaderChapter = parent.chapter

    init {
        status = State.READY
        stream = parent.stream
    }
}
