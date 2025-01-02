package com.pdm.cats.presentation.petlist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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
        Button(
            onClick = {
                onSelectionChanged("All")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedValue == "All") MaterialTheme.colorScheme.primary else Color.LightGray
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "All",
                color = if (selectedValue == "All") Color.White else Color.Black
            )
        }

        items.forEach { (text, value) ->
            Button(
                onClick = {
                    onSelectionChanged(value)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedValue == value) MaterialTheme.colorScheme.primary else Color.LightGray
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = text,
                    color = if (selectedValue == value) Color.White else Color.Black
                )
            }
        }
    }
}