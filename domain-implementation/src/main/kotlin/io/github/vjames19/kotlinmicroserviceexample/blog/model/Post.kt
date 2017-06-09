package io.github.vjames19.kotlinmicroserviceexample.blog.model

import io.requery.*

/**
 * Created by victor.reventos on 6/8/17.
 */
@Entity
interface Post : Persistable {

    @get:Key
    @get:Generated
    var id: Long

    @get:ManyToOne
    @get:ForeignKey(references = User::class)
    var userId: Long

    var content: String
}