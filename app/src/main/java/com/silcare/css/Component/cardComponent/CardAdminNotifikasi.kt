package com.silcare.css.Component.cardComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.silcare.css.R
import com.silcare.css.api.AdminNotifikasi
import com.valentinilk.shimmer.shimmer

@Composable
fun CardAdminNotifikasi(
    data: AdminNotifikasi,
    onClickKonfirmasi: () -> Unit,
    onClickBatal: () -> Unit,
    onClickDitail: () -> Unit
) {
    Box(
        modifier = Modifier.background(if (!data.statusNotifikasi) Color(0xFFFEF7FF) else Color.White)
    ) {
        Column(
            modifier = Modifier.padding(
                start = 20.dp,
                end = 20.dp,
                bottom = 10.dp,
                top = 10.dp
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = data.di_wakilkan_oleh,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF38008B)
                )
                Text(
                    modifier = Modifier.clickable { onClickDitail() },
                    text = "Ditail",
                    color = Color(0xFF0059FF),
                    fontWeight = FontWeight.Light
                )
            }

            Spacer(modifier = Modifier.size(10.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text("Nama Almarhum", fontWeight = FontWeight.Light)
                    Spacer(modifier = Modifier.size(5.dp))
                    Text(data.nama_mayat)
                }
            }

            Spacer(modifier = Modifier.size(10.dp))

            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                val isCompact = maxWidth < 360.dp

                if (isCompact) {
                    Column {
                        InfoSection(data)
                        Spacer(modifier = Modifier.height(12.dp))
                        ActionSectionColumn(
                            data = data,
                            onClickBatal = onClickBatal,
                            onClickKonfirmasi = onClickKonfirmasi
                        )
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        InfoSection(data)

                        ActionSectionRow(
                            data = data,
                            onClickBatal = onClickBatal,
                            onClickKonfirmasi = onClickKonfirmasi
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoSection(data: AdminNotifikasi) {
    Column(
        content = {
            Row(
                content = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            Text(
                                text = "Input data",
                                color = Color(0xFF1D1B20),
                                fontWeight = FontWeight.Light
                            )
                            Spacer(modifier = Modifier.size(10.dp))
                            Icon(
                                modifier = Modifier.size(if (data.inputData) 20.dp else 10.dp),
                                painter = if (data.inputData) painterResource(
                                    R.drawable.sudah
                                ) else painterResource(
                                    R.drawable.belum
                                ),
                                contentDescription = null,
                                tint = if (data.inputData) Color(0xFF35A700) else Color(
                                    0xFFCC0000
                                )
                            )
                        }
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            Text(
                                text = "foto KTP perwakilan",
                                color = Color(0xFF1D1B20),
                                fontWeight = FontWeight.Light
                            )
                            Spacer(modifier = Modifier.size(10.dp))
                            Icon(
                                modifier = Modifier.size(if (data.fotoKtpPerwakilan) 20.dp else 10.dp),
                                painter = if (data.fotoKtpPerwakilan) painterResource(
                                    R.drawable.sudah
                                ) else painterResource(R.drawable.belum),
                                contentDescription = null,
                                tint = if (data.fotoKtpPerwakilan) Color(
                                    0xFF35A700
                                ) else Color(0xFFCC0000)
                            )
                        }
                    )

                }
            )

            Row(
                content = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            Text(
                                text = "Foto KTP",
                                color = Color(0xFF1D1B20),
                                fontWeight = FontWeight.Light
                            )
                            Spacer(modifier = Modifier.size(10.dp))
                            Icon(
                                modifier = Modifier.size(if (data.fotoKtp) 20.dp else 10.dp),
                                painter = if (data.fotoKtp) painterResource(
                                    R.drawable.sudah
                                ) else painterResource(
                                    R.drawable.belum
                                ),
                                contentDescription = null,
                                tint = if (data.fotoKtp) Color(0xFF35A700) else Color(
                                    0xFFCC0000
                                )
                            )
                        }
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            Text(
                                text = "Foto KK",
                                color = Color(0xFF1D1B20),
                                fontWeight = FontWeight.Light
                            )
                            Spacer(modifier = Modifier.size(10.dp))
                            Icon(
                                modifier = Modifier.size(if (data.fotoKk) 20.dp else 10.dp),
                                painter = if (data.fotoKk) painterResource(R.drawable.sudah) else painterResource(
                                    R.drawable.belum
                                ),
                                contentDescription = null,
                                tint = if (data.fotoKk) Color(0xFF35A700) else Color(
                                    0xFFCC0000
                                )
                            )
                        }
                    )
                }
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Text(
                        text = "Surat kematian",
                        color = Color(0xFF1D1B20),
                        fontWeight = FontWeight.Light
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Icon(
                        modifier = Modifier.size(if (data.suratKematian) 20.dp else 10.dp),
                        painter = if (data.suratKematian) painterResource(R.drawable.sudah) else painterResource(
                            R.drawable.belum
                        ),
                        contentDescription = null,
                        tint = if (data.suratKematian) Color(0xFF35A700) else Color(
                            0xFFCC0000
                        )
                    )
                }
            )
        }
    )
}

@Composable
private fun ActionSectionRow(
    data: AdminNotifikasi,
    onClickBatal: () -> Unit,
    onClickKonfirmasi: () -> Unit
) {
    Column {
        if (data.statusNotifikasi) {
            Text(
                modifier = Modifier.align(Alignment.End),
                text = if (data.status) "Di Terima" else "Di Tolak",
                color = if (data.status) Color(0xFF35A700) else Color(0xFFCC0000),
                fontWeight = FontWeight.Bold
            )
        } else {
            Box(
                modifier = Modifier
                    .border(1.dp, Color(0xFFCC0000), RoundedCornerShape(5.dp))
                    .width(100.dp)
                    .height(30.dp)
                    .clickable { onClickBatal() },
                contentAlignment = Alignment.Center
            ) {
                Text("Tolak", color = Color(0xFFCC0000))
            }
            Spacer(modifier = Modifier.size(5.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color(0xFF35A700))
                    .width(100.dp)
                    .height(30.dp)
                    .clickable { onClickKonfirmasi() },
                contentAlignment = Alignment.Center
            ) {
                Text("Konfirmasi", color = Color.White)
            }
        }
    }
}

@Composable
private fun ActionSectionColumn(
    data: AdminNotifikasi,
    onClickBatal: () -> Unit,
    onClickKonfirmasi: () -> Unit
) {
    if (data.statusNotifikasi) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = if (data.status) "Di Terima" else "Di Tolak",
            color = if (data.status) Color(0xFF35A700) else Color(0xFFCC0000),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End
        )
    } else {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .border(1.dp, Color(0xFFCC0000), RoundedCornerShape(5.dp))
                    .fillMaxWidth()
                    .height(40.dp)
                    .clickable { onClickBatal() },
                contentAlignment = Alignment.Center
            ) {
                Text("Tolak", color = Color(0xFFCC0000))
            }
            Spacer(modifier = Modifier.size(8.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color(0xFF35A700))
                    .fillMaxWidth()
                    .height(40.dp)
                    .clickable { onClickKonfirmasi() },
                contentAlignment = Alignment.Center
            ) {
                Text("Konfirmasi", color = Color.White)
            }
        }
    }
}

@Composable
private fun StatusIcon(status: Boolean) {
    Icon(
        painter = if (status) painterResource(R.drawable.sudah) else painterResource(R.drawable.belum),
        contentDescription = null,
        tint = if (status) Color(0xFF35A700) else Color(0xFFCC0000),
        modifier = Modifier.size(if (status) 20.dp else 10.dp)
    )
}


@Composable
fun CardAdminNotifikasiShimmer() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(20.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 120.dp, height = 20.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.Gray.copy(alpha = 0.3f))
                        .shimmer()
                )
                Box(
                    modifier = Modifier
                        .size(width = 50.dp, height = 15.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.Gray.copy(alpha = 0.3f))
                        .shimmer()
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .size(width = 200.dp, height = 15.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.Gray.copy(alpha = 0.3f))
                    .shimmer()
            )

            Spacer(modifier = Modifier.height(10.dp))

            repeat(4) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(width = 120.dp, height = 15.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.Gray.copy(alpha = 0.3f))
                            .shimmer()
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.Gray.copy(alpha = 0.3f))
                            .shimmer()
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 100.dp, height = 30.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.Gray.copy(alpha = 0.3f))
                        .shimmer()
                )
                Box(
                    modifier = Modifier
                        .size(width = 100.dp, height = 30.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.Gray.copy(alpha = 0.3f))
                        .shimmer()
                )
            }
        }
    }
}

@Preview
@Composable
private fun CardAdminNotifikasiPrev() {
    CardAdminNotifikasi(
        data = AdminNotifikasi(statusNotifikasi = true),
        onClickKonfirmasi = {},
        onClickBatal = {},
        onClickDitail = {},
    )
}