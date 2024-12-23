package com.example.kaizenmockapp.usecase

import com.example.kaizenmockapp.data.Sport
import com.example.kaizenmockapp.repository.SportRepository
import com.example.kaizenmockapp.util.KaizenResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ObserveSportsUseCase @Inject constructor(
    private val sportsRepository: SportRepository
) {
    fun execute(): Flow<KaizenResult<List<Sport>>> {
        return sportsRepository.observeSports()
    }
}