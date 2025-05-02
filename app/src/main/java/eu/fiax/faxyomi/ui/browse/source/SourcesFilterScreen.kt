package eu.fiax.faxyomi.ui.browse.source

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import eu.fiax.presentation.browse.SourcesFilterScreen
import eu.fiax.presentation.util.Screen
import eu.fiax.faxyomi.util.system.toast
import faxyomi.i18n.MR
import faxyomi.presentation.core.screens.LoadingScreen

class SourcesFilterScreen : Screen() {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel { SourcesFilterScreenModel() }
        val state by screenModel.state.collectAsState()

        if (state is SourcesFilterScreenModel.State.Loading) {
            LoadingScreen()
            return
        }

        if (state is SourcesFilterScreenModel.State.Error) {
            val context = LocalContext.current
            LaunchedEffect(Unit) {
                context.toast(MR.strings.internal_error)
                navigator.pop()
            }
            return
        }

        val successState = state as SourcesFilterScreenModel.State.Success

        SourcesFilterScreen(
            navigateUp = navigator::pop,
            state = successState,
            onClickLanguage = screenModel::toggleLanguage,
            onClickSource = screenModel::toggleSource,
            // SY -->
            onClickSources = screenModel::toggleSources,
            // SY <--
        )
    }
}
