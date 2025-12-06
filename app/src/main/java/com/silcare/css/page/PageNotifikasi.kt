package com.silcare.css.page

import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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
    var filter by remember { mutableStateOf("") }
    var isFilterMenuOpen by remember { mutableStateOf(false) }

    LaunchedEffect(Unit,filter) {
        viewModel.fetchDataNotifikasi()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        content = {
            TopAppBarFillterHistory(
                onFillter = { isFilterMenuOpen = true },
                onHistory = {}
            )

            DropdownMenu(
                expanded = isFilterMenuOpen,
                onDismissRequest = { isFilterMenuOpen = false },
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(16.dp),
                        clip = false
                    )
                    .padding(vertical = 4.dp)
            ) {
                DropdownMenu(
                    expanded = isFilterMenuOpen,
                    onDismissRequest = { isFilterMenuOpen = false }) {
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (filter == "az") {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "checked",
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                }
                                Text("Nama A → Z")
                            }
                        },
                        onClick = {
                            filter = "az"
                            isFilterMenuOpen = false
                        }
                    )
                    DropdownMenuItem(text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (filter == "za") {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "checked",
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                            Text("Nama Z → A")
                        }
                    }, onClick = {
                        filter = "za"
                        isFilterMenuOpen = false
                    })
                    DropdownMenuItem(text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (filter == "max") {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "checked",
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                            Text("Terisi Paling Banyak")
                        }
                    }, onClick = {
                        filter = "max"
                        isFilterMenuOpen = false
                    })
                    DropdownMenuItem(text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (filter == "min") {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "checked",
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                            Text("Terisi Paling Sedikit")
                        }
                    }, onClick = {
                        filter = "min"
                        isFilterMenuOpen = false
                    })
                }
            }

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