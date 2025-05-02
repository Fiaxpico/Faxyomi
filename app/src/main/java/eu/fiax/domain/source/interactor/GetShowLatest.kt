package eu.fiax.domain.source.interactor

import eu.fiax.domain.ui.UiPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetShowLatest(
    private val preferences: UiPreferences,
) {

    fun subscribe(hasSmartSearchConfig: Boolean): Flow<Boolean> {
        return preferences.useNewSourceNavigation().changes()
            .map {
                !hasSmartSearchConfig && !it
            }
    }
}
