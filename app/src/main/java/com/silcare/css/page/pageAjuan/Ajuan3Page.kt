package com.silcare.css.page.pageAjuan

import android.app.Activity
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Timestamp
import com.silcare.css.Component.cardComponent.CardAddImage
import com.silcare.css.Component.textFieldCustom.TextFieldDropDown
import com.silcare.css.api.AdminNotifikasi
import com.silcare.css.api.AjuanDataStore
import com.silcare.css.api.MakamViewModel
import com.silcare.css.api.UploadRepository
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Ajuan3Page(
    navController: NavController,
    data: AdminNotifikasi,
    uploadRepository: UploadRepository = UploadRepository()
) {
    val viewModel: MakamViewModel = viewModel()
    val blokList by viewModel.blokMakamList.collectAsState()
    val idMakamList by viewModel.idMakamList.collectAsState()
    val context = LocalContext.current
    var selectedBlok by remember { mutableStateOf("") }
    var selectedIdMakam by remember { mutableStateOf("") }
    val dataList = AjuanDataStore.listData
    var ktpAhliWarisUri by remember { mutableStateOf<Uri?>(null) }
    var ktpAlmarhumUri by remember { mutableStateOf<Uri?>(null) }
    var kkUri by remember { mutableStateOf<Uri?>(null) }
    var skkUri by remember { mutableStateOf<Uri?>(null) }
    var buktiPembayranUri by remember { mutableStateOf<Uri?>(null) }
    var metoPem by remember { mutableStateOf("") }
    val clipboardManager = LocalClipboardManager.current

    LaunchedEffect(Unit) {
        println("Total data tersimpan: ${dataList.size}")
        println("Data terakhir: ${dataList.lastOrNull()}")
        viewModel.fetchDataBlokMakam()
    }

    LaunchedEffect(data) {
        Log.d("ajuan3", "Data diterima: $data")
    }

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
                text = "Surat Keterangan Kematian Dari Rumah Sakit / Fasilitas Kesehatan / Kelurahan(*)",
                color = Color(0xFF38008B),
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(20.dp))
            Row {
                CardAddImage(
                    title = "KTP Ahli Waris",
                    desc = "Upload KTP Ahli Waris",
                    onImageSelected = { uri -> ktpAhliWarisUri = uri }
                )
                Spacer(modifier = Modifier.padding(10.dp))
                CardAddImage(
                    title = "KTP Almarhum",
                    desc = "Upload KTP Almarhum",
                    onImageSelected = { uri -> ktpAlmarhumUri = uri }
                )
            }
            Spacer(modifier = Modifier.padding(20.dp))
            Row {
                CardAddImage(
                    title = "KK",
                    desc = "Masukan Kartu Keluarga",
                    onImageSelected = { uri -> kkUri = uri }
                )
                Spacer(modifier = Modifier.padding(10.dp))

                CardAddImage(
                    title = "SKK",
                    desc = "Masukan Surat Kematian",
                    onImageSelected = { uri -> skkUri = uri }
                )

            }
            Spacer(modifier = Modifier.padding(20.dp))
            TextFieldDropDown(
                title = "Block Makam",
                modifier = Modifier.fillMaxWidth(),
                value = selectedBlok,
                onValueChange = { namaBlok ->
                    selectedBlok = namaBlok
                    val blokId = blokList.firstOrNull { it.nama_blok == namaBlok }?.id
                    if (blokId != null) {
                        viewModel.fetchIdMakamByBlok(blokId)
                        Log.e("Ajuan3Page 1 : ", blokId)
                    }
                    Log.e("Ajuan3Page 2 : ", blokId!!.toString())
                },
                pilihan = blokList.map { it.nama_blok }
            )
            if (selectedBlok.isNotEmpty()) {
                Spacer(modifier = Modifier.padding(20.dp))
                TextFieldDropDown(
                    title = "No Makam",
                    modifier = Modifier.fillMaxWidth(),
                    pilihan = idMakamList.map { it.code_makam },
                    value = selectedIdMakam,
                    onValueChange = { selectedIdMakam = it }
                )
            }
            Spacer(modifier = Modifier.padding(20.dp))
            TextFieldDropDown(
                title = "Metode Pembayaran",
                modifier = Modifier.fillMaxWidth(),
                pilihan = listOf("Transfer Bank", "Tunai"),
                value = metoPem,
                onValueChange = { metoPem = it }
            )

            Spacer(modifier = Modifier.padding(10.dp))

            if (metoPem == "Transfer Bank") {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    content = {
                        Text(text = "Silahkan Melakukan Pembayaran: ")
                        Spacer(modifier = Modifier.padding(10.dp))

                        Text(text = "brksyariah", fontWeight = FontWeight.Bold)
//                        Text(text = "106-20-02136 ", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Text(
                            text = "106-20-02136",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier.clickable {
                                clipboardManager.setText(AnnotatedString("106-20-02136"))
                            }
                        )
                        Text(text = "YAYASAN NURUL KHAIR MADANI", fontWeight = FontWeight.Bold)

                        Spacer(modifier = Modifier.padding(10.dp))

                        Text(text = "Masukan Bukti Pembayaran anda :")

                        Spacer(modifier = Modifier.padding(10.dp))

                        CardAddImage(
                            width = 150,
                            title = "Bukti Pembayaran",
                            desc = "Masukan bukti di sini",
                            onImageSelected = { uri -> buktiPembayranUri = uri }
                        )
                    }
                )
            } else if (metoPem == "Tunai") {
                Text(text = "Silahkan Ke menuju ke Admin")
            }

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
                    val scope = rememberCoroutineScope()

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF38008B))
                            .padding(horizontal = 30.dp, vertical = 15.dp)
                            .clickable {
                                scope.launch {
                                    val urlKtpAhliWaris = ktpAhliWarisUri?.let {
                                        uploadRepository.uploadToCloudinary(context, it)
                                    }

                                    val urlKtpAlmarhum = ktpAlmarhumUri?.let {
                                        uploadRepository.uploadToCloudinary(context, it)
                                    }

                                    val urlSuratKematian = skkUri?.let {
                                        uploadRepository.uploadToCloudinary(context, it)
                                    }

                                    val urlBuktiPembayran = buktiPembayranUri?.let {
                                        uploadRepository.uploadToCloudinary(context, it)
                                    }

                                    val urlFotoKk = kkUri?.let {
                                        uploadRepository.uploadToCloudinary(context, it)
                                    }

                                    var data = navController.previousBackStackEntry
                                        ?.savedStateHandle
                                        ?.get<AdminNotifikasi>("adminData")

                                    if (data == null) {
                                        data = AjuanDataStore.getLastData()
                                    }

                                    val newData = data?.copy(
                                        blok_makam = selectedBlok,
                                        id_makam = selectedIdMakam,
                                        tanggal_pengajuan = Timestamp.now(),
                                        urlFotoKtpPerwakilan = urlKtpAhliWaris ?: "",
                                        urlFotoKtp = urlKtpAlmarhum ?: "",
                                        urlSuratKematian = urlSuratKematian ?: "",
                                        urlBuktiPembayran = urlBuktiPembayran ?: "",
                                        urlFotoKk = urlFotoKk ?: ""
                                    ) ?: AdminNotifikasi()

                                    AjuanDataStore.updateLastData { newData }

                                    val finalData = AjuanDataStore.getLastData()

                                    viewModel.tambahNotifikasiAdmin(finalData!!) { success ->
                                        if (success) {
                                            (context as? Activity)?.finish()
                                        } else {
                                            Log.e("tambah mayat", "❌ Gagal menambahkan notifikasi")
                                        }
                                    }
                                }
                            }
                    ) {
                        Text(text = "Ajukan", color = Color.White, fontSize = 13.sp)
                    }
                }
            )
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun Ajuan3PagePrev() {
    Ajuan3Page(rememberNavController(), AdminNotifikasi())
}