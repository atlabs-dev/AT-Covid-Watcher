package dev.atlabs.atcovidwatcher.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dev.atlabs.atcovidwatcher.api.repositories.StatisticRepository
import dev.atlabs.atcovidwatcher.api.response.Resource
import dev.atlabs.atcovidwatcher.dispatchers.DefaultDispatcherProvider

class WatcherViewModel(
    private val repository: StatisticRepository,
    private val dispatcher: DefaultDispatcherProvider = DefaultDispatcherProvider()
) : ViewModel() {
    val currentStatistics = liveData(dispatcher.io()) {
        emit(Resource.loading(null))
        emit(repository.getCurrentStats())
    }
}
