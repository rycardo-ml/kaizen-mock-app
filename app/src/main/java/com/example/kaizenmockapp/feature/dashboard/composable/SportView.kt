package com.example.kaizenmockapp.feature.dashboard.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.kaizenmockapp.R
import com.example.kaizenmockapp.data.Sport
import com.example.kaizenmockapp.data.SportEvent
import com.example.kaizenmockapp.util.AsyncParam
import com.example.kaizenmockapp.util.gesturesDisabled

@Composable
fun SportView(
    sport: Sport,
    showEvents: Boolean,
    filterFavorites: Boolean,
    events: AsyncParam<List<SportEvent>>,
    onFavoriteClick: (SportEvent) -> Unit,
    formatTime: (timeInMilli: Long) -> String,
    onShrinkExpandClick: (Sport) -> Unit,
    onFilterFavoriteClick: (Sport, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    val isFiltering = remember(key1 = events) {
        when (events) {
            is AsyncParam.Success -> {
                events.filtering
            }
            else -> false
        }
    }
    val isLoadingEvents = remember(key1 = events) { events is AsyncParam.Loading }

    Box(
        modifier = modifier.fillMaxWidth()
            .gesturesDisabled(isFiltering)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            SportHeader(
                sportName = sport.name,
                showEvents = showEvents,
                filterFavorites = filterFavorites,
                isLoadingEvents = isLoadingEvents,
                onShowHideClick = { onShrinkExpandClick(sport) },
                onFilterFavoriteClick = { onFilterFavoriteClick(sport, it) }
            )

            if (showEvents) {
                EventsListView(
                    sportName = sport.name,
                    events = events,
                    onFavoriteClick = onFavoriteClick,
                    formatTime = formatTime,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }
        }

        if (isFiltering) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .matchParentSize()
                    .background(Color(0xBF213555))

            ) {
                Text(
                    text = "Filtering",
                    fontSize = 24.sp,
                    color = Color(0xBFD8C4B6),
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 36.dp
                    )
                )

                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(64.dp)
                )
            }
        }
    }
}

@Composable
private fun SportHeader(
    sportName: String,
    onShowHideClick: () -> Unit,
    onFilterFavoriteClick: (checked: Boolean) -> Unit,
    showEvents: Boolean,
    filterFavorites: Boolean,
    isLoadingEvents: Boolean,
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(modifier = modifier
        .height(48.dp)
        .fillMaxWidth()
        .background(Color(red = 33, green= 53, blue = 85))
        .padding(
            horizontal = 16.dp,
            vertical = 4.dp
        )
    ) {
        val (
            refImage,
            refName,
            refFilter,
            refVisibility
        ) = createRefs()

        Icon(
            painter = painterResource(R.drawable.ic_sport),
            contentDescription = null,
            tint = Color.Red,
            modifier = Modifier
                .padding(vertical = 6.dp)
                .constrainAs(refImage) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(refName.start)
            }
        )

        Text(
            text = sportName,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.constrainAs(refName) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(refImage.end, margin = 12.dp)
                end.linkTo(refFilter.start, margin = 12.dp)
                width = Dimension.fillToConstraints
            }
        )

        if (!isLoadingEvents) {
            Switch(
                checked = filterFavorites,
                onCheckedChange = onFilterFavoriteClick,
                modifier = Modifier.constrainAs(refFilter) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(refName.end)
                    end.linkTo(refVisibility.start, margin = 12.dp)
                }
            )
        }

        if (isLoadingEvents) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .constrainAs(refVisibility) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(refFilter.end)
                        end.linkTo(parent.end)
                    }
            )
        } else {
            Icon(
                painter = painterResource(
                    if (showEvents) R.drawable.ic_shrink
                    else R.drawable.ic_expand
                ),
                contentDescription = "Expand details",
                tint = Color.White,
                modifier = Modifier
                    .clickable { onShowHideClick() }
                    .constrainAs(refVisibility) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(refFilter.end)
                        end.linkTo(parent.end)
                    }
            )
        }
    }
}

@Composable
@Preview
private fun HeaderPreview() {
    SportHeader(
        sportName = "Soccer Soccer Soccer Soccer Soccer Soccer Soccer  ",
        showEvents = false,
        isLoadingEvents = true,
        filterFavorites = false,
        onShowHideClick = { },
        onFilterFavoriteClick = {},
    )
}