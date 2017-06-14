package io.github.vjames19.kotlinmicroserviceexample.blog.api.jooby.endpoint

import io.github.vjames19.kotlinmicroserviceexample.blog.domain.Comment
import io.github.vjames19.kotlinmicroserviceexample.blog.domain.Post
import io.github.vjames19.kotlinmicroserviceexample.blog.service.CommentService
import io.github.vjames19.kotlinmicroserviceexample.blog.service.PostService
import org.jooby.mvc.*
import java.util.*
import java.util.concurrent.CompletableFuture
import javax.inject.Inject

/**
 * Created by victor.reventos on 6/12/17.
 */
@Path("/posts")
class PostEndpoint @Inject constructor(val postService: PostService,
                                       val commentService: CommentService) {

    @GET
    @Path("/:id")
    fun get(id: Long): CompletableFuture<Optional<Post>> {
        return postService.get(id)
    }

    @POST
    fun create(@Body createPostRequest: CreatePostRequest): CompletableFuture<Post> {
        return postService.create(createPostRequest.toDomain())
    }

    @PUT
    @Path("/:id")
    fun update(id: Long, @Body updatePostRequest: UpdatePostRequest): CompletableFuture<Optional<Post>> {
        return postService.update(Post(id = id, userId = updatePostRequest.userId, content = updatePostRequest.content))
    }

    @DELETE
    @Path("/:id")
    fun delete(id: Long): CompletableFuture<Optional<*>> {
        return postService.delete(id)
    }

    @GET
    @Path("/:id/comments")
    fun getComments(id: Long): CompletableFuture<List<Comment>> {
        return commentService.getCommentsForPost(id)
    }

    @POST
    @Path("/:id/comments")
    fun addComment(id: Long, @Body commentRequest: CommentRequest): CompletableFuture<Comment> {
        return commentService.create(Comment(id = 0, userId = commentRequest.userId, postId = id, text = commentRequest.text))
    }

    @PUT
    @Path("/:id/comments/:commentId")
    fun updateComment(id: Long, commentId: Long, @Body commentRequest: CommentRequest): CompletableFuture<Optional<Comment>> {
        return commentService.update(Comment(id = commentId, userId = commentRequest.userId, postId = id, text = commentRequest.text))
    }

    @DELETE
    @Path("/:id/comments/:commentId")
    fun deleteComment(commentId: Long): CompletableFuture<Optional<*>> {
        return commentService.delete(commentId)
    }
}

data class CreatePostRequest(val userId: Long, val content: String) {
    fun toDomain(): Post = Post(id = 0, userId = userId, content = content)
}

data class UpdatePostRequest(val userId: Long, val content: String)

data class CommentRequest(val userId: Long, val text: String)