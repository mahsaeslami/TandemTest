package com.tandem.tandemtest.domain.community.usecases

import com.tandem.tandemtest.domain.community.ICommunityRepository

/**
 * Created by Mahsa on 2022.02.26
 *
 * This use case is responsible for fetching Communities
 *
 */
class SaveCommunityUseCase(private val communityRepository: ICommunityRepository) {
    suspend operator fun invoke(communityId: Int, isLike: Boolean) =
        communityRepository.saveCommunity(communityId, isLike)
}