package io.github.vjames19.kotlinmicroserviceexample.blog.api.jooby

import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.github.vjames19.kotlinmicroserviceexample.blog.api.jooby.di.ServiceModule
import io.github.vjames19.kotlinmicroserviceexample.blog.api.jooby.endpoint.PostEndpoint
import io.github.vjames19.kotlinmicroserviceexample.blog.api.jooby.endpoint.UsersEndpoint
import io.github.vjames19.kotlinmicroserviceexample.blog.api.jooby.renderer.ApiError
import io.github.vjames19.kotlinmicroserviceexample.blog.api.jooby.renderer.CompletableFutureMapper
import io.github.vjames19.kotlinmicroserviceexample.blog.api.jooby.renderer.OptionalMapper
import io.github.vjames19.kotlinmicroserviceexample.blog.service.UserServiceError
import io.github.vjames19.kotlinmicroserviceexample.blog.service.UsernameAlreadyExistsUserServiceError
import org.jooby.Jooby.run
import org.jooby.Kooby
import org.jooby.RequestLogger
import org.jooby.Status
import org.jooby.json.Jackson

class App : Kooby({
    use(ServiceModule)
    use(Jackson().module(KotlinModule()))

    map(CompletableFutureMapper())
    map(OptionalMapper())

    use("*", RequestLogger().latency())
    use(UsersEndpoint::class)
    use(PostEndpoint::class)

    error<UserServiceError> { _, resp, err ->
        when (err) {
            is UsernameAlreadyExistsUserServiceError -> resp.sendError(ApiError(Status.BAD_REQUEST, err.message))
        }
    }
})

/**
 * Created by victor.reventos on 6/12/17.
 */
fun main(args: Array<String>) {
    run(::App, args)
}