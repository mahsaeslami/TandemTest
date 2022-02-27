package com.tandem.tandemtest.data.community.local

import com.tandem.tandemtest.core.api.Result
import com.tandem.tandemtest.data.community.local.model.CommunityTable

/**
 * Created by Mahsa on 2022.02.27
 */
interface ICommunityLocalDataSource {

    suspend fun saveCommunity(id: Int, isLike: Boolean): Result<Boolean>

    suspend fun getCommunities(): Result<List<CommunityTable>>
}