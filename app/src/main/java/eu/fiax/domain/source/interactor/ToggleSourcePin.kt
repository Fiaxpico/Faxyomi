package eu.fiax.domain.source.interactor

import eu.fiax.domain.source.service.SourcePreferences
import faxyomi.core.common.preference.getAndSet
import faxyomi.domain.source.model.Source

class ToggleSourcePin(
    private val preferences: SourcePreferences,
) {

    fun await(source: Source) {
        val isPinned = source.id.toString() in preferences.pinnedSources().get()
        preferences.pinnedSources().getAndSet { pinned ->
            if (isPinned) pinned.minus("${source.id}") else pinned.plus("${source.id}")
        }
    }
}
