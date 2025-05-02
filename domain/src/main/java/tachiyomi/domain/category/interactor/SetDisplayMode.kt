package faxyomi.domain.category.interactor

import faxyomi.domain.library.model.LibraryDisplayMode
import faxyomi.domain.library.service.LibraryPreferences

class SetDisplayMode(
    private val preferences: LibraryPreferences,
) {

    fun await(display: LibraryDisplayMode) {
        preferences.displayMode().set(display)
    }
}
