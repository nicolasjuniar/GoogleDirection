package juniar.porkat.Utils

import com.juniar.googledirection.DirectionResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Nicolas Juniar on 12/02/2018.
 */
interface NetworkApi {
    @GET("api/directions/json?")
    fun getDirection(@Query("units") units:String="metric",
                     @Query("origin") origin:String,
                     @Query("destination") destination:String,
                     @Query("mode") mode:String="driving"):Observable<DirectionResponse>
}