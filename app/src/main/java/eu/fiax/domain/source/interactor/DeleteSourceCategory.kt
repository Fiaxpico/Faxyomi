package eu.fiax.domain.source.interactor

import eu.fiax.domain.source.service.SourcePreferences
import faxyomi.core.common.preference.getAndSet
import faxyomi.core.common.preference.minusAssign

class DeleteSourceCategory(private val preferences: SourcePreferences) {

    fun await(category: String) {
        preferences.sourcesTabSourcesInCategories().getAndSet { sourcesInCategories ->
            sourcesInCategories.filterNot { it.substringAfter("|") == category }.toSet()
        }
        preferences.sourcesTabCategories() -= category
    }
}
