package com.strv.movies.data

import com.strv.movies.data.network.ProfileApi
import com.strv.movies.data.network.toDomain
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val profileApi: ProfileApi
) {

    suspend fun getAccountDetails() = profileApi.getAccountDetails().toDomain()

    suspend fun getFavoriteMovies(accountId: Int) =
        profileApi.getFavoriteMovies(accountId).results.map { it.toDomain() }
}