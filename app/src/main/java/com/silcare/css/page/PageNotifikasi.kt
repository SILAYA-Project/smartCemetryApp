package com.silcare.css.page

import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.silcare.css.Component.cardComponent.CardAdminNotifikasi
import com.silcare.css.Component.top_app_bar.TopAppBarFillterHistory
import com.silcare.css.api.MakamViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PageNotifikasi(viewModel: MakamViewModel = viewModel()) {
    val notifikasiList by viewModel.notifikasiList.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchDataNotifikasi()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        content = {
            TopAppBarFillterHistory(
                onFillter = {},
                onHistory = {}
            )
            LazyColumn {
                items(notifikasiList){
                    CardAdminNotifikasi(
                        docId = it.id_mayat,
                        data = it,
                        onClickDitail = { docId ->
                            val intent = Intent(context, DetailNotifikasiActivity::class.java)
                            intent.putExtra("docId", docId)
                            context.startActivity(intent)
                        },
                        onClickKonfirmasi = {
                            viewModel.konfirmasiNotifikasi(
                                notifikasi = it,
                            ) { success ->
                                if (success) {
                                    viewModel.fetchDataNotifikasi()
                                }
                            }
                        },
                        onClickBatal = {
                            viewModel.tolakNotifikasi(
                                notifikasi = it,
                            ) { success ->
                                if (success) {
                                    viewModel.fetchDataNotifikasi()
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.padding(1.dp))
                }
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun PageNotifikasiPrev() {
    PageNotifikasi()
}