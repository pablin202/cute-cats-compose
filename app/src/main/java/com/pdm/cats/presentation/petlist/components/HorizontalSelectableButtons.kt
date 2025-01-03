package com.pdm.cats.presentation.petlist.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pdm.cats.presentation.components.CustomSelectableButton

@Composable
fun HorizontalSelectableButtons(
    modifier: Modifier = Modifier,
    items: List<Pair<String, String>>,
    selectedValue: String,
    onSelectionChanged: (String) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CustomSelectableButton(
            text = "All",
            value = "All",
            selectedValue = selectedValue,
            onSelectionChanged = onSelectionChanged,
            modifier = Modifier
        )
        items.forEach { (text, value) ->
            CustomSelectableButton(
                text = text,
                value = value,
                selectedValue = selectedValue,
                onSelectionChanged = onSelectionChanged,
                modifier = Modifier
            )
        }
    }
}