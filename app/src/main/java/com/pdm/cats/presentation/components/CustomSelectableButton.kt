package com.pdm.cats.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomSelectableButton(
    text: String,
    value: String,
    selectedValue: String,
    onSelectionChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .wrapContentSize()
    ) {
        Button(
            onClick = { onSelectionChanged(value) },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedValue == value) MaterialTheme.colorScheme.primary else Color.LightGray
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                color = if (selectedValue == value) Color.White else Color.Black,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
        Icon(
            imageVector = Icons.Filled.Pets,
            contentDescription = "Pets Icon",
            tint = if (selectedValue == value) MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f) else Color.Gray.copy(alpha = 0.8f),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .rotate(-30f)
                .offset(y = (-2).dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CustomSelectableButtonPreview() {
    var selectedValue by remember { mutableStateOf("All") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Select Category",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CustomSelectableButton(
                text = "All",
                value = "All",
                selectedValue = selectedValue,
                onSelectionChanged = { selectedValue = it },
                modifier = Modifier.weight(1f)
            )

            CustomSelectableButton(
                text = "Dogs",
                value = "Dogs",
                selectedValue = selectedValue,
                onSelectionChanged = { selectedValue = it },
                modifier = Modifier.weight(1f)
            )

            CustomSelectableButton(
                text = "Cats",
                value = "Cats",
                selectedValue = selectedValue,
                onSelectionChanged = { selectedValue = it },
                modifier = Modifier.weight(1f)
            )
        }
    }
}