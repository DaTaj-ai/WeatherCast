package com.example.weathercast.ui.screens.favorite

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.LocationBias
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceTypes
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.kotlin.awaitFindAutocompletePredictions
//import com.google.android.libraries.places.compose.BuildConfig
import com.google.android.libraries.places.compose.autocomplete.components.PlacesAutocompleteTextField
import com.google.android.libraries.places.compose.autocomplete.models.AutocompletePlace
import com.google.android.libraries.places.compose.autocomplete.models.toPlaceDetails
import com.google.maps.android.BuildConfig
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchBar() {
    var context = LocalContext.current
    Places.initializeWithNewPlacesApiEnabled(context, "AIzaSyCaj10hgcwGaosoYRyv79ppLviFJ9eMNmM")
    val placesClient = Places.createClient(context)

    val bias: LocationBias = RectangularBounds.newInstance(
        LatLng(39.9, -105.5), // SW lat, lng
        LatLng(40.1, -105.0) // NE lat, lng
    )

    val searchTextFlow = MutableStateFlow("")
    val searchText by searchTextFlow.collectAsStateWithLifecycle()
    var predictions by remember { mutableStateOf(emptyList<AutocompletePrediction>()) }

    LaunchedEffect(Unit) {
        searchTextFlow.debounce(500.milliseconds).collect { query : String ->
            val response = placesClient.awaitFindAutocompletePredictions {
                locationBias = bias
                typesFilter = listOf(PlaceTypes.ESTABLISHMENT)
                this.query = query
                countries = listOf("US")
            }
            predictions = response.autocompletePredictions
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Search for your city ") }
            )
        }
    ) {
        paddingValues: PaddingValues ->
        ShowGoogleMap()
        PlacesAutocompleteTextField(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            searchText = searchText,
            predictions = predictions.map { it.toPlaceDetails() },
            onQueryChanged = { searchTextFlow.value = it },
            onSelected = { autocompletePlace: AutocompletePlace ->
                val placeId = autocompletePlace.placeId
                val placeRequest = FetchPlaceRequest.newInstance(placeId, listOf(Place.Field.LAT_LNG))
                placesClient.fetchPlace(placeRequest)
                    .addOnSuccessListener { response ->
                        val place = response.place
                        val latLng = place.latLng
                        Toast.makeText(
                            context,
                            "Selected: ${autocompletePlace.primaryText}, Lat: ${latLng?.latitude}, Lng: ${latLng?.longitude}",
                            Toast.LENGTH_SHORT
                        ).show()
                            // this is the lat and long
                         // ${latLng?.latitude}, Lng: ${latLng?.longitude}
                        Log.i("TAG", "Selected Place: ${autocompletePlace.primaryText}, Lat: ${latLng?.latitude}, Lng: ${latLng?.longitude}")
                    }
                    .addOnFailureListener { exception ->
                        Log.e("TAG", "Place not found: ${exception.message}")
                    }
            },
        )
    }

}

//
//    val nameList = listOf("Tag"  , "Taj",
//        "Ahmed", "Mohammed", "Ali", "Fatima", "Amina",
//        "Hassan", "Khalid", "Layla", "Yasmin", "Omar",
//        "Zaynab", "Huda", "Samira", "Rami", "Jamil",
//        "Nadia", "Rasha", "Mona", "Yousef", "Tariq",
//        "Salma", "Ibrahim", "Zayd", "Noura", "Karim",
//        "Ahlam", "Sami", "Nabil", "Mariam", "Zahra"
//    )
//    var query_shearedFlow = remember { MutableSharedFlow<String>() }
//    var observerList = remember { mutableStateOf<List<String>>(listOf()) }
//    var isActive = remember { mutableStateOf("") }
//
//    LaunchedEffect(Unit) {
//        query_shearedFlow.collect { q ->
//            observerList.value = nameList.filter {
//                it.contains(q, ignoreCase = true)
//            }
//        }
//    }
//
//    Scaffold(
//        topBar = {
//            androidx.compose.material3.SearchBar(
//                query = isActive.value,
//                placeholder = { Text("Search") },
//                onQueryChange = {
//                    isActive.value = it
//
//                    GlobalScope.launch { query_shearedFlow.emit(it) }
//                },
//                onSearch = { },
//                active = false,
//                onActiveChange = {
//
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp)
//            ) {
//
//            }
//        }
//    ) {
//        ShowGoogleMap()
////        Box(modifier = Modifier.padding(it)) {
////            LazyColumn {
////                items(observerList.value.size) {
////                    Text(text = observerList.value[it] ,
////                        fontSize = 20.sp,
////                        modifier = Modifier.padding(8.dp))
////                }
////            }
////        }
//    }
//}