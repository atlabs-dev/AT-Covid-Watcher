package dev.atlabs.atcovidwatcher.data

data class CovidStatistic(
    var confirmed: Confirmed = Confirmed(),
    var countries: String = "",
    var countryDetail: CountryDetail = CountryDetail(),
    var dailySummary: String = "",
    var dailyTimeSeries: DailyTimeSeries = DailyTimeSeries(),
    var deaths: Deaths = Deaths(),
    var image: String = "",
    var lastUpdate: String = "",
    var recovered: Recovered = Recovered(),
    var source: String = ""
)
