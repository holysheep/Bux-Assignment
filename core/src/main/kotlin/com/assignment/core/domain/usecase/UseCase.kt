package com.assignment.core.domain.usecase

import com.assignment.core.domain.model.NetworkResult

internal interface UseCase {
    // todo: add list of subscribe params to send
//    interface RequestUseCase<P: Params, T: Any?> : UseCase {
//        suspend fun execute(params: P?): NetworkResult<T>
//    }

    interface RetrieveUseCase<T : Any?> : UseCase {
        suspend fun execute(): NetworkResult<T?>
    }
}