package eu.fiax.faxyomi.di

import android.app.Application
import exh.pref.DelegateSourcePreferences
import faxyomi.domain.UnsortedPreferences
import uy.kohesive.injekt.api.InjektRegistrar

class SYPreferenceModule(val application: Application) : InjektModule {

    override fun InjektRegistrar.registerInjectables() {
        addSingletonFactory {
            DelegateSourcePreferences(
                preferenceStore = get(),
            )
        }

        addSingletonFactory {
            UnsortedPreferences(get())
        }
    }
}
