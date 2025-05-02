package eu.fiax.domain.source.interactor

import eu.fiax.domain.source.service.SourcePreferences
import faxyomi.core.common.preference.getAndSet
import faxyomi.domain.source.model.Source

class ToggleExcludeFromDataSaver(
    private val preferences: SourcePreferences,
) {

    fun await(source: Source) {
        preferences.dataSaverExcludedSources().getAndSet {
            if (source.id.toString() in it) {
                it - source.id.toString()
            } else {
                it + source.id.toString()
            }
        }
    }
}
