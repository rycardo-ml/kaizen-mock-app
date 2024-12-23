package com.example.kaizenmockapp.feature.dashboard.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kaizenmockapp.data.SportEvent
import com.example.kaizenmockapp.util.AsyncParam

@Composable
fun EventsListView(
    sportName: String,
    events: AsyncParam<List<SportEvent>>,
    onFavoriteClick: (SportEvent) -> Unit,
    formatTime: (timeInMilli: Long) -> String,
    modifier: Modifier = Modifier,
) {
    when (events) {
        AsyncParam.Loading -> {
            Text(
                text = "Loading \n${sportName}",
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = Color(0x803E5879),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 48.dp,
                    )
            )
        }

        is AsyncParam.Error -> {
            Text(
                text = "Failed to get events for \n${sportName}",
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = Color(0x803E5879),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 48.dp,
                    )
            )
        }

        is AsyncParam.Success -> {
            if (events.item.isEmpty()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 48.dp)) {

                    Text(
                        text = "No events found",
                        fontSize = 20.sp
                    )
                }
            } else {
                EventsSuccess(
                    events.item,
                    onFavoriteClick = onFavoriteClick,
                    formatTime = formatTime,
                    modifier = modifier,
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun EventsSuccess(
    events: List<SportEvent>,
    onFavoriteClick: (SportEvent) -> Unit,
    formatTime: (timeInMilli: Long) -> String,
    modifier: Modifier = Modifier,
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        maxItemsInEachRow = 4,
        modifier = modifier
            .fillMaxWidth(1f)
            .padding(horizontal = 8.dp)
            .wrapContentHeight(align = Alignment.Top),
    ) {
        events.forEach {
            EventView(
                event = it,
                onFavoriteClick = onFavoriteClick,
                formatTime = formatTime,
                modifier = Modifier
                    .align(Alignment. CenterVertically)
                    .weight(1f, true)
                    .fillMaxRowHeight()
            )
        }
    }
}