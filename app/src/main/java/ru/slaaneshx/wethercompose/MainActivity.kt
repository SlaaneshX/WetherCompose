package ru.slaaneshx.wethercompose

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import ru.slaaneshx.wethercompose.screens.MainCard
import ru.slaaneshx.wethercompose.screens.TabLayout
import ru.slaaneshx.wethercompose.ui.theme.WetherComposeTheme

const val API_KEY = "0bdee3c77c0f428bb10103155221406"
const val WEATHER_URL = "https://api.weatherapi.com/v1/current.json"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WetherComposeTheme() {
                Image(
                    painter = painterResource(id = R.drawable.sky_drawable),
                    contentDescription = "123",
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.9f),
                    contentScale = ContentScale.FillBounds
                )
                Column {
                    MainCard()
                    TabLayout()
                }
            }
        }
    }
}

private fun getResponse(city: String = "London", context: Context, state: MutableState<String>) {
    val url = "$WEATHER_URL?key=$API_KEY&q=$city&aqi=no"
    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(
        Request.Method.GET, url,
        { response ->
            val responsJSON = JSONObject(response)
            var temp = responsJSON.getJSONObject("current").getString("temp_c")
            state.value = temp
        },
        {
            Log.e("WeatherErrorLog", "Volley error $it")
        }
    )
    queue.add(stringRequest)
}