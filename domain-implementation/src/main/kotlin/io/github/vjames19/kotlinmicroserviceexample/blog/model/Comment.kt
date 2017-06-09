package io.github.vjames19.kotlinmicroserviceexample.blog.model

import io.requery.*
import io.github.vjames19.kotlinmicroserviceexample.blog.domain.Comment as CommentDomain


/**
 * Created by victor.reventos on 6/8/17.
 */
@Entity
@Table(name = "comments")
interface Comment : Persistable {

    @get:Key
    @get:Generated
    var id: Long

    @get:ForeignKey(references = User::class)
    var userId: Long

    @get:ManyToOne
    @get:ForeignKey(references = Post::class)
    var postId: Long

    var content: String
}

fun Comment.toDomain(): CommentDomain {
    return CommentDomain(id = id, postId = postId, userId = userId, text = content)
}

fun CommentDomain.toModel(): Comment {
    val ref = this
    return CommentEntity().apply {
        id = ref.id
        userId = ref.userId
        postId = ref.postId
        content = ref.text
    }
}