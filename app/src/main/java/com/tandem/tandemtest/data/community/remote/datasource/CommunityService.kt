package com.tandem.tandemtest.data.community.remote.datasource

import com.tandem.tandemtest.core.api.Result
import com.tandem.tandemtest.data.community.remote.model.CommunitiesResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Mahsa on 2022.02.26
 */
interface CommunityService {

    @GET("community_{page}.json")
    suspend fun getCommunities(@Path("page") id: Int): Result<CommunitiesResponse>
}