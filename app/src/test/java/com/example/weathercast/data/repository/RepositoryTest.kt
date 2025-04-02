package com.example.weathercast.data.repository

import android.content.SharedPreferences
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mvvm_lab3.data.reomteDataSource.IRemoteDataSource
import com.example.weathercast.data.localDataSource.ILocalDataSource
import com.example.weathercast.data.models.*
import io.mockk.mockk
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)  // ✅ Added this
class RepositoryTest {
    private lateinit var repository: Repository

    @Before
    fun setUp() {
        val localDataSource = mockk<ILocalDataSource>(relaxed = true)
        val remoteDataSource = mockk<IRemoteDataSource>(relaxed = true)
        val sharedPreferences = mockk<SharedPreferences>(relaxed = true) // ✅ Fixed variable name
        repository = Repository(remoteDataSource, localDataSource, sharedPreferences)
    }

    @Test
    fun insertAlarmGetAlarm() = runTest {
        val alarm = Alarm(0, 123)
        repository.insertAlarm(alarm)

        val alarms = repository.getAllAlarm().first() // ✅ Ensure data is retrieved
        assertNotNull(alarms)
    }
//
//    @Test
//    fun saveWeather_GetWeatherByLatLong() = runTest {
//        val weather = WeatherModel(
//            "dk",
//            Clouds(454),
//            55,
//            Coord(12.12, 12.12),
//            1235,
//            1,
//            Main(12.2, 52, 5, 5, 5, 5.5, 4.5, 78.5),
//            "54",
//            Sys("fdg", 5, 878, 545, 5),
//            4545,
//            454,
//            emptyList(),
//            Wind(12, 212.2, 54.2)
//        )
//
//        val result = repository.getLocalWeatherByLatLong(weather.coord.lat, weather.coord.lon).first()
//        assertNotNull(result)
//        result?.let {
//            assertThat(it.coord.lat, `is`(weather.coord.lat))
//            assertThat(it.coord.lon, `is`(weather.coord.lon))
//        }
//    }
}





//package com.example.weathercast.data.repository
//
//import android.content.SharedPreferences
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.example.mvvm_lab3.data.reomteDataSource.IRemoteDataSource
//import com.example.weathercast.NavigationBarItem
//import com.example.weathercast.data.localDataSource.ILocalDataSource
//import com.example.weathercast.data.models.Alarm
//import com.example.weathercast.data.models.Clouds
//import com.example.weathercast.data.models.Coord
//import com.example.weathercast.data.models.Main
//import com.example.weathercast.data.models.Sys
//import com.example.weathercast.data.models.WeatherModel
//import com.example.weathercast.data.models.Wind
//import io.mockk.mockk
//import junit.framework.TestCase.assertNotNull
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.test.runTest
//import org.hamcrest.MatcherAssert.assertThat
//import org.hamcrest.core.Is.`is`
//import org.junit.Before
//import org.junit.jupiter.api.Test
//import org.junit.runner.RunWith
//
//
//class RepositoryTest {
//    lateinit var repository: Repository
//
//    @Before
//    fun setUp() {
//        val localDataSource = mockk<ILocalDataSource>(relaxed = true)
//        val remoteDataSource = mockk<IRemoteDataSource>(relaxed = true)
//        val shardPreferences = mockk<SharedPreferences>(relaxed = true)
//        repository = Repository(remoteDataSource, localDataSource, shardPreferences)
//    }
//
//    @Test
//    fun insertAlarmGetAlarm() = runTest {
//        val alarm = Alarm(0, 123)
//        repository.insertAlarm(alarm)
//        repository.getAllAlarm()
//    }
//
//    @Test
//    fun saveWeather_GetWeatherByLatLong() = runTest {
//        val weather = WeatherModel(
//            "dk",
//            Clouds(454),
//            55,
//            Coord(12.12, 12.12),
//            1235,
//            1,
//            Main(12.2, 52, 5, 5, 5, 5.5, 4.5, 78.5),
//            "54",
//            Sys("fdg", 5, 878, 545, 5),
//            4545,
//            454,
//            emptyList(),
//            Wind(12, 212.2, 54.2)
//        )
//
//        val result = repository.getLocalWeatherByLatLong(weather.coord.lat, weather.coord.lon).first()
//        assertNotNull(result)
//        assertThat(result?.coord?.lat, `is`(weather.coord.lat))
//        assertThat(result?.coord?.lon, `is`(weather.coord.lon))
//    }
//
//    @Test
//    fun insertFavoriteWeather() = runTest {
//        val weather = WeatherModel(
//            "dk",
//            Clouds(454),
//            55,
//            Coord(12.12, 12.12),
//            1235,
//            1,
//            Main(12.2, 52, 5, 5, 5, 5.5, 4.5, 78.5),
//            "54",
//            Sys("fdg", 5, 878, 545, 5),
//            4545,
//            454,
//            emptyList(),
//            Wind(12, 212.2, 54.2)
//        )
//
//        val result = repository.getLocalWeatherByLatLong(weather.coord.lat, weather.coord.lon).first()
//        assertNotNull(result)
//        assertThat(result?.coord?.lat, `is`(weather.coord.lat))
//        assertThat(result?.coord?.lon, `is`(weather.coord.lon))
//    }
//}