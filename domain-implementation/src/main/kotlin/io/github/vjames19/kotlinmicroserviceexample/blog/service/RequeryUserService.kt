package io.github.vjames19.kotlinmicroserviceexample.blog.service

import io.github.vjames19.futures.jdk8.recover
import io.github.vjames19.kotlinmicroserviceexample.blog.domain.User
import io.github.vjames19.kotlinmicroserviceexample.blog.model.UserModel
import io.github.vjames19.kotlinmicroserviceexample.blog.model.toDomain
import io.github.vjames19.kotlinmicroserviceexample.blog.model.toModel
import io.github.vjames19.kotlinmicroserviceexample.blog.util.KotlinCompletableEntityStore
import io.github.vjames19.kotlinmicroserviceexample.blog.util.firstOption
import io.github.vjames19.kotlinmicroserviceexample.blog.util.handlePsqlException
import io.github.vjames19.kotlinmicroserviceexample.blog.util.isIntegrityConstrainViolation
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
class RequeryUserService @Inject constructor(val db: KotlinCompletableEntityStore<Persistable>) : UserService {

    override fun getUser(id: Long): CompletableFuture<Optional<User>> = db.execute {
        (select(UserModel::class) where (UserModel::id eq (id)))
                .get()
                .firstOption()
                .map(UserModel::toDomain)
    }

    override fun create(user: User): CompletableFuture<User> = db.execute {
        insert(user.toModel())
                .toDomain()
    }.recover { handleError(user, it) }

    private fun <A> handleError(user: User, throwable: Throwable): A = handlePsqlException(throwable) {
        if (it.isIntegrityConstrainViolation()) throw UsernameAlreadyExistsUserServiceError(user.username)
        throw throwable
    }
}