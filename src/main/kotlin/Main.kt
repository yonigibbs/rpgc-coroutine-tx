import io.reactiverse.pgclient.PgClient
import io.reactiverse.pgclient.PgConnection
import io.reactiverse.pgclient.PgPoolOptions
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import kotlinx.coroutines.experimental.runBlocking
import kotlin.coroutines.experimental.suspendCoroutine
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val connectionPool = PgClient.pool(
            PgPoolOptions.fromUri("postgresql://localhost:5432/postgres")
                    .setUser("postgres")
                    .setPassword("postgres")
                    .setMaxSize(10))

    runBlocking {
        val connection = awaitResult<PgConnection> { connectionPool.getConnection(it) }
        try {
            val transaction = connection.begin()    // This line errors
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            connection.close()
        }
    }

    exitProcess(0)
}

private suspend inline fun <T> awaitResult(crossinline block: (Handler<AsyncResult<T>>) -> Unit): T {
    return suspendCoroutine { cont ->
        try {
            block(Handler { asyncResult ->
                if (asyncResult.succeeded()) cont.resume(asyncResult.result())
                else cont.resumeWithException(asyncResult.cause())
            })
        } catch (e: Exception) {
            cont.resumeWithException(e)
        }
    }
}