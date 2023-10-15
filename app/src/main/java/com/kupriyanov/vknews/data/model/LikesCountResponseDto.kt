package com.kupriyanov.vknews.data.model

import com.google.gson.annotations.SerializedName

data class LikesCountResponseDto(
    @SerializedName("response") val likesCountDto: LikesCountDto
)
