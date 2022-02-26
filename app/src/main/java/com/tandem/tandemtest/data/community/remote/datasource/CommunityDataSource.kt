package com.tandem.tandemtest.data.community.remote.datasource

import com.tandem.tandemtest.core.api.map
import com.tandem.tandemtest.data.community.remote.ICommunityRemoteDataSource
import com.tandem.tandemtest.data.community.remote.model.toEntity

/**
 * Created by Mahsa on 2022.02.26
 */
class CommunityDataSource(private val communityService: CommunityService) :
    ICommunityRemoteDataSource {

    override suspend fun getCommunities(pageId: Int) =
        communityService.getCommunities(pageId).map { it?.toEntity() }
}