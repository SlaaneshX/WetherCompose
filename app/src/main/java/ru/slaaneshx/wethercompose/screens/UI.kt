package ru.slaaneshx.wethercompose.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.slaaneshx.wethercompose.ui.theme.BlueLight

@Preview(showBackground = true)
@Composable
fun ListItem() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp),
        backgroundColor = BlueLight,
        elevation = 0.dp,
        shape = RoundedCornerShape(5.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.padding(start = 8.dp, top = 5.dp, bottom = 5.dp)) {
                Text(text = "12:00")
                Text(
                    text = "Sunny",
                    color = Color.White
                )
            }
            Text(text = "+25C", color = Color.White, style = TextStyle(fontSize = 25.sp))
            AsyncImage(
                model = "https://cdn.weatherapi.com/weather/64x64/day/113.png",
                contentDescription = "image2",
                modifier = Modifier.size(35.dp)
            )
        }
    }

}