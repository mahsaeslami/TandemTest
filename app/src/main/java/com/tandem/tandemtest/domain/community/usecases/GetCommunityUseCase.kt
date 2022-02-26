package com.tandem.tandemtest.domain.community.usecases

import com.tandem.tandemtest.domain.community.ICommunityRepository

/**
 * Created by Mahsa on 2022.02.26
 *
 * This use case is responsible for fetching Communities
 *
 */
class GetCommunityUseCase(private val communityRepository: ICommunityRepository) {
    suspend operator fun invoke(pageId: Int) = communityRepository.getCommunities(pageId)
}