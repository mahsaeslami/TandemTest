package com.tandem.tandemtest.data.community

import com.tandem.tandemtest.core.api.Result
import com.tandem.tandemtest.core.api.doOnNext
import com.tandem.tandemtest.data.community.local.ICommunityLocalDataSource
import com.tandem.tandemtest.data.community.local.model.CommunityTable
import com.tandem.tandemtest.data.community.remote.ICommunityRemoteDataSource
import com.tandem.tandemtest.domain.community.ICommunityRepository
import com.tandem.tandemtest.domain.community.entities.CommunityEntity

/**
 * Created by Mahsa on 2022.02.26
 */
class CommunityRepository(
    private val localDataSource: ICommunityLocalDataSource,
    private val remoteDataSource: ICommunityRemoteDataSource
) : ICommunityRepository {

    override suspend fun getCommunities(pageId: Int): Result<List<CommunityEntity>?> =
        // get data from remote
        remoteDataSource.getCommunities(pageId).doOnNext { remoteCommunities ->

            // get data from local
            localDataSource.getCommunities().doOnNext { localCommunities ->
                mergeLocalAndRemoteData(remoteCommunities, localCommunities)
            }
        }

    override suspend fun saveCommunity(communityId: Int, isLike: Boolean) =
        localDataSource.saveCommunity(communityId, isLike)

    private fun mergeLocalAndRemoteData(
        remoteCommunities: List<CommunityEntity>?,
        localCommunities: List<CommunityTable>?
    ): List<CommunityEntity>? {

        if (remoteCommunities.isNullOrEmpty() || localCommunities.isNullOrEmpty())
            return remoteCommunities

        remoteCommunities.forEach { remote ->
            remote.isLike = localCommunities.find { local ->
                local.id == remote.id
            }?.isLike ?: false
        }

        return remoteCommunities
    }
}