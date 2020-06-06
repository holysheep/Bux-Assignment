package com.assignment.core.domain.usecase

import com.assignment.core.domain.model.NetworkResult
import kotlinx.coroutines.flow.Flow

internal interface UseCase {
    interface RetrieveParamsUseCase<P: Params, T: Any?> : UseCase {
        suspend fun execute(params: P): NetworkResult<T>
    }

    interface RetrieveUseCase<T : Any?> : UseCase {
        suspend fun execute(): NetworkResult<T?>
    }

    interface RetrieveParamsFlowUseCase<P: Params, T: Any?> : UseCase {
        fun execute(params: P): Flow<NetworkResult<T>>
    }

    interface RetrieveFlowUseCase<T : Any?> : UseCase {
        fun execute(): Flow<NetworkResult<T?>>
    }

    abstract class Params
}