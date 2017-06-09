package io.github.vjames19.kotlinmicroserviceexample.blog.service

import io.github.vjames19.kotlinmicroserviceexample.blog.domain.Post
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * Created by victor.reventos on 6/8/17.
 */
interface PostService {

    fun getAllPostsForUser(userId: Long): CompletableFuture<List<Post>>

    fun get(id: Long): CompletableFuture<Optional<Post>>

    fun create(post: Post): CompletableFuture<Post>

    fun update(post: Post): CompletableFuture<Optional<Post>>

    fun delete(id: Long): CompletableFuture<Optional<*>>

}
