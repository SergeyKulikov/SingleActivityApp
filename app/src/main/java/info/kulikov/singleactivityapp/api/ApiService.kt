package info.kulikov.singleactivityapp.api

import com.google.gson.JsonObject
import io.reactivex.Observable
import org.json.JSONObject
import retrofit2.http.*

interface ApiService {
    @GET("/api/users")
    fun getUserRepos(): Observable<JsonObject>
}