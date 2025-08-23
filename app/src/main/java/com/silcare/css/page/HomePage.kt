package com.silcare.css.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.silcare.css.Component.cardComponent.CardMakam
import com.silcare.css.Component.top_app_bar.TopSearchBarFillter

@Composable
fun HomePage() {
    Box(
        modifier = Modifier.fillMaxSize().background(Color.White),
        content = {
            Column (
                modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                content = {
                    TopSearchBarFillter(
                        onClick = {},
                        onValueChange = {},
                        value = "",
                    )
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        content = {
                            Text(modifier = Modifier.padding(start = 5.dp), text = "Nama Makam ・ ${20}")
                            Text(modifier = Modifier.padding(end = 30.dp), text = "Lokasi")
                        }
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    LazyColumn {
                        items(count = 20){
                            CardMakam(
                                namaMakam = "Anggrek",
                                terisi = 10,
                                max = 100
                            )
                            Spacer(modifier = Modifier.padding(5.dp))
                        }
                    }
                }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun HomePagePrev() {
    HomePage()
}