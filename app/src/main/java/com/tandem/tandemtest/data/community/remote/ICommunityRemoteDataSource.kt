package com.tandem.tandemtest.data.community.remote

import com.tandem.tandemtest.core.api.Result
import com.tandem.tandemtest.domain.community.entities.CommunityEntity

/**
 * Created by Mahsa on 2022.02.26
 *
 * This data source is responsible for fetching data from remote
 *
 */
interface ICommunityRemoteDataSource {

    suspend fun getCommunities(pageId: Int): Result<List<CommunityEntity>?>
}