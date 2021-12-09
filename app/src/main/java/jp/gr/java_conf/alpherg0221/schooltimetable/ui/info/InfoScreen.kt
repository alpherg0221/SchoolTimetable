package jp.gr.java_conf.alpherg0221.schooltimetable.ui.info

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue


@Composable
fun InfoScreen(
    infoViewModel: InfoViewModel,
    navigateToOSS: () -> Unit,
    navigateToPrivacyPolicy: () -> Unit,
    onBack: () -> Unit,
) {
    val uiState by infoViewModel.infoUiState.collectAsState()

    InfoContent(
        onBack = onBack,
        onVersionClick = {},
        onOSSClick = navigateToOSS,
        onPrivacyPolicyClick = navigateToPrivacyPolicy,
    )
}