package com.tandem.tandemtest.data.community.remote.model

import com.tandem.tandemtest.domain.community.entities.CommunityEntity

/**
 * Created by Mahsa on 2022.02.26
 */
data class CommunitiesResponse(
    val response: List<CommunityResponse> = emptyList()
)

data class CommunityResponse(
    val topic: String = "",
    val firstName: String = "",
    val pictureUrl: String = "",
    val natives: List<String> = emptyList(),
    val learns: List<String> = emptyList(),
    val referenceCnt: Int = 0
)

fun CommunitiesResponse.toEntity() =
    response.map { communityResponse -> communityResponse.toEntity() }

fun CommunityResponse.toEntity() = CommunityEntity(
    this.topic,
    this.firstName,
    this.pictureUrl,
    this.natives,
    this.learns,
    this.referenceCnt
)
