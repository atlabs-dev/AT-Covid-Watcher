package dev.atlabs.atcovidwatcher.app

import android.app.Application
import dev.atlabs.atcovidwatcher.api.repositories.StatisticRepositoryImpl
import dev.atlabs.atcovidwatcher.api.response.ResponseHandler
import dev.atlabs.atcovidwatcher.ui.viewmodels.factories.WatcherViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ATCovidWatcher : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ATCovidWatcher))

        bind() from singleton { ResponseHandler() }

        bind() from singleton { StatisticRepositoryImpl(instance()) }

        bind() from provider {
            WatcherViewModelFactory(instance())
        }
    }
}
