package com.example.kaizenmockapp.feature.dashboard.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.example.kaizenmockapp.R
import com.example.kaizenmockapp.data.SportEvent
import kotlinx.coroutines.delay
import java.util.Calendar

@Composable
fun EventView(
    event: SportEvent,
    onFavoriteClick: (SportEvent) -> Unit,
    formatTime: (timeInMilli: Long) -> String,
    modifier: Modifier = Modifier
) {
    var remainingTime by remember (event.timeInMillis) {
        mutableLongStateOf(event.timeInMillis - System.currentTimeMillis())
    }

    var isRunning by remember { mutableStateOf(false) }
    LifecycleResumeEffect(Unit) {
        isRunning = remainingTime > 0
        onPauseOrDispose { isRunning = false }
    }

    LaunchedEffect(isRunning) {
        while (isRunning) {
            remainingTime = event.timeInMillis - System.currentTimeMillis()
            delay(1000)
        }
    }

    Card(
        modifier = modifier.testTag("EventViewCard")
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.clickable {
                onFavoriteClick(event)
            }
        ) {
            Text(
                text = formatTime(remainingTime),
                maxLines = 1,
                textAlign = TextAlign.Center,
                color =
                    if (event.favorite) Color(0xFFF5EFE7)
                    else Color(0xFF213555),
                modifier = Modifier
                    .testTag("EventViewCardTimer")
                    .fillMaxWidth()
                    .background(
                        if (event.favorite) Color(0xFF3E5879)
                        else Color(0xFFF5EFE7)
                    )
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 4.dp, vertical = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                event.competitors.forEachIndexed { index, competitor ->
                    Text(
                        text = competitor,
                        textAlign = if (index % 2 == 0) TextAlign.Start else TextAlign.End,
                        maxLines = 3,
                        modifier = Modifier,
                    )

                    if (index < event.competitors.size - 1) {
                        Text(
                            text = "VS",
                            fontSize = 10.sp,
                            color = Color.LightGray,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .height(36.dp)
                    .fillMaxWidth()
                    .background(
                        if (event.favorite) Color(0xFF3E5879)
                        else Color(0xFFF5EFE7)
                    )
                ,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_favorite_on),
                    contentDescription = "Add to favorite",
                    tint =
                    if (event.favorite)  Color(0xFFFADA7A)
                    else Color(0xFF213555),
                    modifier = Modifier
                        .testTag("EventViewCardFavIcon")
                        .padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
@Preview
private fun Preview() {
    val calendar: Calendar = Calendar.getInstance().clone() as Calendar
    calendar.add(Calendar.MINUTE, 5)

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        EventView(
            event = SportEvent(
                id = "123",
                competitors = listOf("Arsenal (labotryas) (Esports)", "Machester United (Fearggwp) (Esports"),
                sportId = "1",
                timeInMillis = calendar.timeInMillis,
                favorite = false
            ),
            onFavoriteClick = {},
            formatTime = { "00:10:00" },
            modifier = Modifier.fillMaxWidth(.25F)
        )
    }
}