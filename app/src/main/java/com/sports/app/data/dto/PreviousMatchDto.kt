package com.sports.app.data.dto

import com.google.gson.annotations.SerializedName

data class PreviousMatchDto(
    @SerializedName("away")
    val away: String? = null,
    @SerializedName("date")
    val date: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("highlights")
    val highlights: String? = null,
    @SerializedName("home")
    val home: String? = null,
    @SerializedName("winner")
    val winner: String? = null,
)
