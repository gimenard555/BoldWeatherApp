package com.jimenard.boldweatherapp.presentation.ui.screens.locationDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.jimenard.boldweatherapp.domain.model.Day
import com.jimenard.boldweatherapp.domain.model.LocationDetail
import com.jimenard.boldweatherapp.presentation.ui.components.UiState
import com.jimenard.boldweatherapp.presentation.viewmodel.LocationDetailViewModel

@Suppress("ktlint:standard:function-naming")
@Composable
fun DetailScreen(
    locationName: String,
    viewModel: LocationDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    viewModel.fetchLocationDetailViewModel(locationName)

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
    ) {
        when (uiState) {
            is UiState.FirstState -> {
                Text(
                    text = "Por favor, inicie la búsqueda",
                    modifier =
                        Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .align(Alignment.CenterHorizontally),
                )
            }

            is UiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            is UiState.Success -> {
                val result = (uiState as UiState.Success<LocationDetail>).data
                LocationHeader(locationName = result.location.name)
                Spacer(modifier = Modifier.height(16.dp))
                WeatherForecast(dailyWeather = result.days)
            }

            is UiState.Error -> {
                val message = (uiState as UiState.Error).message
                Text(
                    text = message,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun LocationHeader(locationName: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Icon(imageVector = Icons.Filled.LocationOn, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = locationName,
            modifier = Modifier.weight(1f),
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun WeatherForecast(dailyWeather: List<Day>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
    ) {
        items(dailyWeather) { day ->
            WeatherDayItem(day = day)
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun WeatherDayItem(day: Day) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                ImageFromUrl(url = day.condition.icon)
                Text(
                    text = day.date,
                    modifier = Modifier.weight(1f),
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "${day.tempAverage}°C",
                    fontSize = 18.sp,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = day.condition.text,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun ImageFromUrl(url: String) {
    val painter =
        rememberImagePainter(
            data = url,
            builder = {
                transformations(CircleCropTransformation())
            },
        )
    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier.size(48.dp),
    )
}
