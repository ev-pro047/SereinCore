package com.maxrave.data.repository

import com.maxrave.domain.data.model.update.UpdateData
import com.maxrave.domain.repository.UpdateRepository
import com.maxrave.domain.utils.Resource
import com.maxrave.kotlinytmusicscraper.YouTube
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Serein is currently a personal-use build with no public update or signing feed.
 * Do not offer updates for the unrelated upstream SimpMusic application.
 *
 * The constructor remains compatible with the existing Koin dependency graph.
 * When a Serein-owned update feed is added, implement it explicitly here.
 */
internal class UpdateRepositoryImpl(
    @Suppress("unused") private val youTube: YouTube,
) : UpdateRepository {
    override fun checkForGithubReleaseUpdate(): Flow<Resource<UpdateData>> =
        flow { emit(Resource.Error<UpdateData>("Serein has no configured release update feed.")) }

    override fun checkForFdroidUpdate(): Flow<Resource<UpdateData>> =
        flow { emit(Resource.Error<UpdateData>("Serein is not published on F-Droid.")) }

    override fun getFdroidSigningKeys(): Flow<Resource<List<String>>> =
        flow { emit(Resource.Error<List<String>>("Serein has no upstream F-Droid signing record.")) }
}
