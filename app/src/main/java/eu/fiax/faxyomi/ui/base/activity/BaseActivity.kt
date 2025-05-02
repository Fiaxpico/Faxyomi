package eu.fiax.faxyomi.ui.base.activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import eu.fiax.faxyomi.ui.base.delegate.SecureActivityDelegate
import eu.fiax.faxyomi.ui.base.delegate.SecureActivityDelegateImpl
import eu.fiax.faxyomi.ui.base.delegate.ThemingDelegate
import eu.fiax.faxyomi.ui.base.delegate.ThemingDelegateImpl
import eu.fiax.faxyomi.util.system.prepareTabletUiContext

open class BaseActivity :
    AppCompatActivity(),
    SecureActivityDelegate by SecureActivityDelegateImpl(),
    ThemingDelegate by ThemingDelegateImpl() {

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(newBase.prepareTabletUiContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        applyAppTheme(this)
        super.onCreate(savedInstanceState)
    }
}
