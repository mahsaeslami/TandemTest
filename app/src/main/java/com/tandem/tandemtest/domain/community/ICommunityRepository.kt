package com.tandem.tandemtest.domain.community

import com.tandem.tandemtest.core.api.Result
import com.tandem.tandemtest.domain.community.entities.CommunityEntity

/**
 * Created by Mahsa on 2022.02.26
 *
 * This repository is responsible for fetching communities data
 *
 */
interface ICommunityRepository {

    suspend fun getCommunities(pageId: Int): Result<List<CommunityEntity>?>

    suspend fun saveCommunity(communityId: Int, isLike: Boolean): Result<Boolean>

}