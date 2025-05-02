package eu.fiax.presentation.category

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import eu.fiax.presentation.category.components.CategoryFloatingActionButton
import eu.fiax.presentation.category.components.genre.SortTagContent
import eu.fiax.presentation.components.AppBar
import eu.fiax.faxyomi.ui.category.genre.SortTagScreenState
import faxyomi.i18n.sy.SYMR
import faxyomi.presentation.core.components.material.Scaffold
import faxyomi.presentation.core.components.material.padding
import faxyomi.presentation.core.components.material.topSmallPaddingValues
import faxyomi.presentation.core.i18n.stringResource
import faxyomi.presentation.core.screens.EmptyScreen
import faxyomi.presentation.core.util.plus

@Composable
fun SortTagScreen(
    state: SortTagScreenState.Success,
    onClickCreate: () -> Unit,
    onClickDelete: (String) -> Unit,
    onClickMoveUp: (String, Int) -> Unit,
    onClickMoveDown: (String, Int) -> Unit,
    navigateUp: () -> Unit,
) {
    val lazyListState = rememberLazyListState()
    Scaffold(
        topBar = { scrollBehavior ->
            AppBar(
                navigateUp = navigateUp,
                title = stringResource(SYMR.strings.action_edit_tags),
                scrollBehavior = scrollBehavior,
            )
        },
        floatingActionButton = {
            CategoryFloatingActionButton(
                lazyListState = lazyListState,
                onCreate = onClickCreate,
            )
        },
    ) { paddingValues ->
        if (state.isEmpty) {
            EmptyScreen(
                SYMR.strings.information_empty_tags,
                modifier = Modifier.padding(paddingValues),
            )
            return@Scaffold
        }

        SortTagContent(
            tags = state.tags,
            lazyListState = lazyListState,
            paddingValues = paddingValues + topSmallPaddingValues +
                PaddingValues(horizontal = MaterialTheme.padding.medium),
            onClickDelete = onClickDelete,
            onMoveUp = onClickMoveUp,
            onMoveDown = onClickMoveDown,
        )
    }
}
