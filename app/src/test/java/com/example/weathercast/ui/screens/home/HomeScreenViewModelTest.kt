package com.example.weathercast.ui.screens.home

import android.app.Application
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weathercast.data.models.*
import com.example.weathercast.data.repository.Repository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class HomeScreenViewModelTest {

    private lateinit var repo: Repository
    private lateinit var viewModel: HomeScreenViewModel


    @Before
    fun setUp() {
        repo = mockk(relaxed = true)

        // Fix: Return a mock Long value
        coEvery { repo.insertFavoriteWeather(any()) } returns 1L

        coEvery { repo.getLocalWeatherByLatLong(any(), any()) } returns flowOf(
            WeatherModel(
                "dk",
                Clouds(454),
                55,
                Coord(12.12, 12.12),
                1235,
                1,
                Main(12.2, 52, 5, 5, 5, 5.5, 4.5, 78.5),
                "54",
                Sys("fdg", 5, 878, 545, 5),
                4545,
                454,
                emptyList(),
                Wind(12, 212.2, 54.2)
            )
        )

        viewModel = HomeScreenViewModel(repo)
    }



    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun addNewTask_setsNewTaskEvent() = runTest {
//        val app = ApplicationProvider.getApplicationContext() as Application

        val weather = WeatherModel(
            "dk",
            Clouds(454),
            55,
            Coord(12.12, 12.12),
            1235,
            1,
            Main(12.2, 52, 5, 5, 5, 5.5, 4.5, 78.5),
            "54",
            Sys("fdg", 5, 878, 545, 5),
            4545,
            454,
            emptyList(),
            Wind(12, 212.2, 54.2)
        )

        // When
        repo.insertFavoriteWeather(weather)
        val result = repo.getLocalWeatherByLatLong(weather.coord.lat, weather.coord.lon).first()

        // Then
        assertNotNull(result)
        assertThat(result?.coord?.lat, `is`(weather.coord.lat))
        assertThat(result?.coord?.lon, `is`(weather.coord.lon))
    }
}
