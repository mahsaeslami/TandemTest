package com.tandem.tandemtest.presentation.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tandem.tandemtest.core.api.subscribeBy
import com.tandem.tandemtest.core.ui.State
import com.tandem.tandemtest.domain.community.usecases.GetCommunityUseCase
import com.tandem.tandemtest.presentation.model.Community
import com.tandem.tandemtest.presentation.model.toPresentationModel
import kotlinx.coroutines.launch

/**
 * Created by Mahsa on 2022.02.26
 */
class CommunityViewModel(
    private val getCommunityUseCase: GetCommunityUseCase
) : ViewModel() {

    val communityLiveData: MutableLiveData<State<List<Community>>> = MutableLiveData()

    fun getCommunities(pageId: Int) {
        communityLiveData.value = State.Loading

        viewModelScope.launch {
            getCommunityUseCase(pageId).subscribeBy(
                onSuccess = { communityEntities ->
                    communityLiveData.postValue(
                        State.Success(
                            communityEntities?.map { communityEntity ->
                                communityEntity.toPresentationModel()
                            } ?: emptyList()
                        )
                    )
                },
                onError = {
                    communityLiveData.postValue(State.Error(it.message ?: ""))
                }

            )
        }

    }
}