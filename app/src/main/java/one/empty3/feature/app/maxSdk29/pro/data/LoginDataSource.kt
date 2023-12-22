package one.empty3.feature.app.maxSdk29.pro.data

import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import one.empty3.feature.app.maxSdk29.pro.data.model.LoggedInUser
import one.empty3.feature.app.maxSdk29.pro.data.model.URLContentTask
import java.io.IOException


/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {
    fun login(username: String, password: String): Result<LoggedInUser> = runBlocking  {
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        try {
            val url: String = "https://empty3.app/agenda/src/android/login.php?username=$username&password=$password"
            val sb = StringBuilder("")
            val urlContentTask = URLContentTask()
            val job = launch { // launch a new coroutine and keep a reference to its Job
                val contentFromURL = urlContentTask.loadUrlAsString(url)
                urlContentTask.ConvertToInt(contentFromURL)
                println("Get HTTPS")
            }
            println("Job launched")
            job.join() // wait until child coroutine completes
            println("Job Done")
            Result.Success(LoggedInUser.getCurrentUser())
        } catch (ex: Throwable) {
            System.err.println("Error logging in" + ex.message)
            Result.Error(IOException("Error logging in" + ex.message))
        }
        Result.Error(NullPointerException("Return null"))
    }

    fun logout() {
        // TODO: revoke authentication
    }
}