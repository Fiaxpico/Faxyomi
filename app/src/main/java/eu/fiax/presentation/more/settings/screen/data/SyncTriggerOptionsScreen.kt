package eu.fiax.presentation.more.settings.screen.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import eu.fiax.domain.sync.SyncPreferences
import eu.fiax.presentation.components.AppBar
import eu.fiax.presentation.util.Screen
import eu.fiax.faxyomi.data.sync.models.SyncTriggerOptions
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.update
import faxyomi.i18n.MR
import faxyomi.i18n.sy.SYMR
import faxyomi.presentation.core.components.LabeledCheckbox
import faxyomi.presentation.core.components.LazyColumnWithAction
import faxyomi.presentation.core.components.SectionCard
import faxyomi.presentation.core.components.material.Scaffold
import faxyomi.presentation.core.i18n.stringResource
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get

class SyncTriggerOptionsScreen : Screen() {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val model = rememberScreenModel { SyncOptionsScreenModel() }
        val state by model.state.collectAsState()

        Scaffold(
            topBar = {
                AppBar(
                    title = stringResource(SYMR.strings.pref_sync_options),
                    navigateUp = navigator::pop,
                    scrollBehavior = it,
                )
            },
        ) { contentPadding ->
            LazyColumnWithAction(
                contentPadding = contentPadding,
                actionLabel = stringResource(MR.strings.action_save),
                actionEnabled = true,
                onClickAction = {
                    navigator.pop()
                },
            ) {
                item {
                    SectionCard(SYMR.strings.label_triggers) {
                        Options(SyncTriggerOptions.mainOptions, state, model)
                    }
                }
            }
        }
    }

    @Composable
    private fun Options(
        options: ImmutableList<SyncTriggerOptions.Entry>,
        state: SyncOptionsScreenModel.State,
        model: SyncOptionsScreenModel,
    ) {
        options.forEach { option ->
            LabeledCheckbox(
                label = stringResource(option.label),
                checked = option.getter(state.options),
                onCheckedChange = {
                    model.toggle(option.setter, it)
                },
                enabled = option.enabled(state.options),
            )
        }
    }
}

private class SyncOptionsScreenModel(
    val syncPreferences: SyncPreferences = Injekt.get(),
) : StateScreenModel<SyncOptionsScreenModel.State>(
    State(
        syncPreferences.getSyncTriggerOptions(),
    ),
) {

    fun toggle(setter: (SyncTriggerOptions, Boolean) -> SyncTriggerOptions, enabled: Boolean) {
        mutableState.update {
            val updatedTriggerOptions = setter(it.options, enabled)
            syncPreferences.setSyncTriggerOptions(updatedTriggerOptions)
            it.copy(
                options = updatedTriggerOptions,
            )
        }
    }

    @Immutable
    data class State(
        val options: SyncTriggerOptions = SyncTriggerOptions(),
    )
}
