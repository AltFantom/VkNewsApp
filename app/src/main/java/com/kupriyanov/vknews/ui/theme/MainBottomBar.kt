package com.kupriyanov.vknews.ui.theme

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kupriyanov.vknews.navigation.NavigationState
import com.kupriyanov.vknews.navigation.Screen

@Composable
fun BottomBar(
    navigationState: NavigationState
) {
    BottomNavigation {

        val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()

        val currentRoute = navBackStackEntry?.destination?.route

        val items = listOf(
            NavigationItem.Home,
            NavigationItem.Favourite,
            NavigationItem.Profile
        )
        items.forEach { item ->
            BottomNavigationItem(
                selected = item.screen.route == currentRoute,
                onClick = { navigationState.navigateTo(item.screen.route) },
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = null
                    )
                },
                label = {
                    Text(text = stringResource(id = item.titleResId))
                },
                selectedContentColor = MaterialTheme.colors.onPrimary,
                unselectedContentColor = MaterialTheme.colors.onSecondary
            )
        }
    }
}