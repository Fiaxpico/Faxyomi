package eu.fiax.faxyomi.crash

import android.content.Intent
import android.os.Bundle
import androidx.core.view.WindowCompat
import eu.kanade.presentation.crash.CrashScreen
import eu.fiax.faxyomi.ui.base.activity.BaseActivity
import eu.fiax.faxyomi.ui.main.MainActivity
import eu.fiax.faxyomi.util.view.setComposeContent

class CrashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val exception = GlobalExceptionHandler.getThrowableFromIntent(intent)
        setComposeContent {
            CrashScreen(
                exception = exception,
                onRestartClick = {
                    finishAffinity()
                    startActivity(Intent(this@CrashActivity, MainActivity::class.java))
                },
            )
        }
    }
}
