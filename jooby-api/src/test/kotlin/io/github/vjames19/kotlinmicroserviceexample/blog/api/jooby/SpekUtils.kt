package io.github.vjames19.kotlinmicroserviceexample.blog.api.jooby

import org.jetbrains.spek.api.dsl.SpecBody
import org.jooby.Jooby

/**
 * Created by victor.reventos on 6/13/17.
 */
fun SpecBody.jooby(app: Jooby, body: SpecBody.() -> Unit) {
    beforeGroup {
        app.start("server.join=false")
    }

    body()

    afterGroup {
        app.stop()
    }
}
