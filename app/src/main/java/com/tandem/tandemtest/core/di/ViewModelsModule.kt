package com.tandem.tandemtest.core.di

import com.tandem.tandemtest.presentation.ui.CommunityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Mahsa on 2022.02.26
 */
val viewModelsModule = module {
    viewModel { CommunityViewModel(get()) }
}