package one.empty3.feature.app.maxSdk29.pro.data

import one.empty3.feature.app.maxSdk29.pro.data.model.LoggedInUser
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.util.UUID

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<LoggedInUser> {
            val url: URL =
                URL("https://empty3.app/agenda/src/android/login.pho?username=" + username + "&password=" + password)
            val sb = StringBuilder("")

            try {
                val `in`: BufferedReader
                `in` = BufferedReader(
                    InputStreamReader(
                        url.openStream()
                    )
                )
                var inputLine: String?
                while (`in`.readLine().also { inputLine = it } != null) sb.append(inputLine)
                `in`.close()
                val ret = sb.toString()

                if (ret.toInt().equals(1)) {
                    val fakeUser = LoggedInUser(UUID.randomUUID().toString(), "Test User");
                    return Result.Success(fakeUser)
                }
            } catch (ex: Throwable) {
                return Result.Error(IOException("Error logging in" + ex.message))
            }
            return Result.Error(IOException("Error logging in"))
        }

    fun logout() {
        // TODO: revoke authentication
    }
}