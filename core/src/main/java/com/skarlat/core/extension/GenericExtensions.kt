package com.skarlat.core.extension

inline fun <reified T> Any.`as`(action: T.() -> Unit) {
    if (this is T) action.invoke(this)
}