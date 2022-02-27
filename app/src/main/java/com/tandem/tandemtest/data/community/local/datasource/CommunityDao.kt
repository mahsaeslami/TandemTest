package com.tandem.tandemtest.data.community.local.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tandem.tandemtest.data.community.local.model.CommunityTable

/**
 * Created by Mahsa on 2022.02.27
 */
@Dao
interface CommunityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCommunity(communityTable: CommunityTable)

    @Query("SELECT * FROM CommunityTable where isLike = :like")
    suspend fun selectCommunities(like: Boolean = true): List<CommunityTable>
}