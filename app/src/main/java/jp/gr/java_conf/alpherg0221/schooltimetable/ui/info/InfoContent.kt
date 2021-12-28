package jp.gr.java_conf.alpherg0221.schooltimetable.ui.info

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.google.accompanist.insets.navigationBarsPadding
import jp.gr.java_conf.alpherg0221.compose.material.InsetAwareTopAppBar
import jp.gr.java_conf.alpherg0221.compose.material.PreferencesItem
import jp.gr.java_conf.alpherg0221.schooltimetable.R


@Composable
fun InfoContent(
    onBack: () -> Unit,
    onVersionClick: () -> Unit,
    onOSSClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            InsetAwareTopAppBar(
                title = { Text(text = stringResource(id = R.string.info)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
    ) {
        Column(modifier = Modifier.navigationBarsPadding()) {
            PreferencesItem(
                title = stringResource(id = R.string.version),
                subtitle = stringResource(id = R.string.version_sub),
                onClick = onVersionClick,
            )
            PreferencesItem(
                title = stringResource(id = R.string.oss),
                subtitle = stringResource(id = R.string.oss_sub),
                onClick = onOSSClick,
            )
            PreferencesItem(
                title = stringResource(id = R.string.privacy_policy),
                onClick = onPrivacyPolicyClick
            )
        }
    }
}