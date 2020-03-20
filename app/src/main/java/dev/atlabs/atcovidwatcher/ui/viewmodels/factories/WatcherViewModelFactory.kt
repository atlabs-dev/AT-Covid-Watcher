package dev.atlabs.atcovidwatcher.ui.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.atlabs.atcovidwatcher.api.repositories.StatisticRepository
import dev.atlabs.atcovidwatcher.ui.viewmodels.WatcherViewModel

@Suppress("UNCHECKED_CAST")
class WatcherViewModelFactory(
    private val repository: StatisticRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WatcherViewModel(repository) as T
    }
}
