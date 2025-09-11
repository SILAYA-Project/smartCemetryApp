package com.silcare.css.page

import android.widget.Space
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun PageBeritaDitail(
    title: String = "Title berita",
    desc: String = "",
    imgUrl: String = "https://picsum.photos/200",
    date: String = "2023-07-23"
) {
    Column (
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        content = {
//            AsyncImage(
//                model = imgUrl,
//                contentDescription = null,
//                modifier = Modifier.fillMaxWidth()
//            )
            Text(
                text = title,
                color = Color(0xFF38008B),
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.size(5.dp))
            Text(text = date, color = Color(0xFFB8B8B8))
            Spacer(modifier = Modifier.size(20.dp))
            Text(text = desc)
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PageBeritaDitailPrev() {
    PageBeritaDitail(
        desc = "Kubruan – Hujan deras yang mengguyur wilayah Desa Kubruan selama dua hari berturut-turut menyebabkan terjadinya tanah longsor di area pemakaman umum pada Selasa pagi (23/7). Akibat kejadian ini, puluhan makam dilaporkan tertimbun material longsoran, dan akses jalan menuju pemakaman pun tertutup total."
    )
}