package com.kupriyanov.vknews.presentation.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kupriyanov.vknews.domain.FeedPost
import com.kupriyanov.vknews.domain.StatisticItem

class NewsFeedViewModel : ViewModel() {

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

    private val initialState = NewsFeedScreenState.Posts(postsList)

    private val _newsFeedScreenState = MutableLiveData<NewsFeedScreenState>(initialState)
    val newsFeedScreenState: LiveData<NewsFeedScreenState> = _newsFeedScreenState

    fun updatePosts(feedPost: FeedPost, item: StatisticItem) {
        val currentState = newsFeedScreenState.value
        if (currentState !is NewsFeedScreenState.Posts) return

        val oldPosts = currentState.posts.toMutableList()
        oldPosts.replaceAll { post ->
            if (feedPost.id == post.id) {
                updateStatistics(post, item)
            } else {
                post
            }
        }
        _newsFeedScreenState.value = NewsFeedScreenState.Posts(oldPosts)
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
        val currentState = newsFeedScreenState.value
        if (currentState !is NewsFeedScreenState.Posts) return

        val modifiedList = currentState.posts.toMutableList()
        modifiedList.remove(feedPost)
        _newsFeedScreenState.value = NewsFeedScreenState.Posts(modifiedList)
    }
}