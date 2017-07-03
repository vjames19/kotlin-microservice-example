package io.github.vjames19.kotlinmicroserviceexample.blog.util

import org.postgresql.util.PSQLException

/**
 * Created by victor.reventos on 7/3/17.
 */

object PSQLViolations {
    val integrityConstraintViolation: String = "23"

}

fun PSQLException.isIntegrityConstrainViolation(): Boolean = serverErrorMessage.sqlState.startsWith(PSQLViolations.integrityConstraintViolation)

inline fun <A> handlePsqlException(throwable: Throwable, crossinline block: (PSQLException) -> A): A = handleException(throwable, block)

inline fun <A, reified T: Exception> handleException(throwable: Throwable, crossinline block: (T) -> A): A {
    var t: Throwable? = throwable
    while (t != null && t !is PSQLException) {
        t = t.cause
    }

    return if (t is T) {
        block(t)
    } else {
        throw throwable
    }
}