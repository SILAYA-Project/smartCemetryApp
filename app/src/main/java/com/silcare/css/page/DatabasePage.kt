package com.silcare.css.page

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.silcare.css.Component.cardComponent.CardDataMayat
import com.silcare.css.Component.cardComponent.CardDataMayatShimmer
import com.silcare.css.Component.top_app_bar.TopSearchBarFillter
import com.silcare.css.api.AdminNotifikasi
import com.silcare.css.api.MakamViewModel
import com.silcare.css.api.Mayat
import com.silcare.css.api.UserPreferences

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatabasePage(
    viewModel: MakamViewModel = viewModel(),
    onProfile: () -> Unit
) {
    val mayatList by viewModel.mayatList.collectAsState()
    val context = LocalContext.current
    val imgUrl = remember { mutableStateOf("") }
    val uidFlow = remember { UserPreferences(context).uid }
    val uid by uidFlow.collectAsState(initial = null)
    val isLoading by viewModel.isLoading.collectAsState()
    var search by remember { mutableStateOf("") }
    var filter by remember { mutableStateOf("") }
    var isFilterMenuOpen by remember { mutableStateOf(false) }

    LaunchedEffect(Unit,search,filter) {
        viewModel.fetchDataMayat(search, filter)
    }

    viewModel.getUserData(
        uid = uid,
        onSuccess = { userData ->
            imgUrl.value = userData.profil
        },
        onError = { error ->
            Log.e("DataUser", error)
        }
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        content = {
            Column(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                content = {

                    TopSearchBarFillter(
                        onClick = { onProfile() },
                        value = search,
                        onValueChange = { search = it },
                        imgUrl = imgUrl.value,
                        onClickFilter = { isFilterMenuOpen = true }
                    )

                    Spacer(modifier = Modifier.padding(5.dp))
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
                        if (isLoading) {
                            items(5) {
                                CardDataMayatShimmer()
                                Spacer(modifier = Modifier.height(5.dp))
                            }
                        } else {
                            items(mayatList) {
                                CardDataMayat(data = it)
                                Spacer(modifier = Modifier.height(5.dp))
                            }
                        }
                    }
                }
            )
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun DatabasePagePrev() {
//    DatabasePage(
//        onProfile = {},
//        viewModel = Mayat,
//        onDetail = {}
//    )
}