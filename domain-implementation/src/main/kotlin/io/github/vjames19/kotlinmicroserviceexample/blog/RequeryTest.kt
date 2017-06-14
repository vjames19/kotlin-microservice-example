package io.github.vjames19.kotlinmicroserviceexample.blog

import io.github.vjames19.kotlinmicroserviceexample.blog.domain.Post
import io.github.vjames19.kotlinmicroserviceexample.blog.domain.User
import io.github.vjames19.kotlinmicroserviceexample.blog.model.Models
import io.github.vjames19.kotlinmicroserviceexample.blog.service.RequeryPostService
import io.github.vjames19.kotlinmicroserviceexample.blog.service.RequeryUserService
import io.github.vjames19.kotlinmicroserviceexample.blog.util.KotlinCompletableEntityStore
import io.requery.Persistable
import io.requery.sql.KotlinConfiguration
import io.requery.sql.KotlinEntityDataStore
import org.postgresql.ds.PGSimpleDataSource
import java.util.concurrent.Executors

/**
 * Created by victor.reventos on 6/9/17.
 */
fun main(args: Array<String>) {
    val dataSource = PGSimpleDataSource().apply {
        user  = "postgres"
        password = "postgres"
        databaseName = "blog"
    }

    val configuration = KotlinConfiguration(
            dataSource = dataSource,
            model = Models.DEFAULT,
            statementCacheSize = 100,
            useDefaultLogging = true)
    val executor = Executors.newCachedThreadPool()
    val instance = KotlinCompletableEntityStore(KotlinEntityDataStore<Persistable>(configuration), executor)
    val service = RequeryUserService(instance)

    println(service.getUser(1).get())

    println(service.create(User(id = 0, username = "user${System.currentTimeMillis()}")).get())
    println(service.create(User(id = 0, username = "user${System.currentTimeMillis()}")).get())

    try {
        println(service.create(User(id = 0, username = "vjames19")).get())
    } catch (e: Exception) {
        println("Error updating existing")
        e.printStackTrace()
    }

    val postService = RequeryPostService(instance)

    println(postService.update(Post(id = Long.MAX_VALUE, userId = 1, content = "some invalid content")).get())

}