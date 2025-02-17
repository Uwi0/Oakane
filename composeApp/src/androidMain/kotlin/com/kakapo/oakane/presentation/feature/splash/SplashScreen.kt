package com.kakapo.oakane.presentation.feature.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakapo.oakane.R
import com.kakapo.oakane.presentation.viewModel.splash.SplashViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun SplashRoute(
    navigateToTermAndService: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToOnBoarding: () -> Unit
) {
    val viewModel = koinViewModel<SplashViewModel>()
    val isAlreadyReadOnBoarding by viewModel.isAlreadyReadOnBoarding.collectAsStateWithLifecycle()
    val isAlreadyReadTermAndService by viewModel.isAlreadyReadTermAndService.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initViewModel()
    }

    SplashScreen(
        isAlreadyReadOnBoarding = isAlreadyReadOnBoarding,
        isAlreadyReadTermAndService = isAlreadyReadTermAndService,
        navigateToTermAndService,
        navigateToHome,
        navigateToOnBoarding
    )
}

@Composable
private fun SplashScreen(
    isAlreadyReadOnBoarding: Boolean,
    isAlreadyReadTermAndService: Boolean,
    navigateToTermAndService: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToOnboarding: () -> Unit
) {
    var isLogoVisible by remember { mutableStateOf(false) }
    var isTextVisible by remember { mutableStateOf(false) }
    var shouldNavigate by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(300)
        isLogoVisible = true

        delay(500)
        isTextVisible = true

        delay(700)
        shouldNavigate = true
    }

    if (shouldNavigate) {
        LaunchedEffect(Unit) {
            delay(1500)
            if (isAlreadyReadTermAndService) {
                if (isAlreadyReadOnBoarding) {
                    navigateToHome.invoke()
                } else {
                    navigateToOnboarding.invoke()
                }
            } else {
                navigateToTermAndService.invoke()
            }
        }
    }

    val logoAlpha by animateFloatAsState(
        targetValue = if (isLogoVisible) 1f else 0f,
        animationSpec = tween(800)
    )
    val textAlpha by animateFloatAsState(
        targetValue = if (isTextVisible) 1f else 0f,
        animationSpec = tween(1000)
    )
    Surface {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.oakane_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(240.dp)
                        .clip(CircleShape)
                        .scale(if (isLogoVisible) 1f else 1.2f)
                        .alpha(logoAlpha)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Oakane",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.alpha(textAlpha)
                )
            }
        }
    }

}