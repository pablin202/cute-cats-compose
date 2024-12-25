package com.pdm.cats.presentation.petdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.pdm.cats.data.dto.Cat
import com.pdm.cats.domain.models.CatModel
import com.pdm.cats.presentation.components.CustomTopBarWithBack

@Composable
fun PetDetailsScreen(
    catModel: CatModel,
    onBackPressed: () -> Unit
) {
    Scaffold(topBar = {
        CustomTopBarWithBack(
            title = "Cat Details",
            onBackPressed = onBackPressed
        )
    },
        content = { paddingValues ->
            PetDetailsScreenContent(
                modifier = Modifier
                    .padding(paddingValues),
                imageUrl = catModel.url,
                name = catModel.breeds[0].name,
                description = catModel.breeds[0].description,
                temperament = catModel.breeds[0].temperament
            )
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PetDetailsScreenContent(
    modifier: Modifier = Modifier,
    imageUrl: String,
    name: String,
    description: String,
    temperament: String
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "Cute cat",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Name: $name",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 8.dp)
        )

        Text(
            text = description.ifEmpty { "No description available." },
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 16.dp)
        )

        if (temperament.isNotEmpty()) {
            Text(
                text = "Temperament:",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 8.dp)
            )

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            ) {
                temperament.split(",").forEach { temp ->
                    SuggestionChip(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        onClick = { /* Acciones opcionales */ },
                        label = { Text(text = temp.trim()) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}