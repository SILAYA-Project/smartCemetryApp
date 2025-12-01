package com.silcare.css.page

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.silcare.css.Component.cardComponent.CardBerita
import com.silcare.css.api.MakamViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BeritaPage(viewModel: MakamViewModel = viewModel()) {
    val beritaList by viewModel.beritaList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchDataBerita()
    }

    LazyColumn {
        items(beritaList) { berita ->
            CardBerita(
                title = berita.title,
                desc = berita.desc,
                imgUrl = berita.img_url
            )
            println("berita.imgUrl  = ${berita.img_url}")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun BeritaPagePrev() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        BeritaPage()
    }
}