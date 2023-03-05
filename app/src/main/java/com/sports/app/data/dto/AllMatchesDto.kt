package com.sports.app.data.dto

import com.google.gson.annotations.SerializedName

data class AllMatchesDto(
    @SerializedName("previous")
    val previous: List<PreviousMatchDto?>? = null,
    @SerializedName("upcoming")
    val upcoming: List<UpcomingMatchDto?>? = null,
)
