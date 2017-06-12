package io.github.vjames19.kotlinmicroserviceexample.blog.api.jooby.endpoint

import io.github.vjames19.kotlinmicroserviceexample.blog.domain.Post
import io.github.vjames19.kotlinmicroserviceexample.blog.domain.User
import io.github.vjames19.kotlinmicroserviceexample.blog.service.UserService
import org.jooby.mvc.Body
import org.jooby.mvc.GET
import org.jooby.mvc.POST
import org.jooby.mvc.Path
import java.util.*
import java.util.concurrent.CompletableFuture
import javax.inject.Inject

/**
 * Created by victor.reventos on 6/12/17.
 */
@Path("/users")
class UsersEndpoint @Inject constructor(val userService: UserService) {

    @Path("/:id")
    @GET
    fun get(id: Long): CompletableFuture<Optional<User>> = userService.getUser(id)

    @POST
    fun create(@Body createUserRequest: CreateUserRequest): CompletableFuture<User> = userService.create(createUserRequest.toDomain())

}

data class CreateUserRequest(val username: String) {
    fun toDomain(): User = User(id = 0, username = username)
}