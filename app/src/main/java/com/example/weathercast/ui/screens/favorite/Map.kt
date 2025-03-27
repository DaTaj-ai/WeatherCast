package com.example.weathercast.ui.screens.favorite

import android.app.Activity.MODE_PRIVATE
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weathercast.data.models.Location
import com.example.weathercast.utlis.Constants
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.LocationBias
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceTypes
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.compose.autocomplete.components.PlacesAutocompleteTextField
import com.google.android.libraries.places.compose.autocomplete.models.AutocompletePlace
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ShowGoogleMap(favoriteViewModel: FavoriteViewModel , mapType : String ) {

    var context = LocalContext.current

    Places.initializeWithNewPlacesApiEnabled(context, "AIzaSyCaj10hgcwGaosoYRyv79ppLviFJ9eMNmM")
    val placesClient = Places.createClient(context)

    val bias: LocationBias = RectangularBounds.newInstance(
        LatLng(39.9, -105.5), // SW lat, lng
        LatLng(40.1, -105.0) // NE lat, lng
    )

    //val searchTextFlow = MutableStateFlow("")

    var searchText by remember { mutableStateOf("") }
    var predictions by remember { mutableStateOf(emptyList<AutocompletePrediction>()) }

    var selectedPlace by remember { mutableStateOf<AutocompletePlace?>(null) }

    var result by remember { mutableStateOf<AutocompletePlace?>(null) }


    var markerState = rememberMarkerState(position = LatLng(1.35, 103.87))
//    {
//        mutableStateOf(MarkerState(LatLng(1.35, 103.87)))
//    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(markerState.position, 5f)
    }

    LaunchedEffect(searchText) {
        predictions = suspendCoroutine { continuation ->
            val token = AutocompleteSessionToken.newInstance()
            val request = FindAutocompletePredictionsRequest.builder()
                .setQuery(searchText)
                .setSessionToken(token)
                .setTypesFilter(listOf(PlaceTypes.CITIES))
                .build()

            placesClient.findAutocompletePredictions(request)
                .addOnSuccessListener { response ->
                    continuation.resume(response.autocompletePredictions)
                }
                .addOnFailureListener { exception ->
                    Log.e("GeocoderHelper", "Error fetching predictions: ${exception.message}")
                    continuation.resume(emptyList())
                }
        }
    }

    LaunchedEffect(markerState.position) {
        cameraPositionState.position = CameraPosition.fromLatLngZoom(markerState.position, 10f)
    }

    Box(modifier = Modifier.fillMaxSize()) {

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapClick = { location ->
                Log.i("TAG", "ShowGoogleMap: ")
                markerState.position = location
            }
        ) {
            Marker(
                state = markerState,
                title = "Singapore",
                snippet = "Marker in Singapore",
                draggable = true
            )
        }

        PlacesAutocompleteTextField(
            modifier = Modifier.padding(top = 100.dp),
            searchText = searchText,
            predictions = predictions.map {
                AutocompletePlace(
                    placeId = it.placeId,
                    primaryText = it.getPrimaryText(null),
                    secondaryText = it.getSecondaryText(null)
                )
            },
            onQueryChanged = { searchText = it },
            onSelected =
            { autocompletePlace: AutocompletePlace ->

                result = autocompletePlace
                selectedPlace = autocompletePlace

                predictions = emptyList()

                val placeRequest =
                    FetchPlaceRequest.builder(
                        autocompletePlace.placeId,
                        listOf(Place.Field.LOCATION)
                    ).build()

                placesClient.fetchPlace(placeRequest)
                    .addOnSuccessListener { response ->
                        val place = response.place
                        var newPlace = place.location ?: LatLng(1.35, 103.87)
                        val latLng = place.latLng
                        markerState.position = newPlace

                    }
                    .addOnFailureListener { exception ->
                        Log.e("TAG", "Place not found: ${exception.message}")
                    }
            },
        )


        // select button
        // Floating button positioned at the bottom right
        Button(
            onClick = {
                if (mapType == Constants.MAP_FAVORITES_TYPE){

                    favoriteViewModel.insertLocation(Location(markerState.position.latitude , markerState.position.longitude , selectedPlace?.primaryText.toString() , selectedPlace?.secondaryText.toString()))
                }
                else if (mapType == Constants.MAP_PICK_TYPE){
                    var sp = context.getSharedPreferences(Constants.SETTINGS, MODE_PRIVATE)
                    sp.edit().putString(Constants.USER_LAT, markerState.position.latitude.toString()).commit()
                    sp.edit().putString(Constants.USER_LONG, markerState.position.longitude.toString()).commit()
                    Log.i("TAG", "ShowGoogleMap:  sp added  ")
                }

                      },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 100.dp),
            colors = buttonColors(containerColor = Color.Blue),
        ) {
            if (mapType == Constants.MAP_FAVORITES_TYPE){

                Text(  "ADD to Favorite"  , fontWeight = FontWeight.Bold, color = Color.White)
            }
            else if (mapType == Constants.MAP_PICK_TYPE){
                Text(  "Select"  , fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }

}



