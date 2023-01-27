package com.kupriyanov.vknews.ui.theme

import com.kupriyanov.vknews.domain.FeedPost
import com.kupriyanov.vknews.domain.PostComment

sealed class CommentsScreenState {

    object Initial : CommentsScreenState()

    data class Comments(val comments: List<PostComment>, val feedPost: FeedPost) : CommentsScreenState()
}
