package dev.atlabs.atcovidwatcher.api.repositories

import dev.atlabs.atcovidwatcher.api.ApiFactory
import dev.atlabs.atcovidwatcher.api.response.Resource
import dev.atlabs.atcovidwatcher.api.response.ResponseHandler
import dev.atlabs.atcovidwatcher.data.CovidStatistic

class StatisticRepositoryImpl(private val responseHandler: ResponseHandler) : StatisticRepository {
    private val client = ApiFactory.apiService

    override suspend fun getCurrentStats(): Resource<CovidStatistic> {
        return try {
            responseHandler.handleSuccess(client.getStatistics())
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}
