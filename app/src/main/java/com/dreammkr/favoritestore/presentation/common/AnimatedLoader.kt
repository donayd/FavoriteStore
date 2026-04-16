package com.dreammkr.favoritestore.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.dreammkr.favoritestore.R

@Composable
fun AnimatedLoader(modifier: Modifier = Modifier) {
    val loaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.loading
        )
    )

    val loaderProgress by animateLottieCompositionAsState(
        loaderLottieComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )


    LottieAnimation(
        composition = loaderLottieComposition,
        progress = loaderProgress,
        modifier = modifier
    )
}