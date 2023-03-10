package com.kupriyanov.vknews.domain

import com.kupriyanov.vknews.R

data class PostComment(
    val id: Int,
    val authorName: String = "Author",
    val authorAvatarId: Int = R.drawable.comment_author_avatar,
    val commentText: String = "Long comment text",
    val publicationData: String = "14:00"
)
