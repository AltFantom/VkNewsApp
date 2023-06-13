package com.kupriyanov.vknews.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.kupriyanov.vknews.domain.FeedPost
import com.kupriyanov.vknews.navigation.AppNavGraph
import com.kupriyanov.vknews.navigation.rememberNavigationState

@Composable
fun MainScreen() {

    val navigationState = rememberNavigationState()
    val commentsToPost: MutableState<FeedPost?> = remember() {
        mutableStateOf(null)
    }

    Scaffold(
        bottomBar = { BottomBar(navigationState = navigationState) }
    ) { paddingValues ->

        AppNavGraph(
            navHostController = navigationState.navHostController,
            newsFeedScreenContent = {
                HomeScreen(
                    paddingValues = paddingValues,
                    onCommentClickListener = {
                        commentsToPost.value = it
                        navigationState.navigateToComments()
                    }
                )
            },
            commentsScreenContent = {
                CommentsScreen(
                    onBackPressed = {
                        navigationState.navHostController.popBackStack()
                    },
                    feedPost = commentsToPost.value!!
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











