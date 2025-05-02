package eu.fiax.faxyomi.ui.browse.migration.advanced.design

import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.fiax.domain.source.service.SourcePreferences
import faxyomi.domain.source.service.SourceManager
import uy.kohesive.injekt.injectLazy

class MigrationSourceAdapter(
    listener: FlexibleAdapter.OnItemClickListener,
) : FlexibleAdapter<MigrationSourceItem>(
    null,
    listener,
    true,
) {
    val sourceManager: SourceManager by injectLazy()

    // SY _->
    val sourcePreferences: SourcePreferences by injectLazy()
    // SY <--
}
