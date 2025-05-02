package eu.fiax.faxyomi.ui.reader.viewer.navigation

import android.graphics.RectF
import eu.fiax.faxyomi.ui.reader.viewer.ViewerNavigation

/**
 * Visualization of default state without any inversion
 * +---+---+---+
 * | N | M | P |   P: Move Right
 * +---+---+---+
 * | N | M | P |   M: Menu
 * +---+---+---+
 * | N | M | P |   N: Move Left
 * +---+---+---+
 */
class RightAndLeftNavigation : ViewerNavigation() {

    override var regionList: List<Region> = listOf(
        Region(
            rectF = RectF(0f, 0f, 0.33f, 1f),
            type = NavigationRegion.LEFT,
        ),
        Region(
            rectF = RectF(0.66f, 0f, 1f, 1f),
            type = NavigationRegion.RIGHT,
        ),
    )
}
