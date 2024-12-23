package com.example.kaizenmockapp.feature.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaizenmockapp.data.Sport
import com.example.kaizenmockapp.data.SportEvent
import com.example.kaizenmockapp.usecase.ClearDataUseCase
import com.example.kaizenmockapp.usecase.GetEventsUseCase
import com.example.kaizenmockapp.usecase.ObserveSportsUseCase
import com.example.kaizenmockapp.usecase.ToggleFavoriteUseCase
import com.example.kaizenmockapp.util.AsyncParam
import com.example.kaizenmockapp.util.KaizenResult
import com.example.kaizenmockapp.util.TimeManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val fetchSportsUseCase: ObserveSportsUseCase,
    private val getEventsUseCase: GetEventsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val clearDataUseCase: ClearDataUseCase,
    private val timeManager: TimeManager,
): ViewModel() {

    private val _stateFlow = MutableStateFlow<ViewState>(
        value = ViewState.Loading
    )
    val stateFlow = _stateFlow.asStateFlow()

    private var filterExpired = false

    fun fetchSports() = viewModelScope.launch {
        _stateFlow.update { return@update ViewState.Loading }

        //clearDataUseCase.execute()

        fetchSportsUseCase.execute().collect { result ->
            when (result) {
                is KaizenResult.Success -> {
                    _stateFlow.update {
                        return@update ViewState.Success(ComposeState(
                            sports = result.item.map {
                                SportData(
                                    sport = it,
                                    opened = true,
                                    filterFavorite = false
                                )
                            },
                            events = result.item.associate { it.id to AsyncParam.Loading }
                        ))
                    }

                    fetchEvents(result.item)
                }

                is KaizenResult.Failure -> {
                    _stateFlow.update { return@update ViewState.Error("Error fetching data: {${result.e.message}}") }
                }

                KaizenResult.Loading -> {
                    _stateFlow.update { return@update ViewState.Loading }
                }
            }
        }
    }

    private fun fetchEvents(sports: List<Sport>) {
        viewModelScope.launch {
            coroutineScope {
                sports.forEach { sport ->
                    launch {
                        val events = getEventsUseCase.execute(
                            sportId = sport.id,
                            filter = GetEventsUseCase.Filter.None,
                            filterExpired = filterExpired
                        )

                        updateSuccessComposeState { oldState ->
                            oldState.copy(
                                events = oldState.events.entries.associate { entry ->
                                    if (entry.key != sport.id) {
                                        entry.key to entry.value
                                    } else {
                                        entry.key to AsyncParam.Success(events)
                                    }
                                },

                                sports = oldState.sports.map {
                                    it.copy(
                                        filterFavorite = false
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    fun onShrinkExpandClick(sport: Sport) {
        updateSuccessComposeState { oldState ->
            oldState.copy(
                sports = oldState.sports.map { oldSportData ->
                    if (sport.id != oldSportData.sport.id)
                        return@map oldSportData

                    return@map  oldSportData.copy(
                        opened = !oldSportData.opened
                    )
                }
            )
        }
    }

    fun formatTime(timeInMilli: Long): String {
        return timeManager.formatElapsedTime(timeInMilli)
    }

    fun onFavoriteClicked(event: SportEvent) {
        viewModelScope.launch {
            val added = toggleFavoriteUseCase.execute(event)

            updateSuccessComposeState { oldState ->
                oldState.copy(
                    events = oldState.events.entries.associate { entry ->
                        if (entry.key != event.sportId) {
                            entry.key to entry.value
                        } else {
                            entry.key to toggleFavorite(entry.value, event, added)
                        }
                    }
                )
            }
        }
    }

    fun onFilterFavoriteClick(sport: Sport, checked: Boolean) {
        viewModelScope.launch {
            updateSuccessComposeState { oldState ->
                oldState.copy(
                    events = oldState.events.entries.associate { entry ->
                        if (entry.key != sport.id) {
                            entry.key to entry.value
                        } else {
                            entry.key to when (val async = entry.value) {
                                is AsyncParam.Success -> async.copy(filtering = true)
                                else -> async
                            }
                        }
                    }
                )
            }

            val filterType =
                if (checked) GetEventsUseCase.Filter.Favorite
                else GetEventsUseCase.Filter.None

            val events = getEventsUseCase.execute(
                sportId = sport.id,
                filter = filterType,
                filterExpired = filterExpired
            )

            updateSuccessComposeState { oldState ->
                oldState.copy(
                    sports = oldState.sports.map { oldSport ->
                        if (oldSport.sport.id != sport.id) return@map oldSport

                        return@map oldSport.copy(
                            filterFavorite = checked
                        )
                    },
                    events = oldState.events.entries.associate { entry ->
                        if (entry.key != sport.id) {
                            entry.key to entry.value
                        } else {
                            entry.key to AsyncParam.Success(events, false)
                        }
                    }
                )
            }
        }
    }

    fun toggleExpired() {
        filterExpired = !filterExpired

        viewModelScope.launch {
            updateSuccessComposeState { oldState ->
                oldState.copy(
                    events = oldState.events.entries.associate { entry ->
                        entry.key to when (val async = entry.value) {
                            is AsyncParam.Success -> async.copy(filtering = true)
                            else -> async
                        }
                    }
                )
            }

            val sports = (_stateFlow.value as ViewState.Success).state.sports.map {
                it.sport
            }

            fetchEvents(sports)
        }
    }

    private fun toggleFavorite(
        value: AsyncParam<List<SportEvent>>,
        event: SportEvent,
        added: Boolean
    ): AsyncParam<List<SportEvent>> {
        if (value !is AsyncParam.Success) return value

        return value.copy(
            item = value.item.map { listEvent ->
                if (listEvent.id != event.id) {
                    return@map listEvent
                }

                listEvent.copy(
                    favorite = added
                )
            }
        )
    }

    private fun updateSuccessComposeState(onChange: (oldComposeState: ComposeState) -> ComposeState) {
        _stateFlow.update { oldViewState ->
            if (oldViewState is ViewState.Success) {
                return@update oldViewState.copy(
                    state = onChange(oldViewState.state)
                )
            }

            return@update oldViewState
        }
    }

    data class ComposeState(
        val sports: List<SportData>,
        val events: Map<String, AsyncParam<List<SportEvent>>>,
    )

    data class SportData(
        val sport: Sport,
        val opened: Boolean,
        val filterFavorite: Boolean
    )

    sealed interface ViewState {
        data object Loading: ViewState
        data class Error(val message: String): ViewState
        data class Success(val state: ComposeState): ViewState
    }
}