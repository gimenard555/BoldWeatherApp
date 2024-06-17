package com.jimenard.boldweatherapp.presentation.ui.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jimenard.boldweatherapp.domain.model.Location
import com.jimenard.boldweatherapp.presentation.ui.components.UiState
import com.jimenard.boldweatherapp.presentation.viewmodel.SearchViewModel

@Suppress("ktlint:standard:function-naming")
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    var searchText by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = searchText,
            onValueChange = {
                searchText = it
                viewModel.searchResults(it)
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Ingrese el nombre de la ubicación") },
        )

        Spacer(modifier = Modifier.height(16.dp))

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
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    val results = (uiState as UiState.Success<List<Location>>).data
                    results.map { location ->
                        LocationItemList(
                            location,
                        ) { navController.navigate("detailScreen/{$searchText}") }
                    }
                }
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
