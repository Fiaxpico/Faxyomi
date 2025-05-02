package eu.fiax.presentation.crash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BugReport
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import eu.fiax.presentation.theme.faxyomiPreviewTheme
import eu.fiax.faxyomi.util.CrashLogUtil
import kotlinx.coroutines.launch
import faxyomi.i18n.MR
import faxyomi.presentation.core.components.material.padding
import faxyomi.presentation.core.i18n.stringResource
import faxyomi.presentation.core.screens.InfoScreen

@Composable
fun CrashScreen(
    exception: Throwable?,
    onRestartClick: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    InfoScreen(
        icon = Icons.Outlined.BugReport,
        headingText = stringResource(MR.strings.crash_screen_title),
        subtitleText = stringResource(MR.strings.crash_screen_description, stringResource(MR.strings.app_name)),
        acceptText = stringResource(MR.strings.pref_dump_crash_logs),
        onAcceptClick = {
            scope.launch {
                CrashLogUtil(context).dumpLogs(exception)
            }
        },
        rejectText = stringResource(MR.strings.crash_screen_restart_application),
        onRejectClick = onRestartClick,
    ) {
        Box(
            modifier = Modifier
                .padding(vertical = MaterialTheme.padding.small)
                .clip(MaterialTheme.shapes.small)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant),
        ) {
            Text(
                text = exception.toString(),
                modifier = Modifier
                    .padding(all = MaterialTheme.padding.small),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun CrashScreenPreview() {
    faxyomiPreviewTheme {
        CrashScreen(exception = RuntimeException("Dummy")) {}
    }
}
