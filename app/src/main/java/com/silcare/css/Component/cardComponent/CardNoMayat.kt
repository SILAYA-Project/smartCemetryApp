package com.silcare.css.Component.cardComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.silcare.css.R
import com.silcare.css.api.noBlok

@Composable
fun CardNoMayat(data: noBlok, onClik: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .border(
                width = 1.dp,
                color = Color(0xFFEEDDFF),
                shape = RoundedCornerShape(10.dp)
            ),
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color.White)
                    .padding(start = 20.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                content = {
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        content = {
                            Row {
                                Text(
                                    text = "${data.no} - ${data.namaMakam}",
                                    color = Color(0xFF38008B),
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                            if (!data.status) {
                                Text(text = data.namaAlm, fontSize = 15.sp, color = Color(0xFF38008B))
                            } else {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    content = {
                                        Text(text = "Tersedia", fontSize = 15.sp, color = Color(0xFF848484), fontWeight = FontWeight.Bold)
                                        Spacer(modifier = Modifier.size(10.dp))
                                        Icon(
                                            modifier = Modifier.size(20.dp),
                                            painter = painterResource(R.drawable.sudah),
                                            contentDescription = null,
                                            tint = Color(0xFF35A700)
                                        )
                                    }
                                )
                            }
                        }
                    )
                    Box(
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(10.dp))
                            .background(Color(0xFFFEF7FF))
                            .size(80.dp),
                        contentAlignment = Alignment.Center,
                        content = {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                painter = painterResource(R.drawable.petaicon),
                                contentDescription = null,
                                tint = Color(0xFF38008B)
                            )
                        }
                    )
                }
            )
        }
    )
}

@Preview
@Composable
private fun CardNoMayatPrev() {
    CardNoMayat(data = noBlok(status = true), onClik = {})
}