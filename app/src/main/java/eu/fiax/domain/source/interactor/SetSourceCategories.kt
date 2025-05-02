package eu.fiax.domain.source.interactor

import eu.fiax.domain.source.service.SourcePreferences
import faxyomi.core.common.preference.getAndSet
import faxyomi.domain.source.model.Source

class SetSourceCategories(
    private val preferences: SourcePreferences,
) {

    fun await(source: Source, sourceCategories: List<String>) {
        val sourceIdString = source.id.toString()
        preferences.sourcesTabSourcesInCategories().getAndSet { sourcesInCategories ->
            val currentSourceCategories = sourcesInCategories.filterNot {
                it.substringBefore('|') == sourceIdString
            }
            val newSourceCategories = currentSourceCategories + sourceCategories.map {
                "$sourceIdString|$it"
            }
            newSourceCategories.toSet()
        }
    }
}
