package com.htcmw.my_good_idea_api.repository

import com.htcmw.my_good_idea_api.entity.IdeaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface IdeaRepository : JpaRepository<IdeaEntity, String> {
}