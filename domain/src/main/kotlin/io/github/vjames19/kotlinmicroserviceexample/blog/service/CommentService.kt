package io.github.vjames19.kotlinmicroserviceexample.blog.service

import io.github.vjames19.kotlinmicroserviceexample.blog.domain.Comment
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * Created by victor.reventos on 6/8/17.
 */
interface CommentService {

    fun getCommentsForPost(postId: Long): CompletableFuture<List<Comment>>

    fun create(comment: Comment): CompletableFuture<Comment>

    fun update(comment: Comment): CompletableFuture<Optional<Comment>>

    fun delete(id: Long): CompletableFuture<Optional<*>>
}
