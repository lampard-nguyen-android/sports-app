package com.sports.app.data.dto

import com.google.gson.annotations.SerializedName

data class UpcomingMatchDto(
    @SerializedName("away")
    val away: String? = null,
    @SerializedName("date")
    val date: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("home")
    val home: String? = null,
)
