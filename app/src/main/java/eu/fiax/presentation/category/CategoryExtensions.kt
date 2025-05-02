package eu.fiax.presentation.category

import android.content.Context
import androidx.compose.runtime.Composable
import faxyomi.core.common.i18n.stringResource
import faxyomi.domain.category.model.Category
import faxyomi.i18n.MR
import faxyomi.presentation.core.i18n.stringResource

val Category.visualName: String
    @Composable
    get() = when {
        isSystemCategory -> stringResource(MR.strings.label_default)
        else -> name
    }

fun Category.visualName(context: Context): String =
    when {
        isSystemCategory -> context.stringResource(MR.strings.label_default)
        else -> name
    }
