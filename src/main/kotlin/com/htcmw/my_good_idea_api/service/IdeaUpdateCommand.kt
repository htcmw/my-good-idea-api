package com.htcmw.my_good_idea_api.service

data class IdeaUpdateCommand(
    val id: String,
    val superId: kotlin.String? = null,
    val title: kotlin.String,
    val content: kotlin.String
)