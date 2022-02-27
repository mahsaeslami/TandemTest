package com.tandem.tandemtest.data.community.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tandem.tandemtest.domain.community.entities.CommunityEntity

/**
 * Created by Mahsa on 2022.02.27
 */
@Entity
data class CommunityTable(
    @PrimaryKey
    val id: Int,
    val isLike: Boolean = false
)
