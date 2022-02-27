package com.tandem.tandemtest.domain.community.entities

/**
 * Created by Mahsa on 2022.02.26
 */
data class CommunityEntity(
    val id: Int = 0,
    val topic: String = "",
    val firstName: String = "",
    val pictureUrl: String = "",
    val natives: List<String> = emptyList(),
    val learns: List<String> = emptyList(),
    val referenceCnt: Int = 0,
    var isLike: Boolean = false
)