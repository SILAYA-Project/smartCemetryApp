package com.silcare.css.page

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.silcare.css.Component.textFieldCustom.TextFieldCustom
import com.silcare.css.Component.textFieldCustom.TextFieldDropDown

@Composable
fun Ajuan1Page() {
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
                value = "",
                onValueChange = {},
                title = "Nama",
                placeholder = "Masukan Nama Anda",
                trailingIcon = {},
                isError = false
            )
            Spacer(modifier = Modifier.padding(10.dp))
            TextFieldCustom(
                value = "",
                onValueChange = {},
                title = "Nomor Hp",
                placeholder = "0813-----------------",
                trailingIcon = {},
                isError = false
            )
            Spacer(modifier = Modifier.padding(10.dp))
            TextFieldCustom(
                value = "",
                onValueChange = {},
                title = "Nomor Idetitas / KTP",
                placeholder = "092739------",
                trailingIcon = {},
                isError = false
            )
            Spacer(modifier = Modifier.padding(10.dp))
            TextFieldCustom(
                value = "",
                onValueChange = {},
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
                        value = "",
                        onValueChange = {},
                        title = "RT",
                        placeholder = "00",
                        trailingIcon = {},
                        isError = false
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    TextFieldCustom(
                        modifier = Modifier.width(170.dp),
                        value = "",
                        onValueChange = {},
                        title = "RW",
                        placeholder = "00",
                        trailingIcon = {},
                        isError = false
                    )
                }
            )
            Spacer(modifier = Modifier.size(20.dp))
            TextFieldDropDown(title = "Kelurahan")
            Spacer(modifier = Modifier.size(20.dp))
            TextFieldDropDown(title = "Kecamatan")
            Spacer(modifier = Modifier.padding(20.dp))
            TextFieldCustom(
                value = "",
                onValueChange = {},
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
                            .clickable { },
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
                            .clickable { },
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
    Ajuan1Page()
}