package com.tandem.tandemtest.core.di

import com.tandem.tandemtest.domain.community.usecases.GetCommunityUseCase
import com.tandem.tandemtest.domain.community.usecases.SaveCommunityUseCase
import org.koin.dsl.module

/**
 * Created by Mahsa on 2022.02.26
 */
val useCasesModule = module {
    factory { GetCommunityUseCase(get()) }
    factory { SaveCommunityUseCase(get()) }
}