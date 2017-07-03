package io.github.vjames19.kotlinmicroserviceexample.blog.util

import io.requery.query.Result
import io.requery.query.Scalar
import io.requery.sql.RowCountException
import java.util.*

/**
 * Created by victor.reventos on 6/9/17.
 */
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