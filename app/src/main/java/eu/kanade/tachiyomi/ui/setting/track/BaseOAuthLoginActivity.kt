package eu.fiax.faxyomi.ui.setting.track

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import eu.fiax.faxyomi.data.track.TrackerManager
import eu.fiax.faxyomi.ui.base.activity.BaseActivity
import eu.fiax.faxyomi.ui.main.MainActivity
import eu.fiax.faxyomi.util.view.setComposeContent
import tachiyomi.presentation.core.screens.LoadingScreen
import uy.kohesive.injekt.injectLazy

abstract class BaseOAuthLoginActivity : BaseActivity() {

    internal val trackerManager: TrackerManager by injectLazy()

    abstract fun handleResult(data: Uri?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setComposeContent {
            LoadingScreen()
        }

        handleResult(intent.data)
    }

    internal fun returnToSettings() {
        finish()

        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        startActivity(intent)
    }
}
