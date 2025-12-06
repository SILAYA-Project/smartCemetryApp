package com.silcare.css.page

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.silcare.css.Component.cardComponent.CardNoMayat
import com.silcare.css.Component.top_app_bar.TopSearchBarFillter
import com.silcare.css.R
import com.silcare.css.api.IdMakam
import com.silcare.css.api.MakamViewModel
import com.silcare.css.api.UserPreferences

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoMakamPage(
    viewModel: MakamViewModel,
    blokId: String,
    navController: NavController
) {
    var idMakamList by remember { mutableStateOf<List<IdMakam>>(emptyList()) }
    val context = LocalContext.current
    val imgUrl = remember { mutableStateOf("") }
    val uidFlow = remember { UserPreferences(context).uid }
    val uid by uidFlow.collectAsState(initial = null)
    var search by remember { mutableStateOf("") }
    var filter by remember { mutableStateOf("") }
    var isFilterMenuOpen by remember { mutableStateOf(false) }

    LaunchedEffect(blokId) {
        viewModel.fetchIdMakamByBlokOnce(blokId) { list ->
            idMakamList = list
        }
    }
    viewModel.getUserData(
        uid = uid,
        onSuccess = { userData ->
            imgUrl.value = userData.profil
            Log.d("imgUrl", imgUrl.value)
        },
        onError = { error ->
            Log.e("DataUser", error)
        }
    )
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(start = 20.dp, end = 20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier
                    .padding(end = 15.dp)
                    .clickable { navController.popBackStack() }
                    .size(20.dp),
                painter = painterResource(R.drawable.iconback),
                contentDescription = null,
                tint = Color(0xFF38008B)
            )
            TopSearchBarFillter(
                onValueChange = {},
                onClick = {},
                value = "",
                imgUrl = imgUrl.value,
                onClickFilter = {}
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(modifier = Modifier.padding(start = 5.dp), text = "Nomor Blok ・ ${idMakamList.size}")
            Text(modifier = Modifier.padding(end = 20.dp), text = "Lokasi")
        }

        Spacer(modifier = Modifier.padding(5.dp))

        LazyColumn {
            items(idMakamList) { makam ->
                CardNoMayat(data = makam)
                Spacer(modifier = Modifier.padding(5.dp))
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun NoMakamPagePrev() {
    NoMakamPage(
        viewModel = MakamViewModel(),
        blokId = "1",
        navController = rememberNavController()
    )
}