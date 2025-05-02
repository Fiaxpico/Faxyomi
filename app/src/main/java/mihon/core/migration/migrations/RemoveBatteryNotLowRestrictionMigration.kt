package mihon.core.migration.migrations

import mihon.core.migration.Migration
import mihon.core.migration.MigrationContext
import faxyomi.core.common.preference.getAndSet
import faxyomi.core.common.util.lang.withIOContext
import faxyomi.domain.library.service.LibraryPreferences

class RemoveBatteryNotLowRestrictionMigration : Migration {
    override val version: Float = 56f

    override suspend fun invoke(migrationContext: MigrationContext): Boolean = withIOContext {
        val libraryPreferences = migrationContext.get<LibraryPreferences>() ?: return@withIOContext false
        val pref = libraryPreferences.autoUpdateDeviceRestrictions()
        if (pref.isSet() && "battery_not_low" in pref.get()) {
            pref.getAndSet { it - "battery_not_low" }
        }

        return@withIOContext true
    }
}
