package com.kupriyanov.vknews.data.mapper

import com.kupriyanov.vknews.data.model.NewsFeedResponseDto
import com.kupriyanov.vknews.domain.FeedPost
import com.kupriyanov.vknews.domain.StatisticItem
import com.kupriyanov.vknews.domain.StatisticType
import kotlin.math.absoluteValue

class NewsFeedMapper {
    fun mapResponseToPosts(newsFeedResponseDto: NewsFeedResponseDto): List<FeedPost> {
        val result = mutableListOf<FeedPost>()
        val posts = newsFeedResponseDto.newsFeedContent.posts
        val groups = newsFeedResponseDto.newsFeedContent.groups
        for (post in posts) {
            val group = groups.find {
                it.id == post.communityId.absoluteValue
            } ?: break
            val feedPost = FeedPost(
                id = post.id,
                communityName = group.name,
                publicationDate = post.date.toString(),
                communityImageUrl = group.imageUrl,
                contentText = post.text,
                contentImageUrl = post.attachments?.firstOrNull()?.photo?.photoUrls?.lastOrNull()?.url,
                statistics = listOf(
                    StatisticItem(type = StatisticType.LIKES, post.likes.count),
                    StatisticItem(type = StatisticType.COMMENTS, post.comments.count),
                    StatisticItem(type = StatisticType.SHARES, post.reposts.count),
                    StatisticItem(type = StatisticType.VIEWS, post.views.count),
                )
            )
            result.add(feedPost)
        }
        return result
    }
}