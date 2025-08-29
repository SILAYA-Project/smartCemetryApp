package com.silcare.css.page

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.silcare.css.Component.cardComponent.CardNoMayat
import com.silcare.css.Component.top_app_bar.TopSearchBarFillter
import com.silcare.css.R
import com.silcare.css.api.noBlok

@Composable
fun NoMakamPage(onBack : () -> Unit) {
    Column (
        modifier = Modifier.background(Color.White).padding(start = 20.dp, end = 20.dp),
        content = {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Icon(
                        modifier = Modifier.clickable { onBack() }.padding(end = 15.dp).size(20.dp),
                        painter = painterResource(R.drawable.iconback),
                        contentDescription = null,
                        tint = Color(0xFF38008B)
                    )
                    TopSearchBarFillter(onValueChange = {}, onClick = {}, value = "")
                }
            )
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,
                content = {
                    Text(modifier = Modifier.padding(start = 5.dp), text = "Nomor Blok ・ ${20}")
                    Text(modifier = Modifier.padding(end = 30.dp), text = "Lokasi")
                }
            )
            Spacer(modifier = Modifier.padding(5.dp))
            LazyColumn {
                items(count = 20){
                    CardNoMayat(data = noBlok(), onClik = {})
                    Spacer(modifier = Modifier.padding(5.dp))
                }

            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun NoMakamPagePrev() {
    NoMakamPage(onBack = {})
}