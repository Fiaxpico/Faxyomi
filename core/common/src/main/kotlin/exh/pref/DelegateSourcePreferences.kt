package exh.pref

import faxyomi.core.common.preference.PreferenceStore

class DelegateSourcePreferences(
    private val preferenceStore: PreferenceStore,
) {

    fun delegateSources() = preferenceStore.getBoolean("eh_delegate_sources", true)

    fun useJapaneseTitle() = preferenceStore.getBoolean("use_jp_title", false)
}
