package com.upc.stockvision.infrastructure.extensions

enum class LoadingTYPE {
    START, END
}

sealed class LCEState< out T> {
    companion object {
        fun loading(isStart: Boolean = true): LCEState<LoadingTYPE> {
            return Loading(state = if (isStart) LoadingTYPE.START else LoadingTYPE.END)
        }

        fun <T> content(data: T): Content<T> {
            return Content(content = data)
        }

        fun <T> error(data: T): Error<T> {
            return Error(error = data)
        }
    }

    class Loading<LoadingTYPE>(val state: LoadingTYPE) : LCEState<LoadingTYPE>()
    class Content<T>(val content: T) : LCEState<T>()
    class Error<T>(val error: T) : LCEState<T>()
}
