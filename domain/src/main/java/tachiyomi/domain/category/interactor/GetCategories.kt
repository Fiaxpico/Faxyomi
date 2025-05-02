package faxyomi.domain.category.interactor

import kotlinx.coroutines.flow.Flow
import faxyomi.domain.category.model.Category
import faxyomi.domain.category.repository.CategoryRepository

class GetCategories(
    private val categoryRepository: CategoryRepository,
) {

    fun subscribe(): Flow<List<Category>> {
        return categoryRepository.getAllAsFlow()
    }

    fun subscribe(mangaId: Long): Flow<List<Category>> {
        return categoryRepository.getCategoriesByMangaIdAsFlow(mangaId)
    }

    suspend fun await(): List<Category> {
        return categoryRepository.getAll()
    }

    suspend fun await(mangaId: Long): List<Category> {
        return categoryRepository.getCategoriesByMangaId(mangaId)
    }
}
