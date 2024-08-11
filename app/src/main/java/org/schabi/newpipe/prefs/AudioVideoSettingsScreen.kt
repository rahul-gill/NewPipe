package org.schabi.newpipe.prefs

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import org.schabi.newpipe.R

class AudioVideoPrefsManager(private val context: Context) {
    val resolutionPreference = StringPreference(
        context = context,
        key = context.getString(R.string.default_resolution_key),
        defaultValue = context.resources.getStringArray(R.array.resolution_list_values).first()
    )
}

@Preview
@Composable
fun AudioVideoSettingsScreen() {
    val context = LocalContext.current
    val prefs = remember {
        AudioVideoPrefsManager(context)
    }
    val resolutionOptions = stringArrayResource(id = R.array.resolution_list_values).toList()

    Column(Modifier.background(MaterialTheme.colorScheme.surface)) {
        ListPreference(
            title = "Default Resolution",
            items = resolutionOptions,
            selectedItemIndex = resolutionOptions.indexOf(prefs.resolutionPreference.value),
            onItemSelection = { prefs.resolutionPreference.setValue(resolutionOptions[it]) },
            itemToDescription = { resolutionOptions[it] },
            placeholderForIcon = false
        )
    }
}
