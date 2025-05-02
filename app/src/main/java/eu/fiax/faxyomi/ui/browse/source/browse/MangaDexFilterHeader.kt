package eu.fiax.faxyomi.ui.browse.source.browse

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import faxyomi.i18n.sy.SYMR
import faxyomi.presentation.core.components.SettingsItemsPaddings
import faxyomi.presentation.core.components.material.TextButton
import faxyomi.presentation.core.i18n.stringResource

@Composable
fun MangaDexFilterHeader(
    openMangaDexRandom: () -> Unit,
    openMangaDexFollows: () -> Unit,
) {
    Row(
        Modifier.fillMaxWidth()
            .padding(horizontal = SettingsItemsPaddings.Horizontal),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        TextButton(onClick = openMangaDexRandom) {
            Text(stringResource(SYMR.strings.random))
        }
        TextButton(onClick = openMangaDexFollows) {
            Text(stringResource(SYMR.strings.mangadex_follows))
        }
    }
}
