package dev.andre.vkeducation.presentation.common

import dev.andre.vkeducation.presentation.di.DefaultDispatcher
import dev.andre.vkeducation.presentation.di.IoDispatchers
import dev.andre.vkeducation.presentation.di.MainDispatchers
import dev.andre.vkeducation.presentation.di.UnconfinedDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class CoroutineDispatchersImpl @Inject constructor(
    @MainDispatchers private val main: CoroutineDispatcher,
    @IoDispatchers private val io: CoroutineDispatcher,
    @DefaultDispatcher private val default: CoroutineDispatcher,
    @UnconfinedDispatcher private val unconfined: CoroutineDispatcher
) : CoroutineDispatchers {
    override fun main(): CoroutineDispatcher = main

    override fun io(): CoroutineDispatcher = io

    override fun default(): CoroutineDispatcher = default

    override fun unconfined(): CoroutineDispatcher = unconfined
}