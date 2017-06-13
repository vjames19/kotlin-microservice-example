package io.github.vjames19.kotlinmicroserviceexample.blog.util

import io.requery.query.Result
import io.requery.query.Scalar
import io.requery.sql.KotlinEntityDataStore
import io.requery.sql.RowCountException
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.function.Supplier

/**
 * Created by victor.reventos on 6/9/17.
 */
inline fun <T : Any, V> KotlinEntityDataStore<T>.execute(executorService: ExecutorService, crossinline block: KotlinEntityDataStore<T>.() -> V): CompletableFuture<V> {
    return CompletableFuture.supplyAsync(Supplier { block() }, executorService)
}

fun <T> Result<T>.firstOption(): Optional<T> = Optional.ofNullable(firstOrNull())

fun Scalar<Int>.toOptional(): Optional<Int> {
    val v = value()
    return if (v > 0) Optional.of(v) else Optional.empty()
}

fun <V> doUpdate(block: () -> V): Optional<V> {
    return try {
        Optional.of(block())
    } catch (e: RowCountException) {
        Optional.empty<V>()
    }
}