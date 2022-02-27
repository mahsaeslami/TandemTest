package com.tandem.tandemtest

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tandem.tandemtest.core.database.AppDatabase
import com.tandem.tandemtest.data.community.local.datasource.CommunityDao
import com.tandem.tandemtest.data.community.local.model.CommunityTable
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Created by Mahsa on 2022.02.27
 */

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class CommunityDaoTest {
    private lateinit var communityDao: CommunityDao
    private lateinit var db: AppDatabase
    private val id = 1

    @Before
    fun setup() {
        // create db
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .build()
        communityDao = db.communityDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeCommunityAndReadInList() {
        runBlocking {
            val community = CommunityTable(id, isLike = true)
            var cachedCommunities: List<CommunityTable> = mutableListOf()

            launch {
                communityDao.insertCommunity(community)
                cachedCommunities = communityDao.selectCommunities()
            }.join()

            val byCommunity = cachedCommunities.filter { it.id == id }
            assertThat(byCommunity[0], equalTo(community))
        }
    }
}