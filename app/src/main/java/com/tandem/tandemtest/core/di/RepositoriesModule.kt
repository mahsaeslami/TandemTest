package com.tandem.tandemtest.core.di

import com.tandem.tandemtest.core.database.AppDatabase
import com.tandem.tandemtest.data.community.CommunityRepository
import com.tandem.tandemtest.data.community.local.ICommunityLocalDataSource
import com.tandem.tandemtest.data.community.local.datasource.CommunityLocalDataSource
import com.tandem.tandemtest.data.community.remote.ICommunityRemoteDataSource
import com.tandem.tandemtest.data.community.remote.datasource.CommunityDataSource
import com.tandem.tandemtest.data.community.remote.datasource.CommunityService
import com.tandem.tandemtest.domain.community.ICommunityRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Created by Mahsa on 2022.02.26
 */
val repositoriesModule = module {
    // Retrofit
    factory { get<Retrofit>(named(RETROFIT)).create(CommunityService::class.java) }

    factory<ICommunityLocalDataSource> {
        CommunityLocalDataSource(
            AppDatabase.getInstance(get()).communityDao()
        )
    }
    factory<ICommunityRemoteDataSource> { CommunityDataSource(get()) }
    factory<ICommunityRepository> { CommunityRepository(get(), get()) }
}