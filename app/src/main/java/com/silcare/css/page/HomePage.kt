package com.silcare.css.page

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.silcare.css.Component.cardComponent.CardMakam
import com.silcare.css.Component.cardComponent.CardMakamShimmer
import com.silcare.css.Component.top_app_bar.TopSearchBarFillter
import com.silcare.css.api.MakamViewModel
import com.silcare.css.api.UserPreferences

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomePage(viewModel: MakamViewModel = viewModel(),navController: NavController, uid: String? = null ,onProfile: () -> Unit) {
    val blokMakamList by viewModel.blokMakamList.collectAsState()
    val imgUrl = remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        viewModel.fetchDataBlokMakam()
    }
    viewModel.getUserData(
        uid = uid,
        onSuccess = { userData ->
            imgUrl.value = userData.profil
            Log.d("DataUser", userData.toString())
        },
        onError = { error ->
            Log.e("DataUser", error)
        }
    )
    Box(
        modifier = Modifier.fillMaxSize().background(Color.White),
        content = {
            Column (
                modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                content = {
                    TopSearchBarFillter(
                        onClick = {onProfile()},
                        onValueChange = {},
                        value = "",
                        imgUrl = imgUrl.value
                    )
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        content = {
                            Text(modifier = Modifier.padding(start = 5.dp), text = "Nama Makam ・ ${blokMakamList.size}")
                            Text(modifier = Modifier.padding(end = 20.dp), text = "Lokasi")
                        }
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    LazyColumn {
                        if (blokMakamList.isEmpty()) {
                            items(5) {
                                CardMakamShimmer()
                                Spacer(modifier = Modifier.height(5.dp))
                            }
                        } else {
                            items(blokMakamList) {
                                CardMakam(
                                    namaMakam = it.nama_blok,
                                    terisi = it.isi,
                                    max = it.maximal,
                                    onClick = {
                                        viewModel.fetchIdMakamByBlokOnce(it.id, onResult = {})
                                        Log.d("id blok", it.id)
                                        navController.navigate("noMakamPage/${it.id}")
                                    }
                                )
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
private fun HomePagePrev() {
    HomePage(navController = rememberNavController(), uid = null, onProfile = {})
}