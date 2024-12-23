package com.example.kaizenmockapp.usecase

import com.example.kaizenmockapp.data.SportEvent
import com.example.kaizenmockapp.repository.FavoriteRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
) {
    suspend fun execute(event: SportEvent): Boolean {
        return favoriteRepository.toggleFavorite(event)
    }
}