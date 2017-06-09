package io.github.vjames19.kotlinmicroserviceexample.blog.model

import io.github.vjames19.kotlinmicroserviceexample.blog.domain.User
import io.requery.*

/**
 * Created by victor.reventos on 6/8/17.
 */
@Entity
@Table(name = "users")
interface UserModel : Persistable {

    @get:Key
    @get:Generated
    var id: Long

    @get:Column(unique = true)
    var username: String
}

fun UserModel.toDomain(): User = User(id = id, username =  username)

fun User.toModel(): UserModel {
    val ref = this
    return UserModelEntity().apply {
        id = ref.id
        username = ref.username
    }
}

