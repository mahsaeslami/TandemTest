package com.tandem.tandemtest.core.di

import com.tandem.tandemtest.core.imageloader.IImageLoader
import com.tandem.tandemtest.presentation.util.GlideImageLoader
import org.koin.dsl.module

/**
 * Created by Mahsa on 2022.02.27
 */
val utilModule = module {
    single { GlideImageLoader() as IImageLoader }
}