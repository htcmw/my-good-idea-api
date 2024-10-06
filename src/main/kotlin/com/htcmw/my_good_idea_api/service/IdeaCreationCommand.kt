package com.htcmw.my_good_idea_api.service

data class IdeaCreationCommand(
    var workspaceId: String,
    var supurId: String? = null,
    var title: String,
    var content: String
)