package eu.fiax.faxyomi.ui.browse.migration.sources

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import eu.fiax.presentation.browse.BrowseTabWrapper
import eu.fiax.presentation.util.Screen

class MigrationSourcesScreen : Screen() {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        BrowseTabWrapper(migrateSourceTab(), onBackPressed = navigator::pop)
    }
}
