package io.github.vjames19.kotlinmicroserviceexample.blog.api.jooby

import io.github.vjames19.kotlinmicroserviceexample.blog.domain.User
import io.restassured.RestAssured.get
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeGreaterOrEqualTo
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jooby.Status

/**
 * Created by victor.reventos on 6/13/17.
 */
object UsersFeatureTest : Spek({

    jooby(App()) {
        describe("Getting a user") {
            given("that the user doesn't exist") {
                it("should return 404") {
                    get("/users/${Long.MIN_VALUE}")
                            .then()
                            .statusCode(Status.NOT_FOUND)
                }
            }

            given("a user that exists") {
                it("should return the user") {
                    val user = get("/users/1")
                            .then()
                            .statusCode(Status.OK)
                            .As<User>()

                    user.id shouldBeGreaterOrEqualTo 0L
                    user.username.isEmpty() shouldBe false
                }
            }
        }
    }
})