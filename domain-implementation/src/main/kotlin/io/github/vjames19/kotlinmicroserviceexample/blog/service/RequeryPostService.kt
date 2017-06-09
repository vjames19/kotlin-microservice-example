package io.github.vjames19.kotlinmicroserviceexample.blog.service

import io.github.vjames19.kotlinmicroserviceexample.blog.di.DbExecutorService
import io.github.vjames19.kotlinmicroserviceexample.blog.domain.Post
import io.github.vjames19.kotlinmicroserviceexample.blog.model.PostEntity
import io.github.vjames19.kotlinmicroserviceexample.blog.model.toModel
import io.github.vjames19.kotlinmicroserviceexample.blog.model.toDomain
import io.github.vjames19.kotlinmicroserviceexample.blog.util.convertUpdateCodeToOptional
import io.github.vjames19.kotlinmicroserviceexample.blog.util.execute
import io.github.vjames19.kotlinmicroserviceexample.blog.util.firstOption
import io.requery.Persistable
import io.requery.kotlin.eq
import io.requery.sql.KotlinEntityDataStore
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import javax.inject.Inject
import io.github.vjames19.kotlinmicroserviceexample.blog.model.Post as PostModel

/**
 * Created by victor.reventos on 6/9/17.
 */
class RequeryPostService @Inject constructor(val db: KotlinEntityDataStore<Persistable>,
                                             @DbExecutorService val executor: ExecutorService) : PostService {

    override fun getAllPostsForUser(userId: Long): CompletableFuture<List<Post>> = db.execute(executor) {
        (select(PostModel::class) where (PostModel::userId eq (userId))).get()
                .map(PostModel::toDomain)
    }

    override fun get(id: Long): CompletableFuture<Optional<Post>> = db.execute(executor) {
        (select(PostModel::class) where (PostModel::id eq (id))).get()
                .firstOption()
                .map(PostModel::toDomain)
    }

    override fun create(post: Post): CompletableFuture<Post> = db.execute(executor) {
        insert(PostEntity().apply {
            userId = post.userId
            content = post.content
        }).toDomain()
    }

    override fun update(post: Post): CompletableFuture<Post> = db.execute(executor) {
        db.update(post.toModel()).toDomain()
    }

    override fun delete(id: Long): CompletableFuture<Optional<*>> = db.execute(executor) {
        (db.delete(PostModel::class) where (PostModel::id eq id))
                .get()
                .value()
                .convertUpdateCodeToOptional()
    }
}