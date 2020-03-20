package dev.atlabs.atcovidwatcher.api

import dev.atlabs.atcovidwatcher.data.CovidStatistic
import retrofit2.http.GET

interface ApiService {

    @GET("/api")
    suspend fun getStatistics(): CovidStatistic
}
