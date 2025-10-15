package com.silcare.css.Component.cardComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun CardBerita(
    title: String,
    desc: String,
    imgUrl: String,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFEF7FF)),
        content = {
            Column(
                modifier = Modifier.padding(20.dp),
                content = {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        model = imgUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = title,
                        color = Color(0xFF38008B),
                        modifier = Modifier.padding(top = 10.dp),
                        fontSize = 16.sp
                    )
                    Text(
                        text = desc,
                        color = Color(0xFF443160),
                        modifier = Modifier.padding(top = 10.dp),
                        fontSize = 13.sp
                    )
                }
            )
            Text(
                text = "Selengkapnya",
                color = Color(0xb30059ff),
                modifier = Modifier
                    .padding(bottom = 10.dp, end = 10.dp)
                    .align(Alignment.BottomEnd)
                    .clickable { onClick() }
            )
        }
    )
}

@Preview
@Composable
private fun CardBeritaPrev() {
    CardBerita(
        title = "Tanah Longsor di Area Pemakaman, Puluhan...",
        desc = "Kubruan – Hujan deras yang mengguyur wilayah Desa Kubruan selama dua hari berturut-turut menyebabkan terjadinya tanah longsor di area pemakaman umum pada Selasa pagi (23/7). Akibat kejadian ini, puluhan makam dilaporkan tertimbun material longsoran, dan akses jalan menuju pemakaman pun tertutup total.",
        imgUrl = ""
    )
}