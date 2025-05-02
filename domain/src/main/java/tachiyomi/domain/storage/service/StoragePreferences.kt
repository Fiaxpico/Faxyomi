package faxyomi.domain.storage.service

import faxyomi.core.common.preference.Preference
import faxyomi.core.common.preference.PreferenceStore
import faxyomi.core.common.storage.FolderProvider

class StoragePreferences(
    private val folderProvider: FolderProvider,
    private val preferenceStore: PreferenceStore,
) {

    fun baseStorageDirectory() = preferenceStore.getString(Preference.appStateKey("storage_dir"), folderProvider.path())
}
