package com.pdm.cats.presentation.petdetails

import android.util.Log
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.pdm.cats.domain.models.CatModel
import com.pdm.cats.presentation.components.CustomTopBarWithBack

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.PetDetailsScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    catModel: CatModel,
    onBackPressed: () -> Unit
) {

    Log.d("CAT", catModel.toString())

    Scaffold(topBar = {
        CustomTopBarWithBack(
            title = "Cat Details",
            onBackPressed = onBackPressed
        )
    },
        content = { paddingValues ->
            PetDetailsScreenContent(
                animatedVisibilityScope,
                modifier = Modifier
                    .padding(paddingValues),
                imageUrl = catModel.url,
                name = catModel.breeds[0].name,
                description = catModel.breeds[0].description,
                temperament = catModel.breeds[0].temperament,
                weight = catModel.breeds[0].weight.metric,
                origin = catModel.breeds[0].origin,
                lifeSpan = catModel.breeds[0].lifeSpan,
                adaptability = catModel.breeds[0].adaptability,
                affectionLevel = catModel.breeds[0].affectionLevel,
                energyLevel = catModel.breeds[0].energyLevel,
                vetstreetUrl = catModel.breeds[0].vetstreetUrl,
                cfaUrl = catModel.breeds[0].cfaUrl,
                vcahospitalsUrl = catModel.breeds[0].vcahospitalsUrl,
                id = catModel.id
            )
        }
    )
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.PetDetailsScreenContent(
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
    imageUrl: String,
    name: String,
    description: String,
    temperament: String,
    weight: String,
    origin: String,
    lifeSpan: String,
    adaptability: Int,
    affectionLevel: Int,
    energyLevel: Int,
    cfaUrl: String?,
    vetstreetUrl: String?,
    vcahospitalsUrl: String?,
    id: String
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
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
                .clip(MaterialTheme.shapes.medium)
                .sharedElement(
                    state = rememberSharedContentState(key = "image/$id"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = { _, _ ->
                        tween(durationMillis = 500)
                    }
                ),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Name: $name",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = description.ifEmpty { "No description available." },
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (temperament.isNotEmpty()) {
            Text(
                text = "Temperament:",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.Start)
            )
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            ) {
                temperament.split(",").forEach { temp ->
                    SuggestionChip(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        onClick = { },
                        label = { Text(text = temp.trim()) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Weight: $weight",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.Start)
        )
        Text(
            text = "Origin: $origin",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Life Span: $lifeSpan years",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.Start)
        )
        Text(
            text = "Adaptability: $adaptability/5",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.Start)
        )
        Text(
            text = "Affection Level: $affectionLevel/5",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.Start)
        )
        Text(
            text = "Energy Level: $energyLevel/5",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Learn more:",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.align(Alignment.Start)
        )
        Column(
            modifier = Modifier.align(Alignment.Start)
        ) {
            if (cfaUrl != null) {
                if (cfaUrl.isNotEmpty()) {
                    Text(
                        text = "CFA: $cfaUrl",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable { }
                    )
                }
            }
            if (vetstreetUrl != null) {
                if (vetstreetUrl.isNotEmpty()) {
                    Text(
                        text = "Vetstreet: $vetstreetUrl",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable { }
                    )
                }
            }
            if (vcahospitalsUrl != null) {
                if (vcahospitalsUrl.isNotEmpty()) {
                    Text(
                        text = "VCA Hospitals: $vcahospitalsUrl",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable { }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

//@Preview(showBackground = true, name = "Pet Details Screen")
//@Composable
//fun PreviewPetDetailsScreenContent() {
//    PetDetailsScreenContent(
//        imageUrl = "https://placekitten.com/800/400",
//        name = "Turkish Van",
//        description = "The Turkish Van is a breed known for its love of water and energetic personality. This is a beautiful and unique breed from Turkey.",
//        temperament = "Agile, Intelligent, Loyal, Playful, Energetic",
//        weight = "7 - 20 lbs (3 - 9 kg)",
//        origin = "Turkey",
//        lifeSpan = "12 - 17",
//        adaptability = 5,
//        affectionLevel = 5,
//        energyLevel = 5,
//        cfaUrl = "http://cfa.org/Breeds/BreedsSthruT/TurkishVan.aspx",
//        vetstreetUrl = "http://www.vetstreet.com/cats/turkish-van",
//        vcahospitalsUrl = "https://vcahospitals.com/know-your-pet/cat-breeds/turkish-van"
//    )
//}