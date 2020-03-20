package dev.atlabs.atcovidwatcher.api.repositories

import dev.atlabs.atcovidwatcher.api.response.Resource
import dev.atlabs.atcovidwatcher.data.CovidStatistic

interface StatisticRepository {
    suspend fun getCurrentStats(): Resource<CovidStatistic>
}
