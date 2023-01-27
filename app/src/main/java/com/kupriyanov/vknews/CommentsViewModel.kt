package com.kupriyanov.vknews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kupriyanov.vknews.domain.FeedPost
import com.kupriyanov.vknews.domain.PostComment
import com.kupriyanov.vknews.ui.theme.CommentsScreenState

class CommentsViewModel(
    feedPost: FeedPost
) : ViewModel() {

    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)
    val screenState: LiveData<CommentsScreenState> = _screenState

    init {
        loadComments(feedPost)
    }

    private fun loadComments(feedPost: FeedPost) {
        val comments = mutableListOf<PostComment>().apply {
            repeat(10) {
                add(
                    PostComment(id = it)
                )
            }
        }
        _screenState.value = CommentsScreenState.Comments(
            comments = comments,
            feedPost = feedPost
        )
    }
}