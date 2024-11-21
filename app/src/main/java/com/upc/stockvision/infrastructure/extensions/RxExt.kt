package com.upc.stockvision.infrastructure.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import androidx.room.RoomDatabase

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.Exception

fun <T> Flowable<T>.applySchedulers(): Flowable<T> = this
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.applySchedulers(): Observable<T> = this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.applySchedulersThread(): Single<T> = this
    .subscribeOn(Schedulers.io())

fun <T> Single<T>.applySchedulers(): Single<T> = this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

/**
 * extrae el mensaje de error de la request : ApiWebErrorDTO || Throwable || Default String
 */
fun <T> Single<T>.subscribeApp(
        onError: ((message : String) -> Unit)? = null,
        onSuccess: (T) -> Unit
): Disposable = subscribe(onSuccess) {
    onError?.invoke(
        it.castError(exceptionCommands) ?: it.message ?: "empty error"
    )
}

fun <T> Maybe<T>.applySchedulers(): Maybe<T> = this
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())

@SuppressLint("CheckResult")
class doAsynTask<T>( val job : () -> T, val response : (response : T) -> Unit, val error : ((throwable : Throwable) -> Unit)? = null)
{
    init {
        Single.fromCallable<T> {
            job()
        }.applySchedulers().subscribe({
            response(it)
        },{
            it.printStackTrace()
            error?.invoke(it)
        })
    }
}

@SuppressLint("CheckResult")
class doAsynTaskOptional<T>( val job : () -> T?, val response : (response : T?) -> Unit)
{
    init {
        Single.fromCallable<T> {
            job()
        }.applySchedulers().subscribe({
            response(it)
        },{
            it.printStackTrace()
        })
    }
}

@SuppressLint("CheckResult")
class doHandler<T>( val job : () -> T, val delayMilis : Long)
{
    init {
        Handler().postDelayed({ job() }, delayMilis)
    }
}

@SuppressLint("CheckResult")
class doTaskInFirstThread(val action : () -> Unit)
{
    init {
        Single.fromCallable {
        }.applySchedulers().subscribe({
            action()
        },{
            it.printStackTrace()
        })
    }
}

/**
 * importante tener en cuenta q no se estàn manejando las excepciones desde aquì..
 */
@SuppressLint("CheckResult")
class doAsync<T>( val job : () -> T)
{
    init {
        try
        {
            Single.fromCallable<T> {
                job()
            }.applySchedulersThread().subscribe()
        }catch (e : Exception){
            e.printStackTrace()
        }
    }
}
@SuppressLint("CheckResult")
fun <T> RoomDatabase.runTransactionInBackground(job : () -> T, response: (response: T) -> Unit, error: (throwable : Throwable) -> Unit) {
    Single.fromCallable<T> {
        this.runInTransaction(job)
    }.applySchedulers().subscribe({
        response(it)
    }, {
        error(it)
    })
}
