package faxyomi.domain.category.interactor

import faxyomi.domain.category.repository.CategoryRepository
import faxyomi.domain.library.model.plus
import faxyomi.domain.library.service.LibraryPreferences

class ResetCategoryFlags(
    private val preferences: LibraryPreferences,
    private val categoryRepository: CategoryRepository,
) {

    suspend fun await() {
        val sort = preferences.sortingMode().get()
        categoryRepository.updateAllFlags(sort.type + sort.direction)
    }
}
