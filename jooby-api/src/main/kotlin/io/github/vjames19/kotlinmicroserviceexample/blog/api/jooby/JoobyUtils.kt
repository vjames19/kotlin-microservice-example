package io.github.vjames19.kotlinmicroserviceexample.blog.api.jooby

import io.github.vjames19.kotlinmicroserviceexample.blog.api.jooby.renderer.ApiError
import org.jooby.Err
import org.jooby.Kooby
import org.jooby.Request
import org.jooby.Response

/**
 * Created by victor.reventos on 7/3/17.
 */
fun Response.sendError(error: ApiError) {
    send(error.toResult())
}

inline fun <reified T: Exception> Kooby.error(crossinline block: (Request, Response, T) -> Unit) {
    err(T::class.java) { req: Request, resp: Response, err: Err ->
        block(req, resp, err.cause!! as T)
    }
}