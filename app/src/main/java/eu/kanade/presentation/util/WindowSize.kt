package eu.kanade.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalConfiguration
import eu.fiax.faxyomi.util.system.isTabletUi

@Composable
@ReadOnlyComposable
fun isTabletUi(): Boolean {
    return LocalConfiguration.current.isTabletUi()
}
