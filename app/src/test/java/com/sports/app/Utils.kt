package com.sports.app

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun <T> mockFlow(block: () -> T): Flow<T> = flow {
    emit(block.invoke())
}
