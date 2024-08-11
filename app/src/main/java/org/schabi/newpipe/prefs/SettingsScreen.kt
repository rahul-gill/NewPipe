package org.schabi.newpipe.prefs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import org.schabi.newpipe.R

@Composable
@Preview
fun SettingsScreen() {
    Column(Modifier.background(MaterialTheme.colorScheme.surface)) {
        GenericPreference(
            title = "Video and Audio",
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_headset),
                    contentDescription = null
                )
            },
            onClick = {
            }
        )
        GenericPreference(
            title = "Download",
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_file_download),
                    contentDescription = null
                )
            },
            onClick = {
            }
        )
        GenericPreference(
            title = "Appearance",
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_palette),
                    contentDescription = null
                )
            },
            onClick = {
            }
        )
        GenericPreference(
            title = "History and Cache",
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_settings_backup_restore),
                    contentDescription = null
                )
            },
            onClick = {
            }
        )
        GenericPreference(
            title = "Content",
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_explore),
                    contentDescription = null
                )
            },
            onClick = {
            }
        )
        GenericPreference(
            title = "Notifications",
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_notifications),
                    contentDescription = null
                )
            },
            onClick = {
            }
        )
        GenericPreference(
            title = "Backup and Restore",
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_settings_backup_restore),
                    contentDescription = null
                )
            },
            onClick = {
            }
        )
        GenericPreference(
            title = "Debug",
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_bug_report),
                    contentDescription = null
                )
            },
            onClick = {
            }
        )
    }
}
