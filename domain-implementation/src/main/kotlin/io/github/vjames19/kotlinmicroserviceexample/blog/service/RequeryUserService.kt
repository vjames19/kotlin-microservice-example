package io.github.vjames19.kotlinmicroserviceexample.blog.service

import io.github.vjames19.kotlinmicroserviceexample.blog.di.DbExecutorService
import io.github.vjames19.kotlinmicroserviceexample.blog.domain.User
import io.github.vjames19.kotlinmicroserviceexample.blog.model.UserModel
import io.github.vjames19.kotlinmicroserviceexample.blog.model.toDomain
import io.github.vjames19.kotlinmicroserviceexample.blog.model.toModel
import io.github.vjames19.kotlinmicroserviceexample.blog.util.execute
import io.github.vjames19.kotlinmicroserviceexample.blog.util.firstOption
import io.requery.Persistable
import io.requery.kotlin.eq
import io.requery.sql.KotlinEntityDataStore
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by victor.reventos on 6/8/17.
 */
@Singleton
class RequeryUserService @Inject constructor(val db: KotlinEntityDataStore<Persistable>,
                                             @DbExecutorService val executor: ExecutorService) : UserService {

    override fun getUser(id: Long): CompletableFuture<Optional<User>> = db.execute(executor) {
        (select(UserModel::class) where (UserModel::id eq (id)))
                .get()
                .firstOption()
                .map(UserModel::toDomain)
    }

    override fun create(user: User): CompletableFuture<User> = db.execute(executor) {
        insert(user.toModel())
                .toDomain()
    }
}