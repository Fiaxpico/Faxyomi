package eu.fiax.presentation.category

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import eu.fiax.presentation.category.components.CategoryFloatingActionButton
import eu.fiax.presentation.category.components.sources.SourceCategoryContent
import eu.fiax.presentation.components.AppBar
import eu.fiax.faxyomi.ui.category.sources.SourceCategoryScreenState
import faxyomi.i18n.MR
import faxyomi.i18n.sy.SYMR
import faxyomi.presentation.core.components.material.Scaffold
import faxyomi.presentation.core.components.material.padding
import faxyomi.presentation.core.components.material.topSmallPaddingValues
import faxyomi.presentation.core.i18n.stringResource
import faxyomi.presentation.core.screens.EmptyScreen
import faxyomi.presentation.core.util.plus

@Composable
fun SourceCategoryScreen(
    state: SourceCategoryScreenState.Success,
    onClickCreate: () -> Unit,
    onClickRename: (String) -> Unit,
    onClickDelete: (String) -> Unit,
    navigateUp: () -> Unit,
) {
    val lazyListState = rememberLazyListState()
    Scaffold(
        topBar = { scrollBehavior ->
            AppBar(
                navigateUp = navigateUp,
                title = stringResource(MR.strings.action_edit_categories),
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
                SYMR.strings.no_source_categories,
                modifier = Modifier.padding(paddingValues),
            )
            return@Scaffold
        }

        SourceCategoryContent(
            categories = state.categories,
            lazyListState = lazyListState,
            paddingValues = paddingValues + topSmallPaddingValues +
                PaddingValues(horizontal = MaterialTheme.padding.medium),
            onClickRename = onClickRename,
            onClickDelete = onClickDelete,
        )
    }
}
