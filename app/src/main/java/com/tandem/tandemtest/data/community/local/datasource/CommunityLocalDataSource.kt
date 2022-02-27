package com.tandem.tandemtest.data.community.local.datasource

import com.tandem.tandemtest.core.api.Result
import com.tandem.tandemtest.data.community.local.ICommunityLocalDataSource
import com.tandem.tandemtest.data.community.local.model.CommunityTable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Mahsa on 2022.02.27
 */
class CommunityLocalDataSource(
    private val communityDao: CommunityDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ICommunityLocalDataSource {

    override suspend fun saveCommunity(id: Int, isLike: Boolean): Result<Boolean> =
        withContext(ioDispatcher) {
            return@withContext try {
                communityDao.insertCommunity(CommunityTable(id, isLike))
                Result.Success(isLike)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun getCommunities(): Result<List<CommunityTable>> =
        withContext(ioDispatcher) {
            return@withContext try {
                Result.Success(communityDao.selectCommunities())
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
}