package com.kupriyanov.vknews.ui.theme

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kupriyanov.vknews.navigation.NavigationState

@Composable
fun BottomBar(
    navigationState: NavigationState
) {
    BottomNavigation {

        val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()

        val items = listOf(
            NavigationItem.Home,
            NavigationItem.Favourite,
            NavigationItem.Profile
        )
        items.forEach { item ->

            val selected = navBackStackEntry?.destination?.hierarchy?.any {
                it.route == item.screen.route
            } ?: false

            BottomNavigationItem(
                selected = selected,
                onClick = {
                    if (!selected) {
                        navigationState.navigateTo(item.screen.route)
                    }
                },
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