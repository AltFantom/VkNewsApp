package com.kupriyanov.vknews.presentation.comments

import com.kupriyanov.vknews.domain.FeedPost
import com.kupriyanov.vknews.domain.PostComment

sealed class CommentsScreenState {

    object Initial : CommentsScreenState()

    data class Comments(val comments: List<PostComment>, val feedPost: FeedPost) : CommentsScreenState()
}
