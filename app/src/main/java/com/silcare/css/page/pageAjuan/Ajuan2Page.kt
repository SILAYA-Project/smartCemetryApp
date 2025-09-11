package com.silcare.css.page.pageAjuan

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.silcare.css.Component.textFieldCustom.DatePickerBox
import com.silcare.css.Component.textFieldCustom.TextFieldCustom
import com.silcare.css.Component.textFieldCustom.TextFieldDropDown
import com.silcare.css.api.AdminNotifikasi
import com.silcare.css.api.AjuanDataStore
import java.time.LocalDate
import java.util.Date

@Composable
fun Ajuan2Page(navController: NavController, data: AdminNotifikasi) {
    var usia by remember { mutableStateOf(data.usia) }
    var alamat by remember { mutableStateOf(data.alamatw) }
    var nama_mayat by remember { mutableStateOf(data.nama_mayat) }
    var meninggal_di by remember { mutableStateOf(data.meninggal_di) }
    var blok_makam by remember { mutableStateOf(data.blok_makam) }
    var id_makam by remember { mutableStateOf(data.id_makam) }
    var skm by remember { mutableStateOf(data.skm) }
    var nomor_kk by remember { mutableStateOf(data.nomor_kk) }
    var email by remember { mutableStateOf(data.email) }
    var tanggal_di_makamkan by remember { mutableStateOf(data.tanggal_di_makamkan) }
    var tempat_dan_tanggal_lahir by remember { mutableStateOf(data.tempat_dan_tanggal_lahir) }
    var jenis_kelamin by remember { mutableStateOf(data.jenis_kelamin) }
    var rt by remember { mutableStateOf(data.rtm) }
    var rw by remember { mutableStateOf(data.rwm) }
    var sebab by remember { mutableStateOf(data.sebab) }
    var agama by remember { mutableStateOf(data.agama) }
    var kewarganegaraan by remember { mutableStateOf(data.kewarganegaraan) }
    var kelurahan by remember { mutableStateOf(data.kelurahanm) }
    var kecamatan by remember { mutableStateOf(data.kecamatanm) }
    var bapak by remember { mutableStateOf(data.nama_bapak) }
    var ibu by remember { mutableStateOf(data.nama_ibu) }
    var suami_atau_istri by remember { mutableStateOf(data.suami_atau_istri) }
    var anak by remember { mutableStateOf(data.anak) }
    var tanggal_meninggal by remember { mutableStateOf(data.tanggal_meninggal) }

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
                text = "Dengan ini mengajukan permohonan pelaksanaan pemakaman",
                color = Color(0xFF38008B),
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(20.dp))
            TextFieldCustom(
                value = nama_mayat,
                onValueChange = {
                    nama_mayat = it
                },
                title = "Nama Almarhum",
                placeholder = "Masukan Nama Almarhum",
                trailingIcon = {},
                isError = false
            )
            Spacer(modifier = Modifier.padding(20.dp))
            TextFieldCustom(
                value = nomor_kk.toString() ,
                onValueChange = {
                    nomor_kk = it.toIntOrNull() ?: 0
                },
                title = "Nomor KK",
                placeholder = "Masukan Nomor KK Almarhum",
                trailingIcon = {},
                isError = false
            )
            Spacer(modifier = Modifier.padding(10.dp))
            TextFieldCustom(
                value = alamat,
                onValueChange = {},
                title = "Alamat",
                placeholder = "ALamat Almarhum",
                trailingIcon = {},
                isError = false
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Row(
                content = {
                    TextFieldCustom(
                        modifier = Modifier.width(170.dp),
                        value = usia.toString(),
                        onValueChange = {
                            usia = it.toIntOrNull() ?: 0
                        },
                        title = "Umur",
                        placeholder = "00",
                        trailingIcon = {},
                        isError = false
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    TextFieldDropDown(
                        title = "Jenis Kelamin",
                        onValueChange = {
                            jenis_kelamin = it
                        },
                        pilihan = listOf("Laki-laki", "Perempuan"),
                        value = jenis_kelamin,
                        placeholder = "00"
                    )
                }
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Row(
                content = {
                    TextFieldDropDown(
                        title = "Agama", modifier = Modifier.width(130.dp),
                        placeholder = "------",
                        onValueChange = {
                            agama = it
                        },
                        value = agama,
                        pilihan = listOf("Islam", "Kristen", "Katholik", "Hindu", "Budha")
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    TextFieldDropDown(
                        title = "Kewarganegaraan",
                        modifier = Modifier.width(210.dp),
                        pilihan = listOf("WNI", "WNA"),
                        placeholder = "-----",
                        onValueChange = {
                            kewarganegaraan = it
                        },
                        value = kewarganegaraan
                    )

                }
            )
            Spacer(modifier = Modifier.size(20.dp))
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
                title = "Kelurahan",
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    kelurahan = it
                },
                pilihan = listOf("Kelurahan 1", "Kelurahan 2", "Kelurahan 3"),
                placeholder = "-----",
                value = kelurahan,
            )
            Spacer(modifier = Modifier.size(20.dp))
            TextFieldDropDown(
                title = "Kecamatan",
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    kecamatan = it
                },
                pilihan = listOf("Kelurahan 1", "Kelurahan 2", "Kelurahan 3"),
                placeholder = "-----",
                value = kecamatan,
            )
            Spacer(modifier = Modifier.padding(15.dp))
            TextFieldCustom(
                value = tempat_dan_tanggal_lahir,
                onValueChange = {
                    tempat_dan_tanggal_lahir = it
                },
                title = "Tempat/Tanggal lahir",
                placeholder = "Tempat lahir dan tanggal",
                trailingIcon = {},
                isError = false
            )
            Spacer(modifier = Modifier.padding(15.dp))
            TextFieldCustom(
                value = bapak,
                onValueChange = {
                    bapak = it
                },
                title = "Bapak",
                placeholder = "Nama Bapak",
                trailingIcon = {},
                isError = false
            )
            Spacer(modifier = Modifier.padding(15.dp))
            TextFieldCustom(
                value = ibu,
                onValueChange = {
                    ibu = it
                },
                title = "Ibu",
                placeholder = "Nama ibu",
                trailingIcon = {},
                isError = false
            )
            Spacer(modifier = Modifier.padding(15.dp))
            TextFieldCustom(
                value = suami_atau_istri,
                onValueChange = {
                    suami_atau_istri = it
                },
                title = "suami/istri",
                placeholder = "Nama suami/istri",
                trailingIcon = {},
                isError = false
            )
            Spacer(modifier = Modifier.padding(15.dp))
            TextFieldCustom(
                value = anak,
                onValueChange = {
                    anak = it
                },
                title = "Anak",
                placeholder = "Nama  salah satu Anak",
                trailingIcon = {},
                isError = false
            )
            Spacer(modifier = Modifier.padding(15.dp))
            DatePickerBox(
                title = "Meninggal pada tanggal",
                modifier = Modifier.fillMaxWidth(),
                onDateSelected = { date: LocalDate ->
                    tanggal_meninggal = date
                }
            )
            Spacer(modifier = Modifier.padding(15.dp))
            TextFieldCustom(
                value = sebab,
                onValueChange = {
                    sebab = it
                },
                title = "Sebab",
                placeholder = "Sebab Meninggal",
                trailingIcon = {},
                isError = false
            )
            Spacer(modifier = Modifier.padding(15.dp))
            TextFieldCustom(
                value = meninggal_di,
                onValueChange = {
                    meninggal_di = it
                },
                title = "Meninggal Di",
                placeholder = "Meninggal DI Mana",
                trailingIcon = {},
                isError = false
            )
            Spacer(modifier = Modifier.padding(15.dp))
            DatePickerBox(
                title = "Akan Dimakamkan tanggal",
                modifier = Modifier.fillMaxWidth(),
                onDateSelected = { date: LocalDate ->
                    tanggal_di_makamkan = date
                }
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
                                navController.popBackStack()
                            },
                        content = {
                            Text(text = "Kembali", color = Color(0xFF38008B), fontSize = 13.sp)
                        }
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF38008B))
                            .padding(start = 30.dp, end = 30.dp, top = 15.dp, bottom = 15.dp)
                            .clickable {
                                AjuanDataStore.updateLastData { old ->
                                    old.copy(
                                        usia = usia,
                                        alamatw = alamat,
                                        nama_mayat = nama_mayat,
                                        meninggal_di = meninggal_di,
                                        blok_makam = blok_makam,
                                        id_makam = id_makam,
                                        skm = skm,
                                        nomor_kk = nomor_kk,
                                        email = email,
                                        tanggal_di_makamkan = tanggal_di_makamkan,
                                        tempat_dan_tanggal_lahir = tempat_dan_tanggal_lahir,
                                        jenis_kelamin = jenis_kelamin,
                                        rtm = rt,
                                        rwm = rw,
                                        sebab = sebab,
                                        agama = agama,
                                        kewarganegaraan = kewarganegaraan,
                                        kelurahanm = kelurahan,
                                        kecamatanm = kecamatan,
                                        nama_bapak = bapak,
                                        nama_ibu = ibu,
                                        suami_atau_istri = suami_atau_istri,
                                        anak = anak,
                                        tanggal_meninggal = tanggal_meninggal
                                    )
                                }
                                navController.navigate("ajuan3")
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
    Ajuan2Page(rememberNavController(), data = AdminNotifikasi())
}