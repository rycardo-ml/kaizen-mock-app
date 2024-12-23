package com.example.kaizenmockapp.feature.dashboard.composable

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.kaizenmockapp.data.SportEvent
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Calendar

@RunWith(AndroidJUnit4::class)
class EventViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun defaultTest() {
        composeTestRule.setContent {

            var favorite by remember {
                mutableStateOf(false)
            }

            val oneHourInFuture = Calendar.getInstance()
            oneHourInFuture.add(Calendar.HOUR, 1)

            EventView(
                event = SportEvent(
                    id = "1",
                    sportId = "FOOT",
                    favorite = favorite,
                    timeInMillis = oneHourInFuture.timeInMillis,
                    competitors = listOf("P1", "P2"),
                ),
                onFavoriteClick = {
                    favorite = !favorite
                },
                formatTime = {
                    "HH:MM:SS"
                },
            )
        }

        val card = hasTestTag("EventViewCard")
        val timer = hasTestTag("EventViewCardTimer")
        val favIcon = hasTestTag("EventViewCardFavIcon")

        composeTestRule.onNode(card, useUnmergedTree = true)
            .assertExists()

        composeTestRule.onNode(timer, useUnmergedTree = true)
            .assertExists()

        composeTestRule.onNode(favIcon, useUnmergedTree = true)
            .assertExists()

        composeTestRule.onNode(card, useUnmergedTree = true)
            .performClick()

        composeTestRule.onNode(favIcon, useUnmergedTree = true)
            .assertIsDisplayed()
    }
}