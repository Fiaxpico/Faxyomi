package mihon.core.migration.migrations

import eu.fiax.domain.ui.UiPreferences
import mihon.core.migration.Migration
import mihon.core.migration.MigrationContext
import faxyomi.core.common.preference.PreferenceStore
import faxyomi.core.common.util.lang.withIOContext

class MoveRelativeTimeSettingMigration : Migration {
    override val version: Float = 57f

    override suspend fun invoke(migrationContext: MigrationContext): Boolean = withIOContext {
        val preferenceStore = migrationContext.get<PreferenceStore>() ?: return@withIOContext false
        val uiPreferences = migrationContext.get<UiPreferences>() ?: return@withIOContext false
        val pref = preferenceStore.getInt("relative_time", 7)
        if (pref.get() == 0) {
            uiPreferences.relativeTime().set(false)
        }

        return@withIOContext true
    }
}
