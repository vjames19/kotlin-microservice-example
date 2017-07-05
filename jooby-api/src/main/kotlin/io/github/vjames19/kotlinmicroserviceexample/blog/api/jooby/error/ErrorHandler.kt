package io.github.vjames19.kotlinmicroserviceexample.blog.api.jooby.error

import io.github.vjames19.kotlinmicroserviceexample.blog.api.jooby.sendError
import io.github.vjames19.kotlinmicroserviceexample.blog.service.UserServiceError
import io.github.vjames19.kotlinmicroserviceexample.blog.service.UsernameAlreadyExistsUserServiceError
import org.jooby.Request
import org.jooby.Response
import org.jooby.Status

/**
 * Created by victor.reventos on 7/5/17.
 */
interface ErrorHandler<in T: Exception> {
    fun handle(req: Request, resp: Response, error: T): Unit
}

class UserServiceErrorHandler : ErrorHandler<UserServiceError> {
    override fun handle(req: Request, resp: Response, error: UserServiceError) = when (error) {
        is UsernameAlreadyExistsUserServiceError -> resp.sendError(ApiError(Status.BAD_REQUEST, error.message))
    }
}