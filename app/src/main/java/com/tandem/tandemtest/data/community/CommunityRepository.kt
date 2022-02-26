package com.tandem.tandemtest.data.community

import com.tandem.tandemtest.core.api.Result
import com.tandem.tandemtest.data.community.remote.ICommunityRemoteDataSource
import com.tandem.tandemtest.domain.community.ICommunityRepository
import com.tandem.tandemtest.domain.community.entities.CommunityEntity

/**
 * Created by Mahsa on 2022.02.26
 */
class CommunityRepository(
    private val remoteDataSource: ICommunityRemoteDataSource
) : ICommunityRepository {
    override suspend fun getCommunities(pageId: Int): Result<List<CommunityEntity>?> {
        return remoteDataSource.getCommunities(pageId)
    }
}