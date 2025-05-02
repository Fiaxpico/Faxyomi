package eu.fiax.faxyomi.ui.browse.migration.advanced.process

import eu.fiax.faxyomi.ui.browse.migration.advanced.design.MigrationType
import java.io.Serializable

data class MigrationProcedureConfig(
    var migration: MigrationType,
    val extraSearchParams: String?,
) : Serializable
