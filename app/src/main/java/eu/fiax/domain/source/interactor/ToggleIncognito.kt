package eu.fiax.domain.source.interactor

import eu.fiax.domain.source.service.SourcePreferences
import faxyomi.core.common.preference.getAndSet

class ToggleIncognito(
    private val preferences: SourcePreferences,
) {
    fun await(extensions: String, enable: Boolean) {
        preferences.incognitoExtensions().getAndSet {
            if (enable) it.plus(extensions) else it.minus(extensions)
        }
    }
}
