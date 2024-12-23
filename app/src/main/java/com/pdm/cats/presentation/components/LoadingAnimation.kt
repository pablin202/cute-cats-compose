package com.pdm.cats.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.pdm.cats.R

@Composable
fun LoadingAnimation(modifier: Modifier = Modifier) {
    val composition = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition.value,
            iterations = Int.MAX_VALUE
        )
    }
}