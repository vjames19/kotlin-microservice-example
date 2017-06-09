package io.github.vjames19.kotlinmicroserviceexample.blog.model

import io.github.vjames19.kotlinmicroserviceexample.blog.domain.Comment
import io.requery.*


/**
 * Created by victor.reventos on 6/8/17.
 */
@Entity
@Table(name = "comments")
interface CommentModel : Persistable {

    @get:Key
    @get:Generated
    var id: Long

    @get:ForeignKey(references = UserModel::class)
    var userId: Long

    @get:ManyToOne
    @get:ForeignKey(references = PostModel::class)
    var postId: Long

    var content: String
}

fun CommentModel.toDomain(): Comment {
    return Comment(id = id, postId = postId, userId = userId, text = content)
}

fun Comment.toModel(): CommentModel {
    val ref = this
    return CommentModelEntity().apply {
        id = ref.id
        userId = ref.userId
        postId = ref.postId
        content = ref.text
    }
}