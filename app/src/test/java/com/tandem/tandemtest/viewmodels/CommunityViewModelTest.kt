package com.tandem.tandemtest.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.tandem.tandemtest.LifeCycleTestOwner
import com.tandem.tandemtest.core.ui.State
import com.tandem.tandemtest.domain.community.usecases.GetCommunityUseCase
import com.tandem.tandemtest.domain.community.usecases.SaveCommunityUseCase
import com.tandem.tandemtest.presentation.model.Community
import com.tandem.tandemtest.presentation.ui.CommunityViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import com.tandem.tandemtest.core.api.Result

/**
 * Created by Mahsa on 2022.02.27
 */
@ExperimentalCoroutinesApi
class CommunityViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val communitiesLiveDataObserver: Observer<State<List<Community>>> = mock()
    private val saveCommunityLiveDataObserver: Observer<State<Pair<Int, Boolean>>> = mock()

    private val getCommunityUseCase: GetCommunityUseCase = mock()
    private val saveCommunityUseCase: SaveCommunityUseCase = mock()

    private lateinit var lifeCycleTestOwner: LifeCycleTestOwner
    private lateinit var communityViewModel: CommunityViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())

        lifeCycleTestOwner = LifeCycleTestOwner()
        lifeCycleTestOwner.onCreate()

        communityViewModel = CommunityViewModel(getCommunityUseCase, saveCommunityUseCase)

        communityViewModel.communityLiveData.observe(
            lifeCycleTestOwner,
            communitiesLiveDataObserver
        )

        communityViewModel.saveCommunityLiveData.observe(
            lifeCycleTestOwner,
            saveCommunityLiveDataObserver
        )
    }

    @After
    fun tearDown() {
        lifeCycleTestOwner.onDestroy()
        Dispatchers.resetMain()
    }

    @Test
    fun `getCommunities, shows loading first then shows the communities after it was successfully fetched`() {
        runTest {
            // Given
            lifeCycleTestOwner.onResume()
            When calling getCommunityUseCase(any()) itReturns Result.Success(emptyList())

            // When
            launch {
                communityViewModel.getCommunities(1)
            }.join()

            // Then
            Verify on communitiesLiveDataObserver that communitiesLiveDataObserver.onChanged(State.Loading) was called

            Verify on getCommunityUseCase that getCommunityUseCase(any()) was called

            Verify on communitiesLiveDataObserver that communitiesLiveDataObserver.onChanged(
                State.Success(emptyList())
            ) was called

            VerifyNoFurtherInteractions on communitiesLiveDataObserver
        }
    }

    @Test
    fun `getCommunities, shows loading first then shows the error`() {
        runTest {
            // Given
            lifeCycleTestOwner.onResume()
            When calling getCommunityUseCase(any()) itReturns Result.Error(Exception("error message"))

            // When
            launch {
                communityViewModel.getCommunities(1)
            }.join()

            // Then
            Verify on communitiesLiveDataObserver that communitiesLiveDataObserver.onChanged(State.Loading) was called

            Verify on getCommunityUseCase that getCommunityUseCase(any()) was called

            Verify on communitiesLiveDataObserver that communitiesLiveDataObserver.onChanged(
                State.Error("error message")
            ) was called

            VerifyNoFurtherInteractions on communitiesLiveDataObserver
        }
    }

    @Test
    fun `saveCommunity, shows loading first then shows the save community after it was successfully save`() {
        runTest {
            // Given
            lifeCycleTestOwner.onResume()
            When calling saveCommunityUseCase(any(), any()) itReturns Result.Success(true)

            // When
            launch {
                communityViewModel.saveCommunity(1, true)
            }.join()

            // Then
            Verify on saveCommunityLiveDataObserver that saveCommunityLiveDataObserver.onChanged(
                State.Loading
            ) was called

            Verify on saveCommunityUseCase that saveCommunityUseCase(any(), any()) was called

            Verify on saveCommunityLiveDataObserver that saveCommunityLiveDataObserver.onChanged(
                State.Success(Pair(1, true))
            ) was called

            VerifyNoFurtherInteractions on saveCommunityLiveDataObserver
        }
    }

    @Test
    fun `saveCommunity, shows loading first then shows the error`() {
        runTest {
            // Given
            lifeCycleTestOwner.onResume()
            When calling saveCommunityUseCase(
                any(),
                any()
            ) itReturns Result.Error(Exception("error message"))

            // When
            launch {
                communityViewModel.saveCommunity(1, true)
            }.join()

            // Then
            Verify on saveCommunityLiveDataObserver that saveCommunityLiveDataObserver.onChanged(
                State.Loading
            ) was called

            Verify on saveCommunityUseCase that saveCommunityUseCase(any(), any()) was called

            Verify on saveCommunityLiveDataObserver that saveCommunityLiveDataObserver.onChanged(
                State.Error("error message")
            ) was called

            VerifyNoFurtherInteractions on saveCommunityLiveDataObserver
        }
    }
}