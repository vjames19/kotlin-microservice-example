package io.github.vjames19.kotlinmicroserviceexample.blog.model

import io.requery.*
import io.github.vjames19.kotlinmicroserviceexample.blog.domain.Post as PostDomain

/**
 * Created by victor.reventos on 6/8/17.
 */
@Entity
@Table(name = "posts")
interface Post : Persistable {

    @get:Key
    @get:Generated
    var id: Long

    @get:ForeignKey(references = User::class)
    var userId: Long

    var content: String
}

fun Post.toDomain(): PostDomain = PostDomain(id = id, userId = userId, content = content)

fun PostDomain.toModel(): Post {
    val ref = this
    return PostEntity().apply {
        id = ref.id
        userId = ref.userId
        content = ref.content
    }
}