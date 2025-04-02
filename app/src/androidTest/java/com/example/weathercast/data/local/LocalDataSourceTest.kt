package com.example.weathercast.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.lab4workmanager.database.WeatherDao
import com.example.lab4workmanager.database.WeatherDataBase
import com.example.mvvm_lab3.data.localDataSource.LocalDataSource
import com.example.weathercast.data.models.Clouds
import com.example.weathercast.data.models.Coord
import com.example.weathercast.data.models.Main
import com.example.weathercast.data.models.Sys
import com.example.weathercast.data.models.WeatherModel
import com.example.weathercast.data.models.Wind
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


class LocalDataSourceTest



@RunWith(AndroidJUnit4::class)
class TasksLocalDataSourceTest {


    private lateinit var database: WeatherDataBase
    private lateinit var weatherDao: WeatherDao
    private lateinit var AlarmDao: AlarmDao
    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDataBase::class.java
        ).build()
        weatherDao = database.getWeatherDao()
        AlarmDao = database.getAlarmDao()
        localDataSource = LocalDataSource(weatherDao, AlarmDao)

    }


    @Test
    fun saveTask_GetTaskById() = runTest  {

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

        localDataSource.insertWeather(weather)
        // when

        var result = localDataSource.getWeatherById(weather.id).first()

        // then
        assertNotNull(result )
        assertThat(result.id, `is`(weather.id))

    }

    @Test
    fun saveWeather_GetWeatherByLatLong() = runTest  {

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

        localDataSource.insertWeather(weather)
        // when

        var result = localDataSource.getLocalWeatherByLatLong(weather.coord.lat, weather.coord.lon).first()

        // then
        assertNotNull(result )
        assertThat(result?.coord?.lat, `is`(weather.coord.lat))

        assertThat(result?.coord?.lon, `is`(weather.coord.lon))

    }


//    @Test
//    fun updateTaskAndGetById() = runTest  {
//        val task = Task("title", "description")
//        localDataSource.saveTask(task)
//
//        // when
//
//        localDataSource.completeTask(task.id)
//
//        var result = localDataSource.getTask(task.id)
//
//        assertThat(result.succeeded, IsEqual(true))
//        //assertThat(result.isCompleted, IsEqual(true))
//        //assertThat(result.succeeded as Task , `is`(task))
//
//    }
//
//
//    fun completeTask_retrievedTaskIsComplete() = runTest{
//
//        var task1 = Task("title1", "description1" , isCompleted = true)
//        var task2 = Task("title2", "description2" , isCompleted = true)
//        var task3 = Task("title3", "description3" , isCompleted = true)
//
//        localDataSource.saveTask(task1)
//        localDataSource.saveTask(task2)
//        localDataSource.saveTask(task3)
//
//        var result = localDataSource.getTasks()
//
//        assertThat(result.succeeded, IsEqual(true))
//
//    }




}