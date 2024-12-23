package com.example.kaizenmockapp.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map


fun <T> Flow<T?>.wrapToResult(): Flow<KaizenResult<T>> {
    return this.map { items ->
        if (items == null) {
            KaizenResult.Loading
        } else {
            KaizenResult.Success(items)
        }
    }.catch {
        it.printStackTrace()
        emit(KaizenResult.Failure(e = it))
    }
}

sealed interface KaizenResult<out T> {
    data class Success<T>(
        val item: T,
    ): KaizenResult<T>

    data class Failure<T>(
        val e: Throwable
    ): KaizenResult<T>

    data object Loading: KaizenResult<Nothing>
}
