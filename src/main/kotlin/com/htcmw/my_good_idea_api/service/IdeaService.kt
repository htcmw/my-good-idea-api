package com.htcmw.my_good_idea_api.service

import com.htcmw.my_good_idea_api.entity.IdeaEntity
import com.htcmw.my_good_idea_api.repository.IdeaRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class IdeaService(
    private val ideaRepository: IdeaRepository
) {
    fun getIdea(id: String): Idea {
        return ideaRepository.findById(id)
            .orElseThrow { EntityNotFoundException("SuperIdea with super ID $id not found") }
            .let {
                mapping(it)
            }
    }

    @Transactional
    fun create(ideaCreationCommand: IdeaCreationCommand): Idea {
        // 중복된 IdeaEntity 저장 로직을 하나의 함수로 추출
        val ideaEntity = ideaCreationCommand.supurId
            ?.let {
                val superIdea = ideaRepository.findById(it)
                    .orElseThrow { IllegalArgumentException("SuperIdea with super ID $it not found") }

                IdeaEntity(
                    supurId = superIdea.id,
                    rootId = superIdea.rootId,
                    workspaceId = ideaCreationCommand.workspaceId,
                    title = ideaCreationCommand.title,
                    content = ideaCreationCommand.content
                )
            }
            ?: IdeaEntity(
                workspaceId = ideaCreationCommand.workspaceId,
                title = ideaCreationCommand.title,
                content = ideaCreationCommand.content
            )

        ideaRepository.save(ideaEntity)

        return mapping(ideaEntity)

    }

    @Transactional
    fun update(ideaUpdateCommand: IdeaUpdateCommand): Idea {
        val ideaById = ideaUpdateCommand.id
            .let {
                ideaRepository.findById(ideaUpdateCommand.id)
                    .orElseThrow { IllegalArgumentException("Idea with ID $it not found") }
            }

        val rootId = ideaUpdateCommand.superId
            ?.let {
                ideaRepository.findById(it)
                    .orElseThrow { IllegalArgumentException("SuperIdea with super ID $it not found") }
                    .rootId
            } ?: ideaById.id


        ideaById.apply {
            this.supurId = ideaUpdateCommand.superId
            this.rootId = rootId
            this.title = ideaUpdateCommand.title
            this.content = ideaUpdateCommand.content
        }

        return mapping(ideaById)
    }

    private fun mapping(ideaEntity: IdeaEntity): Idea {
        return Idea(
            id = ideaEntity.id,
            supurId = ideaEntity.supurId,
            rootId = ideaEntity.rootId!!,
            workspaceId = ideaEntity.workspaceId,
            title = ideaEntity.title,
            content = ideaEntity.content,
            createdAt = ideaEntity.createdAt,
            createdBy = ideaEntity.createdBy,
            updatedAt = ideaEntity.updatedAt,
            updatedBy = ideaEntity.updatedBy
        )
    }
}

