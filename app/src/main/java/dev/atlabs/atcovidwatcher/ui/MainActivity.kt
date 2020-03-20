package dev.atlabs.atcovidwatcher.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dev.atlabs.atcovidwatcher.R
import dev.atlabs.atcovidwatcher.adapters.DashboardAdapter
import dev.atlabs.atcovidwatcher.api.response.Status
import dev.atlabs.atcovidwatcher.data.CovidStatistic
import dev.atlabs.atcovidwatcher.data.models.DashboardItem
import dev.atlabs.atcovidwatcher.ui.viewmodels.WatcherViewModel
import dev.atlabs.atcovidwatcher.ui.viewmodels.factories.WatcherViewModelFactory
import dev.atlabs.atcovidwatcher.utils.NotificationHelper
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import timber.log.Timber

class MainActivity : AppCompatActivity(), KodeinAware {
    private lateinit var watcherViewModel: WatcherViewModel
    override val kodein: Kodein by kodein()
    private val viewModelFactory: WatcherViewModelFactory by instance()
    private lateinit var notificationHelper: NotificationHelper
    private lateinit var dashboardAdapter: DashboardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        watcherViewModel = ViewModelProvider(this, viewModelFactory).get(WatcherViewModel::class.java)
        notificationHelper = NotificationHelper(this)
        dashboardAdapter = DashboardAdapter(this)
        with(rvDashboard) {
            this.adapter = dashboardAdapter
            this.layoutManager = LinearLayoutManager(this@MainActivity)
        }

        watcherViewModel.currentStatistics.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> it.data?.let { latestStatistics ->
                    broadCastMessage(latestStatistics)
                    bindItems(latestStatistics)
                    Log.d("MainActivity", latestStatistics.toString())
                }
                Status.ERROR -> Toast.makeText(this, "Error Occured", Toast.LENGTH_LONG).show()
                Status.LOADING -> Timber.d("Post Loading")
            }
        })
    }

    private fun broadCastMessage(latestStats: CovidStatistic) {
        notificationHelper.createNotification("Covid19 Stats", "Confirmed: ${latestStats.confirmed.value} " +
                "\nRecovered: ${latestStats.recovered.value} " +
                "\nDead: ${latestStats.deaths.value} " +
                "\nGlobal Mortality Rate: ${(latestStats.deaths.value.toFloat() / latestStats.confirmed.value.toFloat()) * 100} %")
                "\nRecovery Rate: ${(latestStats.recovered.value.toFloat() / latestStats.confirmed.value.toFloat()) * 100} %\")"
    }

    private fun bindItems(latestStats: CovidStatistic) {
        val rvItems = listOf(
            DashboardItem("Confirmed", latestStats.confirmed.value.toString()),
            DashboardItem("Recovered", latestStats.recovered.value.toString()),
            DashboardItem("Deaths", latestStats.deaths.value.toString()),
            DashboardItem("Fatality Rate", "${((latestStats.deaths.value.toFloat() / latestStats.confirmed.value.toFloat()) * 100)} %"),
            DashboardItem("Recovery Rate", "${((latestStats.recovered.value.toFloat() / latestStats.confirmed.value.toFloat()) * 100)} %")
        )

        tvTime.text = latestStats.lastUpdate

        dashboardAdapter.setItems(rvItems)
    }
}
