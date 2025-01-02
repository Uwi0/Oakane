package com.kakapo.oakane

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.touchlab.kermit.Logger
import com.kakapo.model.system.Theme
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme
import com.kakapo.oakane.presentation.feature.navigation.OakaneNavHost
import com.kakapo.oakane.presentation.navigation.DrawerMenuNavigation
import com.kakapo.oakane.presentation.viewModel.main.MainEvent
import com.kakapo.oakane.presentation.viewModel.main.MainViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val appState = rememberAppState()
            val viewModel = koinViewModel<MainViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            val isDarkTheme = when (uiState.theme) {
                Theme.System -> isSystemInDarkTheme()
                Theme.Light -> false
                Theme.Dark -> true
            }

            LaunchedEffect(Unit) {
                viewModel.initData()
            }

            AppTheme(darkTheme = isDarkTheme) {
                OakaneApp(
                    appState,
                    onSelectedTheme = { theme ->
                        Logger.d("Theme: $theme")
                        viewModel.handleEvent(MainEvent.OnChange(theme))
                    }
                )
            }
        }
    }
}

@Composable
private fun OakaneApp(
    appState: OakaneAppState,
    onSelectedTheme: (Theme) -> Unit
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val openDrawer: () -> Unit = { scope.launch { drawerState.open() } }
    val closeDrawer: () -> Unit = { scope.launch { drawerState.close() } }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = appState.isDashboardRoute,
        content = {
            OakaneNavHost(
                navController = appState.navController,
                openDrawer = openDrawer,
                onSelectedTheme = onSelectedTheme
            )
        },
        drawerContent = {
            DrawerContent(appState, closeDrawer)
        }
    )
}

@Composable
private fun DrawerContent(
    appState: OakaneAppState,
    closeDrawer: () -> Unit
) {
    val onSelectedMenu: (DrawerMenuNavigation) -> Unit = {
        appState.navigateToCurrentMenu(it)
        closeDrawer()
    }

    ModalDrawerSheet(drawerShape = RectangleShape) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DrawerMenuNavigation.entries.forEach { menu ->
                DrawerMenuItem(
                    menu = menu,
                    onClick = onSelectedMenu,
                    isSelected = appState::isSelected
                )
            }
        }
    }
}

@Composable
private fun DrawerMenuItem(
    menu: DrawerMenuNavigation,
    onClick: (DrawerMenuNavigation) -> Unit,
    isSelected: (DrawerMenuNavigation) -> Boolean
) {
    NavigationDrawerItem(
        label = { Text(text = menu.title) },
        selected = isSelected.invoke(menu),
        onClick = { onClick.invoke(menu) }
    )
}
