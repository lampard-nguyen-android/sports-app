package com.sports.app.data.dto

import com.google.gson.annotations.SerializedName

data class MatchesResponseDto(
    @SerializedName("matches")
    val matches: AllMatchesDto? = null,
)
