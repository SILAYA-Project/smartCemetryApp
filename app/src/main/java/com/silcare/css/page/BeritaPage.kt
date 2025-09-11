package com.silcare.css.page

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.silcare.css.Component.cardComponent.CardBerita

@Composable
fun BeritaPage() {
    LazyColumn (
        content = {
            items (count = 10){
                CardBerita(
                    title = "Tanah Longsor di Area Pemakaman, Puluhan...",
                    desc = "Kubruan – Hujan deras yang mengguyur wilayah Desa Kubruan selama dua hari berturut-turut menyebabkan terjadinya tanah longsor di area pemakaman umum pada Selasa pagi (23/7). Akibat kejadian ini, puluhan makam dilaporkan tertimbun material longsoran, dan akses jalan menuju pemakaman pun tertutup total.",
                    imgUrl = "https://picsum.photos/${it}"
                )
            }
        }
    )
}

@Preview
@Composable
private fun BeritaPagePrev() {
    BeritaPage()
}