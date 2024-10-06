package com.htcmw.my_good_idea_api.entity

import com.htcmw.my_good_idea_api.util.TsidUtil
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "IDEAS")
class IdeaEntity(
    @Id
    val id: String = TsidUtil.generateTsid(),
    var supurId: String? = null,
    var rootId: String? = id,
    var workspaceId: String,
    var title: String,
    var content: String
) : BaseEntity() {
}