package com.example.kaizenmockapp.usecase

import com.example.kaizenmockapp.data.SportEvent
import com.example.kaizenmockapp.repository.EventRepository
import com.example.kaizenmockapp.repository.FavoriteRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class GetEventsUseCaseTest {

    @Test
    fun `test happy path no filter`() = runTest {
        val mockEventRepository = mockk<EventRepository>()
        val mockFavoriteRepository = mockk<FavoriteRepository>()

        coEvery { mockEventRepository.getEvents("VOLLEY") } returns
            listOf(
                SportEvent(
                    id = "1",
                    competitors = listOf("P1", "P2"),
                    sportId = "VOLLEY",
                    timeInMillis = 1734961892L,
                    favorite = false
                ),
                SportEvent(
                    id = "2",
                    competitors = listOf("P3", "P4"),
                    sportId = "VOLLEY",
                    timeInMillis = 1734961892L,
                    favorite = false
                ),
            )

        coEvery { mockFavoriteRepository.getFavorites() } returns listOf()

        val useCase = GetEventsUseCase(
            eventRepository = mockEventRepository,
            favoriteRepository = mockFavoriteRepository
        )

        val events = useCase.execute("VOLLEY", GetEventsUseCase.Filter.None)

        assertEquals(2, events.size)
    }

    @Test
    fun `test happy path filter`() = runTest {
        val mockEventRepository = mockk<EventRepository>()
        val mockFavoriteRepository = mockk<FavoriteRepository>()

        coEvery { mockEventRepository.getEvents("VOLLEY") } returns
                listOf(
                    SportEvent(
                        id = "1",
                        competitors = listOf("P1", "P2"),
                        sportId = "VOLLEY",
                        timeInMillis = 1734961892L,
                        favorite = false
                    ),
                    SportEvent(
                        id = "2",
                        competitors = listOf("P3", "P4"),
                        sportId = "VOLLEY",
                        timeInMillis = 1734961892L,
                        favorite = false
                    ),
                )

        coEvery { mockFavoriteRepository.getFavorites() } returns listOf("2")

        val useCase = GetEventsUseCase(
            eventRepository = mockEventRepository,
            favoriteRepository = mockFavoriteRepository
        )

        val events = useCase.execute("VOLLEY", GetEventsUseCase.Filter.Favorite)

        assertEquals(1, events.size)
        val event = events[0]
        assertEquals("2", event.id)
    }
}