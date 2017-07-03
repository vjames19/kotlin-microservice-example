package io.github.vjames19.kotlinmicroserviceexample.blog.api.jooby.renderer

import org.jooby.Deferred
import org.jooby.Route
import java.util.concurrent.CompletableFuture

/**
 * Created by victor.reventos on 7/3/17.
 */
class CompletableFutureMapper : Route.Mapper<CompletableFuture<*>> {

    @Throws(Throwable::class)
    override fun map(future: CompletableFuture<*>): Any {
        return Deferred { deferred ->
            future.whenComplete { value, x ->
                if (x != null) {
                    val t = x.cause ?: x
                    deferred.reject(t)
                } else {
                    deferred.resolve(value)
                }
            }
        }
    }

}