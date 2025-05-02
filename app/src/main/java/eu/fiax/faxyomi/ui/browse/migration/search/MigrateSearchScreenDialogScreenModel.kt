package eu.fiax.faxyomi.ui.browse.migration.search

import androidx.compose.runtime.Immutable
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import faxyomi.domain.manga.interactor.GetManga
import faxyomi.domain.manga.model.Manga
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get

class MigrateSearchScreenDialogScreenModel(
    val mangaId: Long,
    getManga: GetManga = Injekt.get(),
) : StateScreenModel<MigrateSearchScreenDialogScreenModel.State>(State()) {

    init {
        screenModelScope.launch {
            val manga = getManga.await(mangaId)!!

            mutableState.update {
                it.copy(manga = manga)
            }
        }
    }

    @Immutable
    data class State(
        val manga: Manga? = null,
    )
}
