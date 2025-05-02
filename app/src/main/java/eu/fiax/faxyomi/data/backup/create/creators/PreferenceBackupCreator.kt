package eu.fiax.faxyomi.data.backup.create.creators

import eu.fiax.faxyomi.data.backup.models.BackupPreference
import eu.fiax.faxyomi.data.backup.models.BackupSourcePreferences
import eu.fiax.faxyomi.data.backup.models.BooleanPreferenceValue
import eu.fiax.faxyomi.data.backup.models.FloatPreferenceValue
import eu.fiax.faxyomi.data.backup.models.IntPreferenceValue
import eu.fiax.faxyomi.data.backup.models.LongPreferenceValue
import eu.fiax.faxyomi.data.backup.models.StringPreferenceValue
import eu.fiax.faxyomi.data.backup.models.StringSetPreferenceValue
import eu.fiax.faxyomi.source.ConfigurableSource
import eu.fiax.faxyomi.source.preferenceKey
import eu.fiax.faxyomi.source.sourcePreferences
import faxyomi.core.common.preference.Preference
import faxyomi.core.common.preference.PreferenceStore
import faxyomi.domain.source.service.SourceManager
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get

class PreferenceBackupCreator(
    private val sourceManager: SourceManager = Injekt.get(),
    private val preferenceStore: PreferenceStore = Injekt.get(),
) {

    fun createApp(includePrivatePreferences: Boolean): List<BackupPreference> {
        return preferenceStore.getAll().toBackupPreferences()
            .withPrivatePreferences(includePrivatePreferences)
    }

    fun createSource(includePrivatePreferences: Boolean): List<BackupSourcePreferences> {
        return sourceManager.getCatalogueSources()
            .filterIsInstance<ConfigurableSource>()
            .map {
                BackupSourcePreferences(
                    it.preferenceKey(),
                    it.sourcePreferences().all.toBackupPreferences()
                        .withPrivatePreferences(includePrivatePreferences),
                )
            }
            .filter { it.prefs.isNotEmpty() }
    }

    @Suppress("UNCHECKED_CAST")
    private fun Map<String, *>.toBackupPreferences(): List<BackupPreference> {
        return this
            .filterKeys { !Preference.isAppState(it) }
            .mapNotNull { (key, value) ->
                when (value) {
                    is Int -> BackupPreference(key, IntPreferenceValue(value))
                    is Long -> BackupPreference(key, LongPreferenceValue(value))
                    is Float -> BackupPreference(key, FloatPreferenceValue(value))
                    is String -> BackupPreference(key, StringPreferenceValue(value))
                    is Boolean -> BackupPreference(key, BooleanPreferenceValue(value))
                    is Set<*> -> (value as? Set<String>)?.let {
                        BackupPreference(key, StringSetPreferenceValue(it))
                    }
                    else -> null
                }
            }
    }

    private fun List<BackupPreference>.withPrivatePreferences(include: Boolean) =
        if (include) {
            this
        } else {
            this.filter { !Preference.isPrivate(it.key) }
        }
}
