package com.sports.app.data.dto

import com.google.gson.annotations.SerializedName

data class TeamListDto(
    @SerializedName("teams")
    val teams: List<TeamDto?>? = null,
)
