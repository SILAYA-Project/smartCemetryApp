package com.silcare.css.page

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.silcare.css.api.AdminNotifikasi
import com.silcare.css.api.toFormattedString

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailNotifikasi(data: AdminNotifikasi, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        DetailSection(title = "Status Data") {
            BooleanRow("Input Data ", data.inputData)
            BooleanRow("Foto KTP Perwakilan ", data.fotoKtpPerwakilan)
            BooleanRow("Foto KTP Almarhum ", data.fotoKtp)
            BooleanRow("Foto KK ", data.fotoKk)
            BooleanRow("Surat Kematian ", data.suratKematian)
        }

        DetailSection(title = "Data Almarhum") {
            InfoRow("Nama ", data.nama_mayat)
            InfoRow("Usia ", data.usia)
            InfoRow("Jenis Kelamin ", data.jenis_kelamin)
            InfoRow("Tempat & Tanggal Lahir ", data.tempat_dan_tanggal_lahir)
            InfoRow("Tanggal Meninggal ", data.tanggal_meninggal.toFormattedString())
            InfoRow("Sebab ", data.sebab)
            InfoRow("Blok Makam ", data.blok_makam)
            InfoRow("ID Makam ", data.id_makam)
            InfoRow("Meninggal di ", data.meninggal_di)
            InfoRow("Wafat ", data.wafat)
            InfoRow("SKM ", data.skm)
            InfoRow("Nomor KK (Mayat) ", data.nomor_kk)
        }

        DetailSection(title = "Perwakilan & Kontak") {
            InfoRow("Diwakilkan Oleh ", data.di_wakilkan_oleh)
            InfoRow("Hubungan ", data.hubungan)
            InfoRow("Nomor Telepon ", data.nomor_telpon)
            InfoRow("Email ", data.email)
            InfoRow("Nomor NIK (Perwakilan) ", data.nomor_nik)
        }

        DetailSection(title = "Alamat Makam") {
            InfoRow("Alamat ", data.alamatm)
            InfoRow("RT ", data.rtm)
            InfoRow("RW ", data.rwm)
            InfoRow("Kelurahan ", data.kelurahanm)
            InfoRow("Kecamatan ", data.kecamatanm)
        }

        DetailSection(title = "Alamat Perwakilan") {
            InfoRow("Alamat ", data.alamatw)
            InfoRow("RT ", data.rtw)
            InfoRow("RW ", data.rww)
            InfoRow("Kelurahan ", data.kelurahanw)
            InfoRow("Kecamatan ", data.kecamatanw)
        }

        DetailSection(title = "Data Keluarga") {
            InfoRow("Agama ", data.agama)
            InfoRow("Kewarganegaraan ", data.kewarganegaraan)
            InfoRow("Nama Ayah ", data.nama_bapak)
            InfoRow("Nama Ibu ", data.nama_ibu)
            InfoRow("Suami/Istri ", data.suami_atau_istri)
            InfoRow("Anak ", data.anak)
        }

        DetailSection(title = "Dokumen") {
            ImagePreview("KTP Perwakilan ", data.urlFotoKtpPerwakilan)
            ImagePreview("KTP Almarhum ", data.urlFotoKtp)
            ImagePreview("KK ", data.urlFotoKk)
            ImagePreview("Surat Kematian ", data.urlSuratKematian)
        }

        DetailSection(title = "Waktu Proses") {
            InfoRow("Tanggal Pengajuan ", data.tanggal_pengajuan.toFormattedString())
            InfoRow("Tanggal Konfirmasi ", data.tanggal_konfirmasi.toFormattedString())
            InfoRow("Tanggal Dimakamkan ", data.tanggal_di_makamkan.toFormattedString())
        }
    }
}

@Composable
fun BooleanRow(label: String, value: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.onSurface
        )
        Icon(
            imageVector = if (value) Icons.Default.Check else Icons.Default.Close,
            contentDescription = null,
            tint = if (value) Color.Green else Color.Red
        )
    }
    Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
}

@Composable
fun DetailSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.1.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFF38008B)
            )
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
fun InfoRow(label: String, value: String?) {
    if (!value.isNullOrEmpty()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = Color.Black
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black.copy(alpha = 0.8f)
            )
        }
        Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
    }
}

@Composable
fun ImagePreview(label: String, url: String) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            label,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black
        )
        Spacer(Modifier.height(8.dp))
        if (url.isNotEmpty()) {
            Image(
                painter = rememberAsyncImagePainter(url),
                contentDescription = label,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color.LightGray)
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color(0xFFFFEAEA)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Gambar tidak tersedia",
                    tint = Color.Red,
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun DetailNotifikasiPrev() {
    MaterialTheme {
        DetailNotifikasi(
            AdminNotifikasi(
                nama_mayat = "Budi Santoso",
                usia = "65",
                jenis_kelamin = "Laki-laki",
                tempat_dan_tanggal_lahir = "Jakarta, 1 Januari 1960",
                sebab = "Sakit",
                blok_makam = "Blok A-12",
                di_wakilkan_oleh = "Andi Santoso",
                hubungan = "Anak",
                nomor_telpon = "08123456789",
                email = "andi@mail.com",
                alamatm = "Jl. Makam Indah No. 45",
                kelurahanm = "Kelurahan A",
                kecamatanm = "Kecamatan B",
                fotoKtp = true,
                fotoKtpPerwakilan = true,
                suratKematian = false,
                urlFotoKtp = "https://picsum.photos/300/200",
                nomor_nik = "1234567890123456",
                nomor_kk = "3201010101010101",
            )
        )
    }
}