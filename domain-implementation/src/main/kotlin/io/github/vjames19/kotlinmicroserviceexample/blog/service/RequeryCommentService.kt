package io.github.vjames19.kotlinmicroserviceexample.blog.service

import io.github.vjames19.kotlinmicroserviceexample.blog.domain.Comment
import io.github.vjames19.kotlinmicroserviceexample.blog.model.CommentModel
import io.github.vjames19.kotlinmicroserviceexample.blog.model.CommentModelEntity
import io.github.vjames19.kotlinmicroserviceexample.blog.model.toDomain
import io.github.vjames19.kotlinmicroserviceexample.blog.model.toModel
import io.github.vjames19.kotlinmicroserviceexample.blog.util.KotlinCompletableEntityStore
import io.github.vjames19.kotlinmicroserviceexample.blog.util.toOptional
import io.requery.Persistable
import io.requery.kotlin.eq
import java.util.*
import java.util.concurrent.CompletableFuture
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by victor.reventos on 6/8/17.
 */
@Singleton
class RequeryCommentService @Inject constructor(val db: KotlinCompletableEntityStore<Persistable>) : CommentService {

    override fun getCommentsForPost(postId: Long): CompletableFuture<List<Comment>> = db.execute {
        (select(CommentModel::class) where (CommentModel::postId eq (postId)))
                .get()
                .map(CommentModel::toDomain)
    }

    override fun create(comment: Comment): CompletableFuture<Comment> = db.execute {
        insert(comment.toModel()).toDomain()
    }

    override fun update(comment: Comment): CompletableFuture<Optional<Comment>> = db.execute {
        update()
                .set(CommentModelEntity.CONTENT, comment.text)
                .where(CommentModel::id eq (comment.id))
                .get()
                .toOptional()
                .map { comment }
    }

    override fun delete(id: Long): CompletableFuture<Optional<*>> = db.execute {
        delete().where(CommentModel::id eq (id)).get()
                .toOptional()
    }
}