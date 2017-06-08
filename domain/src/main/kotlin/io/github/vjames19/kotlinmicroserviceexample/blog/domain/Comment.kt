package io.github.vjames19.kotlinmicroserviceexample.blog.domain

/**
 * Created by victor.reventos on 6/8/17.
 */
data class Comment(val id: Long, val userId: Long, val postId: Long, val text: String)
