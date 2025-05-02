package eu.fiax.presentation.category

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import eu.fiax.presentation.category.components.CategoryFloatingActionButton
import eu.fiax.presentation.category.components.biometric.BiometricTimesContent
import eu.fiax.presentation.components.AppBar
import eu.fiax.faxyomi.ui.category.biometric.BiometricTimesScreenState
import eu.fiax.faxyomi.ui.category.biometric.TimeRangeItem
import faxyomi.i18n.sy.SYMR
import faxyomi.presentation.core.components.material.Scaffold
import faxyomi.presentation.core.components.material.padding
import faxyomi.presentation.core.components.material.topSmallPaddingValues
import faxyomi.presentation.core.i18n.stringResource
import faxyomi.presentation.core.screens.EmptyScreen
import faxyomi.presentation.core.util.plus

@Composable
fun BiometricTimesScreen(
    state: BiometricTimesScreenState.Success,
    onClickCreate: () -> Unit,
    onClickDelete: (TimeRangeItem) -> Unit,
    navigateUp: () -> Unit,
) {
    val lazyListState = rememberLazyListState()
    Scaffold(
        topBar = { scrollBehavior ->
            AppBar(
                navigateUp = navigateUp,
                title = stringResource(SYMR.strings.biometric_lock_times),
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
                SYMR.strings.biometric_lock_times_empty,
                modifier = Modifier.padding(paddingValues),
            )
            return@Scaffold
        }

        BiometricTimesContent(
            timeRanges = state.timeRanges,
            lazyListState = lazyListState,
            paddingValues = paddingValues + topSmallPaddingValues +
                PaddingValues(horizontal = MaterialTheme.padding.medium),
            onClickDelete = onClickDelete,
        )
    }
}
