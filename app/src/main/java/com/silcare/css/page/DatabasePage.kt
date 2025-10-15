package com.silcare.css.page

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.silcare.css.Component.cardComponent.CardDataMayat
import com.silcare.css.Component.cardComponent.CardDataMayatShimmer
import com.silcare.css.Component.top_app_bar.TopSearchBarFillter
import com.silcare.css.api.MakamViewModel
import com.silcare.css.api.Mayat
import com.silcare.css.api.UserPreferences

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatabasePage(viewModel: MakamViewModel = viewModel()) {
    val mayatList by viewModel.mayatList.collectAsState()
    val context = LocalContext.current
    val imgUrl = remember { mutableStateOf("") }
    val uidFlow = remember { UserPreferences(context).uid }
    val uid by uidFlow.collectAsState(initial = null)
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchDataMayat()
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
                        onClick = {},
                        onValueChange = {},
                        value = "",
                        imgUrl = imgUrl.value
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
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
    DatabasePage()
}