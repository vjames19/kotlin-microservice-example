package io.github.vjames19.kotlinmicroserviceexample.blog.api.jooby.endpoint

import io.github.vjames19.kotlinmicroserviceexample.blog.domain.Post
import io.github.vjames19.kotlinmicroserviceexample.blog.service.CommentService
import io.github.vjames19.kotlinmicroserviceexample.blog.service.PostService
import org.jooby.mvc.GET
import org.jooby.mvc.Path
import java.util.*
import java.util.concurrent.CompletableFuture
import javax.inject.Inject

/**
 * Created by victor.reventos on 6/12/17.
 */
@Path("/posts")
class PostEndpoint @Inject constructor(val postService: PostService,
                                       val commentService: CommentService) {

    @Path("/:id")
    @GET
    fun get(id: Long): CompletableFuture<Optional<Post>> {
        return postService.get(id)
    }
}