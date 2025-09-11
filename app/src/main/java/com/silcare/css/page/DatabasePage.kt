package com.silcare.css.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.silcare.css.Component.cardComponent.CardDataMayat
import com.silcare.css.Component.top_app_bar.TopSearchBarFillter
import com.silcare.css.api.MakamViewModel
import com.silcare.css.api.Mayat

@Composable
fun DatabasePage(viewModel: MakamViewModel = viewModel()) {
    val mayatList by viewModel.mayatList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchDataMayat()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        content = {
            Column(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                content = {
                    TopSearchBarFillter(
                        onClick = {},
                        onValueChange = {},
                        value = "",
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    LazyColumn {
                        items(mayatList) {
                            CardDataMayat(
                                data = Mayat(
                                    nama_mayat = it.nama_mayat,
                                    usia = it.usia,
                                    wafat = it.wafat,
                                    jenis_kelamin = it.jenis_kelamin,
                                    nomor_kk = it.nomor_kk,
                                    sebab = it.sebab,
                                    nomor_nik = it.nomor_nik,
                                    blok_makam = it.blok_makam,
                                    alamat = it.alamat,
                                    meninggal_di = it.meninggal_di,
                                    tempat_dan_tanggal_lahir = it.tempat_dan_tanggal_lahir,
                                    id_makam = it.id_makam,
                                    di_wakilkan_oleh = it.di_wakilkan_oleh,
                                    nomor_telpon = it.nomor_telpon,
                                    email = it.email,
                                    tanggal_di_makamkan = it.tanggal_di_makamkan,
                                    skm = it.skm,
                                    id_mayat = it.id_mayat
                                )
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
private fun DatabasePagePrev() {
    DatabasePage()
}