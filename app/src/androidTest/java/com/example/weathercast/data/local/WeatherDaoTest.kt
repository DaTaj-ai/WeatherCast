package com.example.weathercast.data.local

import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.lab4workmanager.database.WeatherDao
import com.example.lab4workmanager.database.WeatherDataBase
import com.example.weathercast.data.models.Clouds
import com.example.weathercast.data.models.Coord
import com.example.weathercast.data.models.Location
import com.example.weathercast.data.models.Main
import com.example.weathercast.data.models.Sys
import com.example.weathercast.data.models.WeatherModel
import com.example.weathercast.data.models.Wind
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsEqual
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TaskDaoTest {

    private lateinit var database: WeatherDataBase
    private lateinit var dao: WeatherDao

    @Before
    fun setup() {

        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDataBase::class.java
        ).build()
        dao = database.getWeatherDao()
    }

    @Test
    fun insertAndGetTaskById() = runTest {
        // give
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
        //when
        dao.insertWeather(weather)

        val result = dao.getByid(weather.id).first()


        assertNotNull(result )
        assertThat(result.id , `is`(weather.id))


    }
    @Test
    fun insertLocationAndGetall() = runTest {
        // give
        val location = Location(12.2,12.12 , "","")
        //when
        dao.insertLocation(location)

        val result = dao.getAllLocations().first()


        assertNotNull(result )
        Log.i("TAG", "insertLocationAndGetall: ")
    assertThat(result.size , `is`(1))


    }


//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun InsertWeather_andGetAll() = runTest {
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
//        val weather2 = WeatherModel(
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
//        // Insert weather data
//        dao.insertWeather(weather)
//        dao.insertWeather(weather2)
//        val result1 = dao.getAllWeather().collect{
//            Log.i("TAG", "InsertWeather_andGetAll: ${it.size}")
//            assertThat(it.size , `is`(1))
//
//        }
//
//    }


    @After
    fun teardown() {
        database.close()
    }

}