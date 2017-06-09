package io.github.vjames19.kotlinmicroserviceexample.blog.service

import io.github.vjames19.kotlinmicroserviceexample.blog.di.DbExecutorService
import io.github.vjames19.kotlinmicroserviceexample.blog.domain.Post
import io.github.vjames19.kotlinmicroserviceexample.blog.model.PostModel
import io.github.vjames19.kotlinmicroserviceexample.blog.model.PostModelEntity
import io.github.vjames19.kotlinmicroserviceexample.blog.model.toDomain
import io.github.vjames19.kotlinmicroserviceexample.blog.model.toModel
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
        insert(post.toModel()).toDomain()
    }

    override fun update(post: Post): CompletableFuture<Optional<Post>> = db.execute(executor) {
        db.update()
                .set(PostModelEntity.CONTENT, post.content)
                .where(PostModelEntity::id eq (post.id))
                .get()
                .value()
                .convertUpdateCodeToOptional()
                .map { post }
    }

    override fun delete(id: Long): CompletableFuture<Optional<*>> = db.execute(executor) {
        (db.delete(PostModel::class) where (PostModel::id eq id))
                .get()
                .value()
                .convertUpdateCodeToOptional()
    }
}