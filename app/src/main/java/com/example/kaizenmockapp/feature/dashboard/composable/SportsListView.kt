package com.example.kaizenmockapp.feature.dashboard.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kaizenmockapp.data.Sport
import com.example.kaizenmockapp.data.SportEvent
import com.example.kaizenmockapp.util.AsyncParam
import com.example.kaizenmockapp.feature.dashboard.DashboardViewModel

@Composable
fun SportsListView(
    state: DashboardViewModel.ComposeState,
    onFavoriteClick: (SportEvent) -> Unit,
    formatTime: (timeInMilli: Long) -> String,
    onShrinkExpandClick: (Sport) -> Unit,
    onFilterFavoriteClick: (Sport, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    if (state.sports.isEmpty()) {

        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier.fillMaxSize()) {
            Text(
                text = "No sports found!",
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
        }

    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier,

        ) {
            items(state.sports) {
                SportView(
                    sport = it.sport,
                    showEvents = it.opened,
                    filterFavorites = it.filterFavorite,
                    events = state.events[it.sport.id] ?: AsyncParam.Error("events not found"),
                    onFavoriteClick = onFavoriteClick,
                    formatTime = formatTime,
                    onShrinkExpandClick = onShrinkExpandClick,
                    onFilterFavoriteClick = onFilterFavoriteClick
                )
            }
        }
    }
}
