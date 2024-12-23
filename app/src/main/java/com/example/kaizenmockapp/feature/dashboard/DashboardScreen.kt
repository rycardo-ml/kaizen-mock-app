package com.example.kaizenmockapp.feature.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.kaizenmockapp.R
import com.example.kaizenmockapp.data.Sport
import com.example.kaizenmockapp.data.SportEvent
import com.example.kaizenmockapp.feature.dashboard.composable.SportsListView

@Composable
fun DashboardScreen(
    viewState: DashboardViewModel.ViewState,
    fetchData: () -> Unit,
    toggleExpired: () -> Unit,
    onShrinkExpandClick: (Sport) -> Unit,
    onFilterFavoriteClick: (Sport, Boolean) -> Unit,
    onFavoriteClick: (SportEvent) -> Unit,
    formatTime: (timeInMilli: Long) -> String,
    ) {
    LaunchedEffect(key1 = Unit) {
        fetchData()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Kaizen-Betano")
                },
                actions = {
                    IconButton(onClick = toggleExpired) {
                        Icon(
                            painter = painterResource(R.drawable.ic_expired),
                            contentDescription = "show/hide expired items",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
        }
    ) { paddingValue ->

        Box(
            modifier = Modifier.padding(
                bottom = paddingValue.calculateBottomPadding(),
                top = paddingValue.calculateTopPadding()
            )
        ) {
            when (viewState) {
                DashboardViewModel.ViewState.Loading -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp, vertical = 48.dp)
                    ) {
                        Text("Loading")
                    }
                }

                is DashboardViewModel.ViewState.Error -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp, vertical = 48.dp)

                    ) {
                        Text("And error occurred: ${viewState.message}" )

                        Button(
                            onClick = fetchData,
                            modifier = Modifier.padding(vertical = 16.dp),
                        ) {
                            Text(
                                text = "Retry"
                            )
                        }
                    }
                }

                is DashboardViewModel.ViewState.Success -> {
                    SportsListView(
                        state = viewState.state,
                        onShrinkExpandClick = onShrinkExpandClick,
                        onFavoriteClick = onFavoriteClick,
                        formatTime = formatTime,
                        onFilterFavoriteClick = onFilterFavoriteClick
                    )
                }
            }
        }
    }
}
