package com.silcare.css.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.silcare.css.Component.cardComponent.CardAdminNotifikasi
import com.silcare.css.Component.top_app_bar.TopAppBarFillterHistory
import com.silcare.css.api.AdminNotifikasi

@Composable
fun PageNotifikasi() {
    Column(
        modifier = Modifier.fillMaxSize(),
        content = {
            TopAppBarFillterHistory(
                onFillter = {},
                onHistory = {}
            )
            LazyColumn {
                items(20) {
                    CardAdminNotifikasi(
                        data = AdminNotifikasi(),
                        onClickDitail = {},
                        onClickBatal = {},
                        onClickKonfirmasi = {}
                    )
                    Spacer(modifier = Modifier.padding(1.dp))
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PageNotifikasiPrev() {
    PageNotifikasi()
}