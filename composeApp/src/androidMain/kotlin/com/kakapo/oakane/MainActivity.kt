package com.kakapo.oakane

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme
import com.kakapo.oakane.presentation.feature.navigation.OakaneNavHost
import com.kakapo.oakane.presentation.navigation.DrawerMenuNavigation
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val appState = rememberAppState()
            AppTheme {
                OakaneApp(appState)
            }
        }
    }
}

@Composable
private fun OakaneApp(
    appState: OakaneAppState
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val openDrawer: () -> Unit = { scope.launch { drawerState.open() } }
    val closeDrawer: () -> Unit = { scope.launch { drawerState.close() } }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = appState.isDashboardRoute,
        content = {
            OakaneNavHost(navController = appState.navController, openDrawer = openDrawer)
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
