package com.kupriyanov.vknews.presentation.news

import com.kupriyanov.vknews.domain.FeedPost

sealed class NewsFeedScreenState {

    object Initial : NewsFeedScreenState()

    data class Posts(val posts: List<FeedPost>) : NewsFeedScreenState()

}
