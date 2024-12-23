package com.example.kaizenmockapp.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

//fun <T> Flow<T>.wrapToResult(): Flow<UseCaseResult<T>> {
//    return this.map { items ->
//        UseCaseResult.Success(items) as UseCaseResult<T>
//    }.catch {
//        it.printStackTrace()
//        emit(UseCaseResult.Failure(e = it))
//    }
//}
//
//sealed interface UseCaseResult<T> {
//    data class Success<T>(
//        val item: T
//    ): UseCaseResult<T>
//
//    data class Failure<T>(
//        val e: Throwable
//    ): UseCaseResult<T>
//}