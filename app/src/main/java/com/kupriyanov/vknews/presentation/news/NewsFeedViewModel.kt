package com.kupriyanov.vknews.presentation.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kupriyanov.vknews.data.NewsFeedRepository
import com.kupriyanov.vknews.domain.FeedPost
import com.kupriyanov.vknews.domain.StatisticItem
import kotlinx.coroutines.launch

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {

    private val initialState = NewsFeedScreenState.Initial

    private val _screenState = MutableLiveData<NewsFeedScreenState>(initialState)
    val screenState: LiveData<NewsFeedScreenState> = _screenState

    private val newsFeedRepository = NewsFeedRepository(application)

    init {
        loadRecommendations()
    }

    private fun loadRecommendations() {
        viewModelScope.launch {
            val feedPosts = newsFeedRepository.loadRecommendations()
            _screenState.value = NewsFeedScreenState.Posts(feedPosts)
        }
    }

    fun changeLikeStatus(feedPost: FeedPost) {
        viewModelScope.launch {
            newsFeedRepository.changeLikeStatus(feedPost)
            _screenState.value = NewsFeedScreenState.Posts(newsFeedRepository.feedPosts)
        }
    }

    fun updatePosts(feedPost: FeedPost, item: StatisticItem) {
        val currentState = screenState.value
        if (currentState !is NewsFeedScreenState.Posts) return

        val oldPosts = currentState.posts.toMutableList()
        oldPosts.replaceAll { post ->
            if (feedPost.id == post.id) {
                updateStatistics(post, item)
            } else {
                post
            }
        }
        _screenState.value = NewsFeedScreenState.Posts(oldPosts)
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
        val currentState = screenState.value
        if (currentState !is NewsFeedScreenState.Posts) return

        val modifiedList = currentState.posts.toMutableList()
        modifiedList.remove(feedPost)
        _screenState.value = NewsFeedScreenState.Posts(modifiedList)
    }
}