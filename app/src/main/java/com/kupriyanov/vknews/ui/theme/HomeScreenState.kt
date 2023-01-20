package com.kupriyanov.vknews.ui.theme

import com.kupriyanov.vknews.domain.FeedPost
import com.kupriyanov.vknews.domain.PostComment

sealed class HomeScreenState {

    object Initial : HomeScreenState()

    data class Posts(val posts: List<FeedPost>) : HomeScreenState()

    data class Comments(val comments: List<PostComment>, val post: FeedPost) : HomeScreenState()
}
