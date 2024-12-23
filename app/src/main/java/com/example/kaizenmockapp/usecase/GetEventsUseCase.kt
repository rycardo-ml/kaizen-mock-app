package com.example.kaizenmockapp.usecase

import com.example.kaizenmockapp.data.SportEvent
import com.example.kaizenmockapp.repository.EventRepository
import com.example.kaizenmockapp.repository.FavoriteRepository
import kotlinx.coroutines.delay
import java.util.Calendar
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class GetEventsUseCase @Inject constructor(
    private val eventRepository: EventRepository,
    private val favoriteRepository: FavoriteRepository,
) {

    suspend fun execute(
        sportId: String,
        filter: Filter,
        filterExpired: Boolean,
    ): List<SportEvent> {
        val favorites = favoriteRepository.getFavorites()

        val currentTimeInMillis = Calendar.getInstance().timeInMillis

        val events = eventRepository.getEvents(sportId).map {
            it.copy(
                favorite = favorites.contains(it.id)
            )
        }

        //FIXME remove this delay
        val random = Math.random() * 5
        delay(random.seconds)

        return events.filter {
            (filter == Filter.None || it.favorite) &&
            (!filterExpired || it.timeInMillis - currentTimeInMillis > 0)
        }
    }

    enum class Filter {
        None,
        Favorite
    }
}
