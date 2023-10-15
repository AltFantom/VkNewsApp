package com.kupriyanov.vknews.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kupriyanov.vknews.R
import com.kupriyanov.vknews.domain.FeedPost
import com.kupriyanov.vknews.domain.StatisticItem
import com.kupriyanov.vknews.domain.StatisticType

@Composable
fun PostCard(
    modifier: Modifier = Modifier,
    feedPost: FeedPost = FeedPost(),
    onViewsClickListener: (StatisticItem) -> Unit,
    onSharesClickListener: (StatisticItem) -> Unit,
    onCommentsClickListener: (StatisticItem) -> Unit,
    onLikesClickListener: (StatisticItem) -> Unit
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(all = 8.dp)
        ) {
            PostHat(feedPost)
            Spacer(modifier = Modifier.height(8.dp))
            PostText(feedPost)
            Spacer(modifier = Modifier.height(8.dp))
            PostImage(feedPost)
            Spacer(modifier = Modifier.height(8.dp))
            Statistics(
                feedPost.statistics,
                onLikesClickListener = onLikesClickListener,
                onCommentsClickListener = onCommentsClickListener,
                onSharesClickListener = onSharesClickListener,
                onViewsClickListener = onViewsClickListener,
            )
        }
    }
}

@Composable
private fun PostHat(feedPost: FeedPost) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(50.dp)
                .clip(shape = CircleShape),
            painter = painterResource(id = feedPost.avatarResId),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .height(50.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = feedPost.communityName,
                color = MaterialTheme.colors.onPrimary
            )
            Text(
                text = feedPost.publicationDate,
                color = MaterialTheme.colors.onSecondary
            )
        }
        Icon(
            imageVector = Icons.Rounded.MoreVert,
            contentDescription = null,
            tint = MaterialTheme.colors.onSecondary
        )
    }
}

@Composable
private fun PostText(feedPost: FeedPost) {
    Text(
        text = feedPost.contentText,
        color = MaterialTheme.colors.onPrimary
    )
}

@Composable
private fun PostImage(feedPost: FeedPost) {
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        painter = painterResource(id = feedPost.contentImageResId),
        contentDescription = null,
        contentScale = ContentScale.FillWidth
    )
}

@Composable
private fun Statistics(
    statisticItems: List<StatisticItem>,
    onViewsClickListener: (StatisticItem) -> Unit,
    onSharesClickListener: (StatisticItem) -> Unit,
    onCommentsClickListener: (StatisticItem) -> Unit,
    onLikesClickListener: (StatisticItem) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.weight(1f)
        ) {
            val viewsItem = statisticItems.getItemByType(StatisticType.VIEWS)
            IconWithText(
                iconResId = R.drawable.ic_views_count,
                text = "${viewsItem.count}",
                onItemClickListener = {
                    onViewsClickListener(viewsItem)
                }
            )
        }
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val sharesItem = statisticItems.getItemByType(StatisticType.SHARES)
            IconWithText(
                iconResId = R.drawable.ic_shares,
                text = "${sharesItem.count}",
                onItemClickListener = {
                    onSharesClickListener(sharesItem)
                }
            )
            val commentsItem = statisticItems.getItemByType(StatisticType.COMMENTS)
            IconWithText(
                iconResId = R.drawable.ic_comments,
                text = "${commentsItem.count}",
                onItemClickListener = {
                    onCommentsClickListener(commentsItem)
                }
            )
            val likesItem = statisticItems.getItemByType(StatisticType.LIKES)
            IconWithText(
                iconResId = R.drawable.ic_likes,
                text = "${likesItem.count}",
                onItemClickListener = {
                    onLikesClickListener(likesItem)
                }
            )
        }
    }
}

private fun List<StatisticItem>.getItemByType(type: StatisticType): StatisticItem {
    return this.find { it.type == type } ?: throw RuntimeException("Type not found")
}

@Composable
private fun IconWithText(
    iconResId: Int,
    text: String,
    onItemClickListener: () -> Unit
) {
    Row(
        modifier = Modifier.clickable {
            onItemClickListener()
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            tint = MaterialTheme.colors.onSecondary
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            fontSize = 16.sp,
            color = MaterialTheme.colors.onSecondary
        )
    }
}