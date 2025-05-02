package faxyomi.domain.category.interactor

import logcat.LogPriority
import faxyomi.core.common.util.lang.withNonCancellableContext
import faxyomi.core.common.util.system.logcat
import faxyomi.domain.category.model.Category
import faxyomi.domain.category.model.CategoryUpdate
import faxyomi.domain.category.repository.CategoryRepository

class RenameCategory(
    private val categoryRepository: CategoryRepository,
) {

    suspend fun await(categoryId: Long, name: String) = withNonCancellableContext {
        val update = CategoryUpdate(
            id = categoryId,
            name = name,
        )

        try {
            categoryRepository.updatePartial(update)
            Result.Success
        } catch (e: Exception) {
            logcat(LogPriority.ERROR, e)
            Result.InternalError(e)
        }
    }

    suspend fun await(category: Category, name: String) = await(category.id, name)

    sealed interface Result {
        data object Success : Result
        data class InternalError(val error: Throwable) : Result
    }
}
