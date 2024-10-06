package com.htcmw.my_good_idea_api.service

import java.time.Instant

data class Idea(
    var id: String,
    var supurId: String? = null,
    var rootId: String,
    var workspaceId: String,
    var title: String,
    var content: String,
    var createdAt: Instant? = null,
    var createdBy: String? = null,
    var updatedAt: Instant? = null,
    var updatedBy: String? = null
)