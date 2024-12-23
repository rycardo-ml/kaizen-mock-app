package com.example.kaizenmockapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kaizenmockapp.feature.dashboard.DashboardScreen
import com.example.kaizenmockapp.feature.dashboard.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val viewModel: DashboardViewModel = viewModel()
            val state by viewModel.stateFlow.collectAsStateWithLifecycle()

            DashboardScreen(
                viewState = state,
                fetchData = viewModel::fetchSports,
                toggleExpired = viewModel::toggleExpired,
                onShrinkExpandClick = viewModel::onShrinkExpandClick,
                onFilterFavoriteClick = viewModel::onFilterFavoriteClick,
                onFavoriteClick = viewModel::onFavoriteClicked,
                formatTime = viewModel::formatTime,
            )
        }
    }
}