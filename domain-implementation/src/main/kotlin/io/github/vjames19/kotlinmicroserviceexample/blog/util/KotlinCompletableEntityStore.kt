package io.github.vjames19.kotlinmicroserviceexample.blog.util

import io.requery.kotlin.BlockingEntityStore
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor
import java.util.function.Supplier

/**
 * Created by victor.reventos on 6/14/17.
 */
class KotlinCompletableEntityStore<T: Any>(private val store: BlockingEntityStore<T>,
                                           val executor: Executor): BlockingEntityStore<T> by store {

    inline fun <R> execute(crossinline block: BlockingEntityStore<T>.() -> R): CompletableFuture<R> {
        return CompletableFuture.supplyAsync(Supplier { block() }, executor)
    }

}