package eu.fiax.faxyomi.ui.browse

import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabOptions
import eu.fiax.core.preference.asState
import eu.fiax.domain.ui.UiPreferences
import eu.fiax.presentation.components.TabbedScreen
import eu.fiax.presentation.util.Tab
import eu.fiax.R
import eu.fiax.faxyomi.ui.browse.extension.ExtensionsScreenModel
import eu.fiax.faxyomi.ui.browse.extension.extensionsTab
import eu.fiax.faxyomi.ui.browse.feed.feedTab
import eu.fiax.faxyomi.ui.browse.migration.sources.migrateSourceTab
import eu.fiax.faxyomi.ui.browse.source.globalsearch.GlobalSearchScreen
import eu.fiax.faxyomi.ui.browse.source.sourcesTab
import eu.fiax.faxyomi.ui.main.MainActivity
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import faxyomi.i18n.MR
import faxyomi.presentation.core.i18n.stringResource
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get

data object BrowseTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val isSelected = LocalTabNavigator.current.current.key == key
            val image = AnimatedImageVector.animatedVectorResource(R.drawable.anim_browse_enter)
            return TabOptions(
                index = 3u,
                title = stringResource(MR.strings.browse),
                icon = rememberAnimatedVectorPainter(image, isSelected),
            )
        }

    override suspend fun onReselect(navigator: Navigator) {
        navigator.push(GlobalSearchScreen())
    }

    private val switchToExtensionTabChannel = Channel<Unit>(1, BufferOverflow.DROP_OLDEST)

    fun showExtension() {
        switchToExtensionTabChannel.trySend(Unit)
    }

    @Composable
    override fun Content() {
        val context = LocalContext.current
        val scope = rememberCoroutineScope()
        // SY -->
        val hideFeedTab by remember { Injekt.get<UiPreferences>().hideFeedTab().asState(scope) }
        val feedTabInFront by remember { Injekt.get<UiPreferences>().feedTabInFront().asState(scope) }
        // SY <--

        // Hoisted for extensions tab's search bar
        val extensionsScreenModel = rememberScreenModel { ExtensionsScreenModel() }
        val extensionsState by extensionsScreenModel.state.collectAsState()

        // SY -->
        val tabs = if (hideFeedTab) {
            persistentListOf(
                sourcesTab(),
                extensionsTab(extensionsScreenModel),
                migrateSourceTab(),
            )
        } else if (feedTabInFront) {
            persistentListOf(
                feedTab(),
                sourcesTab(),
                extensionsTab(extensionsScreenModel),
                migrateSourceTab(),
            )
        } else {
            persistentListOf(
                sourcesTab(),
                feedTab(),
                extensionsTab(extensionsScreenModel),
                migrateSourceTab(),
            )
        }
        // SY <--

        val state = rememberPagerState { tabs.size }

        TabbedScreen(
            titleRes = MR.strings.browse,
            tabs = tabs,
            state = state,
            searchQuery = extensionsState.searchQuery,
            onChangeSearchQuery = extensionsScreenModel::search,
        )
        LaunchedEffect(Unit) {
            switchToExtensionTabChannel.receiveAsFlow()
                .collectLatest { state.scrollToPage(/* SY --> */2/* SY <-- */) }
        }

        LaunchedEffect(Unit) {
            (context as? MainActivity)?.ready = true
        }
    }
}
