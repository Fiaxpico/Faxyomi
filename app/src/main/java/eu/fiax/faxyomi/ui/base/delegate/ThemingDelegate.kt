package eu.fiax.faxyomi.ui.base.delegate

import android.app.Activity
import eu.fiax.domain.ui.UiPreferences
import eu.fiax.domain.ui.model.AppTheme
import eu.fiax.R
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get

interface ThemingDelegate {
    fun applyAppTheme(activity: Activity)

    companion object {
        fun getThemeResIds(appTheme: AppTheme, isAmoled: Boolean): List<Int> {
            return buildList(2) {
                add(themeResources.getOrDefault(appTheme, R.style.Theme_faxyomi))
                if (isAmoled) add(R.style.ThemeOverlay_faxyomi_Amoled)
            }
        }
    }
}

class ThemingDelegateImpl : ThemingDelegate {
    override fun applyAppTheme(activity: Activity) {
        val uiPreferences = Injekt.get<UiPreferences>()
        ThemingDelegate.getThemeResIds(uiPreferences.appTheme().get(), uiPreferences.themeDarkAmoled().get())
            .forEach(activity::setTheme)
    }
}

private val themeResources: Map<AppTheme, Int> = mapOf(
    AppTheme.MONET to R.style.Theme_faxyomi_Monet,
    AppTheme.GREEN_APPLE to R.style.Theme_faxyomi_GreenApple,
    AppTheme.LAVENDER to R.style.Theme_faxyomi_Lavender,
    AppTheme.MIDNIGHT_DUSK to R.style.Theme_faxyomi_MidnightDusk,
    AppTheme.MONOCHROME to R.style.Theme_faxyomi_Monochrome,
    AppTheme.NORD to R.style.Theme_faxyomi_Nord,
    AppTheme.STRAWBERRY_DAIQUIRI to R.style.Theme_faxyomi_StrawberryDaiquiri,
    AppTheme.TAKO to R.style.Theme_faxyomi_Tako,
    AppTheme.TEALTURQUOISE to R.style.Theme_faxyomi_TealTurquoise,
    AppTheme.YINYANG to R.style.Theme_faxyomi_YinYang,
    AppTheme.YOTSUBA to R.style.Theme_faxyomi_Yotsuba,
    AppTheme.TIDAL_WAVE to R.style.Theme_faxyomi_TidalWave,
)
