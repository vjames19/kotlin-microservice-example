package io.github.vjames19.kotlinmicroserviceexample.blog.util

import io.requery.query.Result
import io.requery.sql.KotlinEntityDataStore
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.function.Supplier

/**
 * Created by victor.reventos on 6/9/17.
 */
fun <T: Any, V> KotlinEntityDataStore<T>.execute(executorService: ExecutorService, block: KotlinEntityDataStore<T>.() -> V): CompletableFuture<V> {
    return CompletableFuture.supplyAsync(Supplier { block() }, executorService)
}

fun <T> Result<T>.firstOption(): Optional<T> = Optional.ofNullable(first())