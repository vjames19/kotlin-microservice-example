package io.github.vjames19.kotlinmicroserviceexample.blog.service

/**
 * Created by victor.reventos on 7/3/17.
 */
sealed class UserServiceError(message: String? = null, cause: Throwable? = null) : RuntimeException(message, cause)

data class UsernameAlreadyExistsUserServiceError(val username: String): UserServiceError("username $username already exists")