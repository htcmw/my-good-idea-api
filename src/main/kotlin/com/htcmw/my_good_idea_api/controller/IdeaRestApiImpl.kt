package com.htcmw.my_good_idea_api.controller

import com.htcmw.my_good_idea_api.api.IdeaRestApi
import com.htcmw.my_good_idea_api.model.IdeaCreationRequest
import com.htcmw.my_good_idea_api.model.IdeaCreationResponse
import com.htcmw.my_good_idea_api.model.IdeaDetailResponse
import com.htcmw.my_good_idea_api.model.IdeaUpdateRequest
import com.htcmw.my_good_idea_api.service.IdeaCreationCommand
import com.htcmw.my_good_idea_api.service.IdeaService
import com.htcmw.my_good_idea_api.service.IdeaUpdateCommand
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class IdeaRestApiImpl(
    private val ideaService: IdeaService
) : IdeaRestApi {

    override fun createIdea(ideaCreationRequest: IdeaCreationRequest): ResponseEntity<IdeaCreationResponse> {
        return IdeaCreationCommand(
            workspaceId = ideaCreationRequest.workspaceId,
            supurId = ideaCreationRequest.superId,
            title = ideaCreationRequest.title,
            content = ideaCreationRequest.content
        )
            .let {
                ideaService.create(it)
            }
            .let {
                ResponseEntity.status(HttpStatus.CREATED)
                    .body(IdeaCreationResponse(id = it.id))
            }
    }

    override fun readIdea(id: String): ResponseEntity<IdeaDetailResponse> {
        return ideaService.getIdea(id)
            .let {
                ResponseEntity.status(HttpStatus.OK)
                    .body(
                        IdeaDetailResponse(
                            id = it.id,
                            superId = it.supurId,
                            rootId = it.rootId,
                            workspaceId = it.workspaceId,
                            title = it.title,
                            content = it.content,
                            createdAt = it.createdAt?.toEpochMilli()!!,
                            createdBy = it.createdBy!!,
                            updatedAt = it.updatedAt?.toEpochMilli(),
                            updatedBy = it.updatedBy
                        )
                    )
            }
    }

    override fun updateIdea(id: String, ideaUpdateRequest: IdeaUpdateRequest): ResponseEntity<Any> {
        return IdeaUpdateCommand(
            id = id,
            superId = ideaUpdateRequest.superId,
            title = ideaUpdateRequest.title,
            content = ideaUpdateRequest.content
        )
            .let {
                ideaService.update(it)
            }
            .let {
                ResponseEntity.status(HttpStatus.OK)
                    .build();
            }
    }
}
