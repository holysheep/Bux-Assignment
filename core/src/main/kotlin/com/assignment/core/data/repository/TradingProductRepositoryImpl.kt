package com.assignment.core.data.repository

import com.assignment.core.data.service.RestService
import com.assignment.core.domain.error.Failure
import com.assignment.core.domain.error.RetryConnection.retryConnection
import com.assignment.core.domain.model.NetworkResult
import com.assignment.core.domain.model.connect.ErrorCodes
import com.assignment.core.domain.model.connect.ErrorMessage
import com.assignment.core.domain.model.retreive.TradingProduct
import com.assignment.core.domain.repository.TradingProductRepository
import com.squareup.moshi.Moshi
import retrofit2.HttpException

internal class TradingProductRepositoryImpl(
    val restService: RestService,
    val moshi: Moshi
) : TradingProductRepository {

    override suspend fun getProductList(): NetworkResult<List<TradingProduct>> {
        return handleRequest { restService.fetchProductList().sortedBy { it.title } }
    }

    override suspend fun getProductById(productId: String): NetworkResult<TradingProduct> {
        return handleRequest { restService.fetchProductById(productId) }
    }

    private suspend fun <T : Any> handleRequest(func: suspend () -> T): NetworkResult<T> {
        return try {
            NetworkResult.Success(func.invoke())
        } catch (ex: HttpException) {
            val httpError = Failure.HttpError(ex)
            NetworkResult.Error(parseApiErrorMessage(httpError))
        } catch (ex: Exception) {
            retryConnection { handleRequest(func) }
        }
    }

    private fun parseApiErrorMessage(error: Failure.HttpError): Failure {
        val apiErrorMessage = moshi.adapter(ErrorMessage::class.java)
        error.serverErrorBody?.let {
            return when (apiErrorMessage.fromJson(it)?.errorCode) {
                ErrorCodes.AUTH_007,
                ErrorCodes.AUTH_008 -> Failure.NotValidTokenError
                ErrorCodes.AUTH_009,
                ErrorCodes.AUTH_014 -> Failure.AuthError
                ErrorCodes.TRADING_002 -> Failure.UnexpectedError()
                else -> Failure.UnexpectedError()
            }
        }
        return Failure.UnexpectedError()
    }
}