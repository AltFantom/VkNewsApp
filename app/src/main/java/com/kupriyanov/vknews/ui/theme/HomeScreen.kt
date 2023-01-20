package com.kupriyanov.vknews.ui.theme

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kupriyanov.vknews.MainViewModel
import com.kupriyanov.vknews.domain.FeedPost

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    paddingValues: PaddingValues
) {
    val stateScreen = viewModel.homeScreenState.observeAsState(HomeScreenState.Initial)

    when (val currentState = stateScreen.value) {
        is HomeScreenState.Posts -> {
            FeedPosts(
                posts = currentState.posts,
                viewModel = viewModel,
                paddingValues = paddingValues
            )
        }
        is HomeScreenState.Comments -> {
            CommentsScreen(
                feedPost = currentState.post,
                comments = currentState.comments,
                onBackPressed = { viewModel.closeComments() }
            )
            BackHandler {
                viewModel.closeComments()
            }
        }
        is HomeScreenState.Initial -> {

        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun FeedPosts(
    posts: List<FeedPost>,
    viewModel: MainViewModel,
    paddingValues: PaddingValues
) {
    LazyColumn(
        modifier = androidx.compose.ui.Modifier.padding(paddingValues),
        contentPadding = PaddingValues(
            start = 8.dp,
            end = 8.dp,
            top = 12.dp,
            bottom = 12.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = posts,
            key = { it.id }
        ) { post ->

            val dismissState = rememberDismissState()

            if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                viewModel.deletePost(post)
            }

            SwipeToDismiss(
                modifier = Modifier.animateItemPlacement(),
                state = dismissState,
                background = {},
                directions = setOf(DismissDirection.EndToStart)
            ) {
                PostCard(
                    feedPost = post,
                    onViewsClickListener = { statisticItem ->
                        viewModel.updatePosts(post, statisticItem)
                    },
                    onSharesClickListener = { statisticItem ->
                        viewModel.updatePosts(post, statisticItem)
                    },
                    onCommentsClickListener = {
                        viewModel.showComments(post)
                    },
                    onLikesClickListener = { statisticItem ->
                        viewModel.updatePosts(post, statisticItem)
                    }
                )
            }
        }
    }
}

















