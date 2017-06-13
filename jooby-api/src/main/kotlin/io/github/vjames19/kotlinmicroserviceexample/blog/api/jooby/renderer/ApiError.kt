package io.github.vjames19.kotlinmicroserviceexample.blog.api.jooby.renderer

import org.jooby.Status

/**
 * Created by victor.reventos on 6/13/17.
 */
data class ApiError(val status: Int, val message: Any) {
    constructor(status: Status, message: Any) : this(status.value(), message)

    constructor(status: Status) : this(status.value(), status.reason())
}