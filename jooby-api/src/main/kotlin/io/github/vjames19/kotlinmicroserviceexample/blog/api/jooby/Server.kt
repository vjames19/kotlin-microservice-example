package io.github.vjames19.kotlinmicroserviceexample.blog.api.jooby

import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.github.vjames19.kotlinmicroserviceexample.blog.api.jooby.di.ServiceModule
import io.github.vjames19.kotlinmicroserviceexample.blog.api.jooby.endpoint.PostEndpoint
import io.github.vjames19.kotlinmicroserviceexample.blog.api.jooby.endpoint.UsersEndpoint
import io.github.vjames19.kotlinmicroserviceexample.blog.api.jooby.renderer.OptionalMapper
import org.jooby.*
import org.jooby.json.Jackson


fun app(): Jooby {
    return jooby {
        use(ServiceModule)
        use(Jackson().module(KotlinModule()))

        map(AsyncMapper())
        map(OptionalMapper())

        use("*", RequestLogger().latency())
        use(UsersEndpoint::class)
        use(PostEndpoint::class)
    }
}
/**
 * Created by victor.reventos on 6/12/17.
 */
fun main(args: Array<String>) {
    Jooby.run(::app, args)
//    run(*args) {
//        use(ServiceModule)
//        use(Jackson().module(KotlinModule()))
//
//        map(AsyncMapper())
//        map(OptionalMapper())
//
//        use("*", RequestLogger().latency())
//        use(UsersEndpoint::class)
//        use(PostEndpoint::class)
//    }
}
