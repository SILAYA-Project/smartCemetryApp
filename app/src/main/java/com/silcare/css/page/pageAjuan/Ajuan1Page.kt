package com.silcare.css.page.pageAjuan

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.silcare.css.Component.textFieldCustom.TextFieldCustom
import com.silcare.css.Component.textFieldCustom.TextFieldDropDown
import com.silcare.css.api.AdminNotifikasi
import com.silcare.css.api.AjuanDataStore

@SuppressLint("ContextCastToActivity")
@Composable
fun Ajuan1Page(navController: NavController,data : AdminNotifikasi) {
    var di_wakilkan_oleh by remember { mutableStateOf(data.di_wakilkan_oleh) }
    var nomor_telpon by remember { mutableStateOf(data.nomor_telpon) }
    var alamat by remember { mutableStateOf(data.alamatw) }
    var nomor_nik by remember { mutableStateOf(data.nomor_nik) }
    var rt by remember { mutableStateOf(data.rtw) }
    var rw by remember { mutableStateOf(data.rww) }
    var kelurahan by remember { mutableStateOf(data.kelurahanw) }
    var kecamatan by remember { mutableStateOf(data.kecamatanw) }
    var hubungan by remember { mutableStateOf(data.hubungan) }
    val activity = (LocalContext.current as? Activity)

    val kecamatanList = listOf(
        "Batam Kota", "Batu Aji", "Batu Ampar", "Belakang Padang", "Bengkong",
        "Bulang", "Galang", "Lubuk Baja", "Nongsa", "Sagulung",
        "Sei Beduk", "Sekupang"
    )

    val kelurahanMap = mapOf(
        "Batam Kota" to listOf("Baloi Permai", "Belian", "Sukajadi", "Sungai Panas", "Taman Baloi", "Teluk Tering"),
        "Batu Aji" to listOf("Bukit Tempayan", "Buliang", "Kibing", "Tanjung Uncang"),
        "Batu Ampar" to listOf("Batu Merah", "Kampung Seraya", "Sungai Jodoh", "Tanjung Sengkuang"),
        "Belakang Padang" to listOf("Kasu", "Pecong", "Pemping", "Pulau Terong", "Sekanak Raya", "Tanjung Sari"),
        "Bengkong" to listOf("Bengkong Harapan", "Bengkong Indah", "Bengkong Laut", "Sadai", "Tanjung Buntung"),
        "Bulang" to listOf("Batu Legong", "Bulang Lintang", "Pantai Gelam", "Pulau Buluh", "Pulau Setokok", "Temoyong"),
        "Galang" to listOf("Air Raja", "Galang Baru", "Karas", "Pulau Abang", "Rempang Cate", "Sembulang", "Sijantung", "Subang Mas"),
        "Lubuk Baja" to listOf("Baloi Indah", "Batu Selicin", "Kampung Pelita", "Lubuk Baja Kota", "Tanjung Uma"),
        "Nongsa" to listOf("Batu Besar", "Kabil", "Ngenang", "Sambau"),
        "Sagulung" to listOf("Sagulung Kota", "Sungai Binti", "Sungai Langkai", "Sungai Lekop", "Sungai Pelunggut", "Tembesi"),
        "Sei Beduk" to listOf("Duriangkang", "Mangsang", "Muka Kuning", "Tanjung Piayu"),
        "Sekupang" to listOf("Tiban Asri", "Tanjung Riau", "Tiban Lama", "Tiban Baru", "Tiban Indah", "Patam Lestari", "Sungai Harapan", "Tanjung Pinggir")
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            Spacer(modifier = Modifier.padding(20.dp))
            Text(
                text = "Yang Bertanggung Jawab",
                color = Color(0xFF38008B),
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.padding(20.dp))
            TextFieldCustom(
                value = di_wakilkan_oleh,
                onValueChange = {
                    di_wakilkan_oleh = it
                },
                title = "Nama",
                placeholder = "Masukan Nama Anda",
                trailingIcon = {},
                isError = false
            )
            Spacer(modifier = Modifier.padding(10.dp))
            TextFieldCustom(
                value = nomor_telpon,
                onValueChange = {
                    nomor_telpon = it
                },
                title = "Nomor Hp",
                placeholder = "0813-----------------",
                trailingIcon = {},
                isError = false
            )
            Spacer(modifier = Modifier.padding(10.dp))
            TextFieldCustom(
                value = nomor_nik,
                onValueChange = {
                    nomor_nik = it
                },
                title = "Nomor Idetitas / KTP",
                placeholder = "092739------",
                trailingIcon = {},
                isError = false
            )
            Spacer(modifier = Modifier.padding(10.dp))
            TextFieldCustom(
                value = alamat,
                onValueChange = {
                    alamat = it
                },
                title = "Alamat",
                placeholder = "Alamat Anda",
                trailingIcon = {},
                isError = false
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Row(
                content = {
                    TextFieldCustom(
                        modifier = Modifier.width(170.dp),
                        value = rt,
                        onValueChange = {
                            rt = it
                        },
                        title = "RT",
                        placeholder = "00",
                        trailingIcon = {},
                        isError = false
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    TextFieldCustom(
                        modifier = Modifier.width(170.dp),
                        value = rw,
                        onValueChange = {
                            rw = it
                        },
                        title = "RW",
                        placeholder = "00",
                        trailingIcon = {},
                        isError = false
                    )
                }
            )

            Spacer(modifier = Modifier.size(20.dp))
            TextFieldDropDown(
                title = "Kecamatan",
                modifier = Modifier.fillMaxWidth(),
                value = kecamatan,
                onValueChange = {
                    kecamatan = it
                    kelurahan = ""
                },
                pilihan = kecamatanList
            )
            Spacer(modifier = Modifier.size(20.dp))
            TextFieldDropDown(
                title = "Kelurahan",
                modifier = Modifier.fillMaxWidth(),
                value = kelurahan,
                onValueChange = { kelurahan = it },
                pilihan = kelurahanMap[kecamatan] ?: emptyList()
            )
            Spacer(modifier = Modifier.padding(10.dp))
            TextFieldCustom(
                value = hubungan,
                onValueChange = {
                    hubungan = it
                },
                title = "Hubungan",
                placeholder = "hubungan anda Dengan alm",
                trailingIcon = {},
                isError = false
            )
            Spacer(modifier = Modifier.padding(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                content = {
                    Box(
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = Color(0xFF38008B),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(start = 20.dp, end = 20.dp, top = 15.dp, bottom = 15.dp)
                            .clickable {
                                activity?.finish()
                            },
                        content = {
                            Text(text = "Batal", color = Color(0xFF38008B), fontSize = 13.sp)
                        }
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF38008B))
                            .padding(start = 30.dp, end = 30.dp, top = 15.dp, bottom = 15.dp)
                            .clickable {
                                val newData = AdminNotifikasi(
                                    di_wakilkan_oleh = di_wakilkan_oleh,
                                    nomor_telpon = nomor_telpon,
                                    alamatw = alamat,
                                    nomor_nik = nomor_nik,
                                    rtw = rt,
                                    rww = rw,
                                    kelurahanw = kelurahan,
                                    kecamatanw = kecamatan,
                                    hubungan = hubungan
                                )

                                AjuanDataStore.addData(newData)
                                navController.navigate("ajuan2")
                            },
                        content = {
                            Text(text = "Selanjutnya", color = Color.White, fontSize = 13.sp)
                        }
                    )
                }
            )
        }
    )
}

@Preview
@Composable
private fun Ajuan1PagePrev() {
    Ajuan1Page(rememberNavController(), data = AdminNotifikasi())
}