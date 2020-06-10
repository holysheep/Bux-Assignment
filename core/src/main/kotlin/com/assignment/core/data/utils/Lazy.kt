package com.assignment.core.data.utils

import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope
import kotlin.reflect.KClass

/**
 * Lazy koin injection, solution for too-early starting socket service
 */
class Lazy<T : Any>(
    private val scope: Scope,
    private val klass: KClass<T>,
    private val qualifier: Qualifier? = null,
    private val parameters: ParametersDefinition? = null
) {
    private val value by lazy { scope.get<T>(klass, qualifier, parameters) }
    fun get(): T = value
}

inline fun <reified T : Any> Scope.lazy(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
): Lazy<T> {
    return Lazy(
        this,
        T::class,
        qualifier,
        parameters
    )
}