package io.github.vjames19.kotlinmicroserviceexample.blog.model

import io.github.vjames19.kotlinmicroserviceexample.blog.domain.Post
import io.requery.*

/**
 * Created by victor.reventos on 6/8/17.
 */
@Entity
@Table(name = "posts")
interface PostModel : Persistable {

    @get:Key
    @get:Generated
    var id: Long

    @get:ForeignKey(references = UserModel::class)
    var userId: Long

    var content: String
}

fun PostModel.toDomain(): Post = Post(id = id, userId = userId, content = content)

fun Post.toModel(): PostModel {
    val ref = this
    return PostModelEntity().apply {
        id = ref.id
        userId = ref.userId
        content = ref.content
    }
}