package com.tandem.tandemtest.presentation.model

import com.tandem.tandemtest.domain.community.entities.CommunityEntity

/**
 * Created by Mahsa on 2022.02.26
 */
data class Community(
    val topic: String = "",
    val firstName: String = "",
    val pictureUrl: String = "",
    val natives: List<String> = emptyList(),
    val learns: List<String> = emptyList(),
    val referenceCnt: Int = 0,
    val isLike: Boolean
)

fun CommunityEntity.toPresentationModel() = Community(
    this.topic,
    this.firstName,
    this.pictureUrl,
    this.natives,
    this.learns,
    this.referenceCnt,
    this.isLike
)

