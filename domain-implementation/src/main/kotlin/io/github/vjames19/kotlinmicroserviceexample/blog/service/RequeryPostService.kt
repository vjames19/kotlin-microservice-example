package io.github.vjames19.kotlinmicroserviceexample.blog.service

import io.github.vjames19.kotlinmicroserviceexample.blog.domain.Post
import io.github.vjames19.kotlinmicroserviceexample.blog.model.PostModel
import io.github.vjames19.kotlinmicroserviceexample.blog.model.toDomain
import io.github.vjames19.kotlinmicroserviceexample.blog.model.toModel
import io.github.vjames19.kotlinmicroserviceexample.blog.util.KotlinCompletableEntityStore
import io.github.vjames19.kotlinmicroserviceexample.blog.util.doUpdate
import io.github.vjames19.kotlinmicroserviceexample.blog.util.firstOption
import io.github.vjames19.kotlinmicroserviceexample.blog.util.toOptional
import io.requery.Persistable
import io.requery.kotlin.eq
import java.util.*
import java.util.concurrent.CompletableFuture
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by victor.reventos on 6/9/17.
 */
@Singleton
class RequeryPostService @Inject constructor(val db: KotlinCompletableEntityStore<Persistable>) : PostService {

    override fun getAllPostsForUser(userId: Long): CompletableFuture<List<Post>> = db.execute {
        (select(PostModel::class) where (PostModel::userId eq (userId))).get()
                .map(PostModel::toDomain)
    }

    override fun get(id: Long): CompletableFuture<Optional<Post>> = db.execute {
        (select(PostModel::class) where (PostModel::id eq (id)) limit 1).get()
                .firstOption()
                .map(PostModel::toDomain)
    }

    override fun create(post: Post): CompletableFuture<Post> = db.execute {
        insert(post.toModel()).toDomain()
    }

    override fun update(post: Post): CompletableFuture<Optional<Post>> = db.execute {
        doUpdate {
            update(post.toModel()).toDomain()
        }
    }

    override fun delete(id: Long): CompletableFuture<Optional<*>> = db.execute {
        (db.delete(PostModel::class) where (PostModel::id eq id))
                .get()
                .toOptional()
    }
}