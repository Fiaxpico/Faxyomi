package eu.fiax.faxyomi.data.backup.create.creators

import eu.fiax.faxyomi.data.backup.models.BackupSavedSearch
import eu.fiax.faxyomi.data.backup.models.backupSavedSearchMapper
import tachiyomi.data.DatabaseHandler
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get

class SavedSearchBackupCreator(
    private val handler: DatabaseHandler = Injekt.get(),
) {

    suspend operator fun invoke(): List<BackupSavedSearch> {
        return handler.awaitList { saved_searchQueries.selectAll(backupSavedSearchMapper) }
    }
}
