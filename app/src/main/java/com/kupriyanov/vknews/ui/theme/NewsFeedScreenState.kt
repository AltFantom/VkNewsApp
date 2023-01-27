package com.kupriyanov.vknews.ui.theme

import com.kupriyanov.vknews.domain.FeedPost

sealed class NewsFeedScreenState {

    object Initial : NewsFeedScreenState()

    data class Posts(val posts: List<FeedPost>) : NewsFeedScreenState()

}
