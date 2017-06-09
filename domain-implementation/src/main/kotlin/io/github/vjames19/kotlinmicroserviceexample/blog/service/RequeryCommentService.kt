package io.github.vjames19.kotlinmicroserviceexample.blog.service

import io.github.vjames19.kotlinmicroserviceexample.blog.di.DbExecutorService
import io.github.vjames19.kotlinmicroserviceexample.blog.domain.Comment
import io.github.vjames19.kotlinmicroserviceexample.blog.model.CommentEntity
import io.github.vjames19.kotlinmicroserviceexample.blog.model.toDomain
import io.github.vjames19.kotlinmicroserviceexample.blog.model.toModel
import io.github.vjames19.kotlinmicroserviceexample.blog.util.convertUpdateCodeToOptional
import io.github.vjames19.kotlinmicroserviceexample.blog.util.execute
import io.requery.Persistable
import io.requery.kotlin.eq
import io.requery.sql.KotlinEntityDataStore
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import javax.inject.Inject
import io.github.vjames19.kotlinmicroserviceexample.blog.model.Comment as CommentModel
import io.github.vjames19.kotlinmicroserviceexample.blog.model.User as UserDao

/**
 * Created by victor.reventos on 6/8/17.
 */
class RequeryCommentService @Inject constructor(val db: KotlinEntityDataStore<Persistable>,
                                                @DbExecutorService val executor: ExecutorService) : CommentService {

    override fun getCommentsForPost(postId: Long): CompletableFuture<List<Comment>> = db.execute(executor) {
        (select(CommentModel::class) where (CommentModel::postId eq (postId)))
                .get()
                .map(CommentModel::toDomain)
    }

    override fun create(comment: Comment): CompletableFuture<Comment> = db.execute(executor) {
        insert(comment.toModel()).toDomain()
    }

    override fun update(comment: Comment): CompletableFuture<Optional<Comment>> = db.execute(executor) {
        db.update()
                .set(CommentEntity.CONTENT, comment.text)
                .where(CommentModel::id eq (comment.id))
                .get()
                .value()
                .convertUpdateCodeToOptional()
                .map { comment }
    }

    override fun delete(id: Long): CompletableFuture<Optional<*>> = db.execute(executor) {
        delete().where(CommentModel::id eq (id)).get()
                .value()
                .convertUpdateCodeToOptional()
    }
}