package io.github.vjames19.kotlinmicroserviceexample.blog.api.jooby.di

import com.google.inject.Binder
import com.google.inject.Injector
import com.google.inject.binder.AnnotatedBindingBuilder
import com.google.inject.binder.ScopedBindingBuilder

/**
 * Created by victor.reventos on 6/13/17.
 */
inline fun <reified T> Module.bind(): AnnotatedBindingBuilder<T> {
    return binder().bind<T>()
}

inline fun <reified T> Binder.bind() = bind(T::class.java)

inline fun <reified T>AnnotatedBindingBuilder<in T>.to(): ScopedBindingBuilder = to(T::class.java)

inline fun <reified T> Injector.getInstance(): T = getInstance(T::class.java)