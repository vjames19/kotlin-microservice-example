package io.github.vjames19.kotlinmicroserviceexample.blog.service

import io.github.vjames19.kotlinmicroserviceexample.blog.domain.User
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * Created by victor.reventos on 6/8/17.
 */
interface UserService {

    fun getUser(id: Long): CompletableFuture<Optional<User>>

    @Throws(UserServiceError::class)
    fun create(user: User): CompletableFuture<User>
}