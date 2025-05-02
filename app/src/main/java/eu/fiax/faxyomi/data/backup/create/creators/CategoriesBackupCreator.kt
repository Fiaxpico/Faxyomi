package eu.fiax.faxyomi.data.backup.create.creators

import eu.fiax.faxyomi.data.backup.models.BackupCategory
import eu.fiax.faxyomi.data.backup.models.backupCategoryMapper
import faxyomi.domain.category.interactor.GetCategories
import faxyomi.domain.category.model.Category
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get

class CategoriesBackupCreator(
    private val getCategories: GetCategories = Injekt.get(),
) {

    suspend operator fun invoke(): List<BackupCategory> {
        return getCategories.await()
            .filterNot(Category::isSystemCategory)
            .map(backupCategoryMapper)
    }
}
