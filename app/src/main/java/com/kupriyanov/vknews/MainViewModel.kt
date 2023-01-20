package com.kupriyanov.vknews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kupriyanov.vknews.domain.FeedPost
import com.kupriyanov.vknews.domain.PostComment
import com.kupriyanov.vknews.domain.StatisticItem
import com.kupriyanov.vknews.ui.theme.HomeScreenState
import com.kupriyanov.vknews.ui.theme.NavigationItem

class MainViewModel : ViewModel() {

    private val postsList = mutableListOf<FeedPost>().apply {
        repeat(10) {
            add(
                FeedPost(
                    id = it,
                    communityName = "/dev/null $it"
                )
            )
        }
    }

    private val commentsList = mutableListOf<PostComment>().apply {
        repeat(10) {
            add(
                PostComment(
                    id = it
                )
            )
        }
    }

    private val initialState = HomeScreenState.Posts(postsList)

    private val _homeScreenState = MutableLiveData<HomeScreenState>(initialState)
    val homeScreenState: LiveData<HomeScreenState> = _homeScreenState

    private var savedState: HomeScreenState? = initialState

    fun showComments(post: FeedPost) {
        savedState = homeScreenState.value
        _homeScreenState.value = HomeScreenState.Comments(post = post, comments = commentsList)
    }

    fun closeComments() {
        _homeScreenState.value = savedState
    }

    fun updatePosts(feedPost: FeedPost, item: StatisticItem) {
        val currentState = homeScreenState.value
        if (currentState !is HomeScreenState.Posts) return

        val oldPosts = currentState.posts.toMutableList()
        oldPosts.replaceAll { post ->
            if (feedPost.id == post.id) {
                updateStatistics(post, item)
            } else {
                post
            }
        }
        _homeScreenState.value = HomeScreenState.Posts(oldPosts)
    }

    private fun updateStatistics(feedPost: FeedPost, item: StatisticItem): FeedPost {
        val oldStatistics = feedPost.statistics
        val newStatistics = oldStatistics.map { oldItem ->
            if (oldItem.type == item.type) {
                oldItem.copy(count = oldItem.count + 1)
            } else {
                oldItem
            }
        }
        return feedPost.copy(statistics = newStatistics)
    }


    fun deletePost(feedPost: FeedPost) {
        val currentState = homeScreenState.value
        if (currentState !is HomeScreenState.Posts) return

        val modifiedList = currentState.posts.toMutableList()
        modifiedList.remove(feedPost)
        _homeScreenState.value = HomeScreenState.Posts(modifiedList)
    }
}