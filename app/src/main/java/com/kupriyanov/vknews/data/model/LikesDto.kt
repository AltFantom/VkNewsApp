package com.kupriyanov.vknews.data.model

import com.google.gson.annotations.SerializedName

data class LikesDto(
    @SerializedName("count") val count: Int
)