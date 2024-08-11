package org.schabi.newpipe.prefs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.schabi.newpipe.R
import org.schabi.newpipe.ui.theme.AppTheme

private val GroupHeaderStartPadding = 16.dp
private val PrefItemMinHeight = 72.dp

@Composable
fun PreferenceGroupHeader(
    modifier: Modifier = Modifier,
    title: String,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Box(
        Modifier
            .padding(start = GroupHeaderStartPadding)
            .fillMaxWidth()
            .then(modifier),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            title,
            color = color,
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Composable
fun GenericPreference(
    title: String,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    summary: String? = null,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    placeholderSpaceForLeadingIcon: Boolean = true
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(20))
            .clickable(
                onClick = onClick ?: {}
            )
            .heightIn(min = PrefItemMinHeight)
            .fillMaxWidth()
            .padding(contentPadding)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (leadingIcon == null && placeholderSpaceForLeadingIcon) {
            Box(Modifier.minimumInteractiveComponentSize()) {}
        } else if (leadingIcon != null) {
            Box(
                modifier = Modifier
                    .minimumInteractiveComponentSize()
                    .align(Alignment.Top),
                contentAlignment = Alignment.Center
            ) {
                leadingIcon()
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = title,
                fontSize = 19.sp
            )
            if (summary != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = summary,
                    fontSize = 15.sp,
                    modifier = Modifier.wrapContentWidth()
                )
            }
        }
        if (trailingContent != null) {
            Spacer(
                modifier = Modifier
                    .width(8.dp)
            )
            trailingContent()
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ListPreferencePreview() {
    val items = remember {
        listOf("Some Pref val 1", "Some Pref val 2", "Some Pref val 3")
    }
    var selectedItemIndex by remember {
        mutableIntStateOf(0)
    }
    var isSelected by remember {
        mutableStateOf(false)
    }
    AppTheme {
        Surface {
            Column(Modifier.verticalScroll(rememberScrollState())) {
                PreferenceGroupHeader(
                    title = "Some Other Prefs",
                    color = MaterialTheme.colorScheme.onSurface
                )
                GenericPreference(
                    title = "Some Inner Screen",
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null
                        )
                    },
                    summary = "Theme related settings"
                )
                HorizontalDivider()
                SwitchPreference(
                    title = "Some switch pref",
                    isChecked = isSelected,
                    onCheckedChange = { isSelected = !isSelected },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Person, contentDescription = null)
                    },
                    summary = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text",
                    placeholderForIcon = true
                )
                PreferenceGroupHeader(
                    title = "Some Prefs",
                    color = MaterialTheme.colorScheme.onSurface
                )
                for (i in 0 until 5) {
                    ListPreference(
                        title = "Some Pref",
                        items = items,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = null
                            )
                        },
                        selectedItemIndex = selectedItemIndex,
                        onItemSelection = { selectedItemIndex = it },
                        itemToDescription = { items[it] }
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun <T> ListPreference(
    title: String,
    items: List<T>,
    selectedItemIndex: Int,
    onItemSelection: (Int) -> Unit,
    itemToDescription: @Composable (Int) -> String,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
    placeholderForIcon: Boolean = true,
    selectItemOnClick: Boolean = true
) {
    val isShowingSelectionDialog = remember {
        mutableStateOf(false)
    }
    GenericPreference(
        title = title,
        leadingIcon = leadingIcon,
        summary = itemToDescription(selectedItemIndex),
        modifier = modifier,
        placeholderSpaceForLeadingIcon = placeholderForIcon,
        onClick = {
            isShowingSelectionDialog.value = true
        },
    )
    if (isShowingSelectionDialog.value) {
        var dialogSelectedItemIndex by remember {
            mutableIntStateOf(selectedItemIndex)
        }
        AlertDialog(
            onDismissRequest = {
                isShowingSelectionDialog.value = false
            },
            title = {
                Text(text = title)
            },
            text = {
                Column(
                    Modifier
                        .selectableGroup()
                        .verticalScroll(rememberScrollState())
                ) {
                    items.forEachIndexed { index, choice ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .selectable(
                                    selected = (index == dialogSelectedItemIndex),
                                    onClick = {
                                        dialogSelectedItemIndex = index
                                        if (selectItemOnClick) {
                                            onItemSelection(index)
                                        }
                                    },
                                    role = Role.RadioButton
                                )
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (index == dialogSelectedItemIndex),
                                onClick = null
                            )
                            Text(
                                text = itemToDescription(index),
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    if (!selectItemOnClick) {
                        onItemSelection(dialogSelectedItemIndex)
                    }
                    isShowingSelectionDialog.value = false
                }) {
                    Text(text = stringResource(id = R.string.ok))
                }
            },
            dismissButton = {
                TextButton(onClick = { isShowingSelectionDialog.value = false }) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            }
        )
    }
}

@Composable
fun SwitchPreference(
    title: String,
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    summary: String? = null,
    onCheckedChange: ((Boolean) -> Unit)? = null,
    isEnabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    placeholderForIcon: Boolean = true,
) {
    println(" 4523423 SwitchPreference isChecked: $isChecked")
    GenericPreference(
        title = title,
        onClick = {
            if (onCheckedChange != null) {

                println(" 4523423 SwitchPreference set isChecked: ${!isChecked}")
                onCheckedChange(!isChecked)
            }
        },
        modifier = modifier,
        summary = summary,
        leadingIcon = leadingIcon,
        placeholderSpaceForLeadingIcon = placeholderForIcon,
        trailingContent = {

            println("4523423 calling Switch isChecked: $isChecked")
            Switch(
                modifier = modifier,
                checked = isChecked,
                onCheckedChange = null,
                enabled = isEnabled
            )
        }
    )
}
