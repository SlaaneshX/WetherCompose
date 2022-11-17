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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import ru.slaaneshx.wethercompose.data.WeatherModel
import ru.slaaneshx.wethercompose.screens.MainCard
import ru.slaaneshx.wethercompose.screens.TabLayout
import ru.slaaneshx.wethercompose.ui.theme.WetherComposeTheme

const val API_KEY = "0bdee3c77c0f428bb10103155221406"
const val WEATHER_URL = "https://api.weatherapi.com/v1/forecast.json"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {
            WetherComposeTheme {
                val list = remember {
                    mutableStateOf(listOf<WeatherModel>())
                }
                val currentDay = remember {
                    mutableStateOf(
                        WeatherModel(
                            "", "", "", "", "", "", "", ""
                        )
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.sky_drawable),
                    contentDescription = "123",
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.9f),
                    contentScale = ContentScale.FillBounds
                )
                getData("Nizhny Novgorod", this, list, currentDay)
                Column {
                    MainCard(currentDay)
                    TabLayout(list, currentDay)
                }
            }
        }
    }
}

private fun getData(
    city: String = "Nizhny Novgorod",
    context: Context,
    state: MutableState<List<WeatherModel>>,
    currentDay: MutableState<WeatherModel>
) {
    val url = "$WEATHER_URL?key=$API_KEY&q=$city&days=3&aqi=no&alerts=no"

    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(Request.Method.GET, url, { response ->
        val list = getWeatherToDay(response)
        state.value = list
        currentDay.value = list[0]

    }, {
        Log.d("WeatherErrorLog", "Volley error $it")
    })
    queue.add(stringRequest)
}

private fun getWeatherToDay(response: String): List<WeatherModel> {

    if (response.isEmpty()) return emptyList()
    val list = ArrayList<WeatherModel>()

    val mainObject = JSONObject(response)
    val city = mainObject.getJSONObject("location").getString("name")
    val days = mainObject.getJSONObject("forecast").getJSONArray("forecastday")

    for (i in 0 until days.length()) {
        val item = days.get(i) as JSONObject
        list.add(
            WeatherModel(
                city,
                item.getString("date"),
                "",
                item.getJSONObject("day").getJSONObject("condition").getString("text"),
                item.getJSONObject("day").getJSONObject("condition").getString("icon"),
                item.getJSONObject("day").getString("maxtemp_c"),
                item.getJSONObject("day").getString("mintemp_c"),
                item.getJSONArray("hour").toString()
            )
        )
    }
    list[0] = list[0].copy(
        time = mainObject.getJSONObject("current").getString("last_updated"),
        currentTemp = mainObject.getJSONObject("current").getString("temp_c"),
    )
    return list
}