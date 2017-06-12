package io.github.vjames19.kotlinmicroserviceexample.blog.api.jooby.di

import com.google.inject.AbstractModule
import com.google.inject.Binder
import com.typesafe.config.Config
import org.jooby.Env
import org.jooby.Jooby

/**
 * Created by victor.reventos on 6/12/17.
 */
abstract class Module : Jooby.Module, AbstractModule() {
    override fun configure(env: Env, conf: Config, binder: Binder) {
        binder.install(this)
    }

    override fun configure() {
    }
}