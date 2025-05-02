package eu.fiax.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalContext
import eu.fiax.domain.ui.UiPreferences
import eu.fiax.domain.ui.model.AppTheme
import eu.fiax.presentation.theme.colorscheme.BaseColorScheme
import eu.fiax.presentation.theme.colorscheme.GreenAppleColorScheme
import eu.fiax.presentation.theme.colorscheme.LavenderColorScheme
import eu.fiax.presentation.theme.colorscheme.MidnightDuskColorScheme
import eu.fiax.presentation.theme.colorscheme.MonetColorScheme
import eu.fiax.presentation.theme.colorscheme.MonochromeColorScheme
import eu.fiax.presentation.theme.colorscheme.NordColorScheme
import eu.fiax.presentation.theme.colorscheme.StrawberryColorScheme
import eu.fiax.presentation.theme.colorscheme.faxyomiColorScheme
import eu.fiax.presentation.theme.colorscheme.TakoColorScheme
import eu.fiax.presentation.theme.colorscheme.TealTurqoiseColorScheme
import eu.fiax.presentation.theme.colorscheme.TidalWaveColorScheme
import eu.fiax.presentation.theme.colorscheme.YinYangColorScheme
import eu.fiax.presentation.theme.colorscheme.YotsubaColorScheme
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get

@Composable
fun faxyomiTheme(
    appTheme: AppTheme? = null,
    amoled: Boolean? = null,
    content: @Composable () -> Unit,
) {
    val uiPreferences = Injekt.get<UiPreferences>()
    BasefaxyomiTheme(
        appTheme = appTheme ?: uiPreferences.appTheme().get(),
        isAmoled = amoled ?: uiPreferences.themeDarkAmoled().get(),
        content = content,
    )
}

@Composable
fun faxyomiPreviewTheme(
    appTheme: AppTheme = AppTheme.DEFAULT,
    isAmoled: Boolean = false,
    content: @Composable () -> Unit,
) = BasefaxyomiTheme(appTheme, isAmoled, content)

@Composable
private fun BasefaxyomiTheme(
    appTheme: AppTheme,
    isAmoled: Boolean,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = getThemeColorScheme(appTheme, isAmoled),
        content = content,
    )
}

@Composable
@ReadOnlyComposable
private fun getThemeColorScheme(
    appTheme: AppTheme,
    isAmoled: Boolean,
): ColorScheme {
    val colorScheme = if (appTheme == AppTheme.MONET) {
        MonetColorScheme(LocalContext.current)
    } else {
        colorSchemes.getOrDefault(appTheme, faxyomiColorScheme)
    }
    return colorScheme.getColorScheme(
        isSystemInDarkTheme(),
        isAmoled,
    )
}

private val colorSchemes: Map<AppTheme, BaseColorScheme> = mapOf(
    AppTheme.DEFAULT to faxyomiColorScheme,
    AppTheme.GREEN_APPLE to GreenAppleColorScheme,
    AppTheme.LAVENDER to LavenderColorScheme,
    AppTheme.MIDNIGHT_DUSK to MidnightDuskColorScheme,
    AppTheme.MONOCHROME to MonochromeColorScheme,
    AppTheme.NORD to NordColorScheme,
    AppTheme.STRAWBERRY_DAIQUIRI to StrawberryColorScheme,
    AppTheme.TAKO to TakoColorScheme,
    AppTheme.TEALTURQUOISE to TealTurqoiseColorScheme,
    AppTheme.TIDAL_WAVE to TidalWaveColorScheme,
    AppTheme.YINYANG to YinYangColorScheme,
    AppTheme.YOTSUBA to YotsubaColorScheme,
)
