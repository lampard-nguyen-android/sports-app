package com.sports.app.data.dto

import com.google.gson.annotations.SerializedName

data class TeamDto(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("logo")
    val logo: String? = null,
    @SerializedName("name")
    val name: String? = null,
)
