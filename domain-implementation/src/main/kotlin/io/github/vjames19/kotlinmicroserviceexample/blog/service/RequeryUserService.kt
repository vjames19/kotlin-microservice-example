package io.github.vjames19.kotlinmicroserviceexample.blog.service

import io.github.vjames19.kotlinmicroserviceexample.blog.di.DbExecutorService
import io.github.vjames19.kotlinmicroserviceexample.blog.domain.User
import io.github.vjames19.kotlinmicroserviceexample.blog.model.UserEntity
import io.github.vjames19.kotlinmicroserviceexample.blog.model.toDomain
import io.github.vjames19.kotlinmicroserviceexample.blog.util.execute
import io.github.vjames19.kotlinmicroserviceexample.blog.util.firstOption
import io.github.vjames19.kotlinmicroserviceexample.blog.model.User as UserDao
import io.requery.Persistable
import io.requery.kotlin.eq
import io.requery.sql.KotlinEntityDataStore
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import javax.inject.Inject

/**
 * Created by victor.reventos on 6/8/17.
 */
class RequeryUserService @Inject constructor(val db: KotlinEntityDataStore<Persistable>,
                                             @DbExecutorService val executor: ExecutorService) : UserService {

    override fun getUser(id: Long): CompletableFuture<Optional<User>> {
        return db.execute(executor) {
            (select(UserDao::class) where (UserDao::id eq (id)))
                    .get()
                    .firstOption()
                    .map { it.toDomain() }
        }
    }

    override fun create(user: User): CompletableFuture<User> {
        return db.execute(executor) {
            insert(UserEntity().apply {
                username = user.username
            }).toDomain()
        }
    }
}