package com.kupriyanov.vknews.presentation.main

import androidx.compose.foundation.clickable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.kupriyanov.vknews.navigation.AppNavGraph
import com.kupriyanov.vknews.navigation.rememberNavigationState
import com.kupriyanov.vknews.presentation.comments.CommentsScreen

@Composable
fun MainScreen() {
    val navigationState = rememberNavigationState()

    Scaffold(
        bottomBar = { BottomBar(navigationState = navigationState) }
    ) { paddingValues ->

        AppNavGraph(
            navHostController = navigationState.navHostController,
            newsFeedScreenContent = {
                NewsFeedScreen(
                    paddingValues = paddingValues,
                    onCommentClickListener = {
                        navigationState.navigateToComments(it)
                    }
                )
            },
            commentsScreenContent = { feedPost ->
                CommentsScreen(
                    onBackPressed = {
                        navigationState.navHostController.popBackStack()
                    },
                    feedPost = feedPost
                )
            },
            favouriteScreenContent = { TextCounter(name = "Favourite") },
            profileScreenContent = { TextCounter(name = "Profile") }
        )
    }
}

@Composable
fun TextCounter(name: String) {
    var count by rememberSaveable() {
        mutableStateOf(0)
    }
    Text(
        modifier = Modifier.clickable { count++ },
        text = "$name Count: $count",
        color = MaterialTheme.colors.onPrimary
    )
}











