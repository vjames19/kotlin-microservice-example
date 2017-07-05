package io.github.vjames19.kotlinmicroserviceexample.blog.api.jooby

import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.github.vjames19.kotlinmicroserviceexample.blog.api.jooby.di.ServiceModule
import io.github.vjames19.kotlinmicroserviceexample.blog.api.jooby.endpoint.PostEndpoint
import io.github.vjames19.kotlinmicroserviceexample.blog.api.jooby.endpoint.UsersEndpoint
import io.github.vjames19.kotlinmicroserviceexample.blog.api.jooby.error.UserServiceErrorHandler
import io.github.vjames19.kotlinmicroserviceexample.blog.api.jooby.renderer.CompletableFutureMapper
import io.github.vjames19.kotlinmicroserviceexample.blog.api.jooby.renderer.OptionalMapper
import org.jooby.Jooby.run
import org.jooby.Kooby
import org.jooby.RequestLogger
import org.jooby.json.Jackson

class App : Kooby({
    use(ServiceModule)
    use(Jackson().module(KotlinModule()))

    map(CompletableFutureMapper())
    map(OptionalMapper())

    use("*", RequestLogger().latency())
    use(UsersEndpoint::class)
    use(PostEndpoint::class)

    error(UserServiceErrorHandler()::handle)
})

/**
 * Created by victor.reventos on 6/12/17.
 */
fun main(args: Array<String>) {
    run(::App, args)
}