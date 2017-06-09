package io.github.vjames19.kotlinmicroserviceexample.blog.model

import io.requery.*
import io.github.vjames19.kotlinmicroserviceexample.blog.domain.User as UserDomain
/**
 * Created by victor.reventos on 6/8/17.
 */
@Entity
@Table(name = "users")
interface User : Persistable {

    @get:Key
    @get:Generated
    var id: Long

    @get:Column(unique = true)
    var username: String
}

fun User.toDomain(): UserDomain = UserDomain(id = id, username =  username)

fun UserDomain.toModel(): User {
    val ref = this
    return UserEntity().apply {
        id = ref.id
        username = ref.username
    }
}

