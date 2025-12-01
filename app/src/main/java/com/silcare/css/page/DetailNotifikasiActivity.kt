package com.silcare.css.page

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.silcare.css.R
import com.silcare.css.api.MakamViewModel


class DetailNotifikasiActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val docId = intent.getStringExtra("docId") ?: ""

        setContent {
            val viewModel: MakamViewModel = viewModel()
            viewModel.fetchNotifikasiByDocId(
                docId,
                onSuccess = { /* sukses */ },
                onError = { /* error */ }
            )
            val notifikasi by viewModel.notifikasiDetail.collectAsState()

            Scaffold(
                topBar = {
                    SmallTopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.White,
                            titleContentColor = Color(0xFF38008B),
                        ),
                        title = {},
                        navigationIcon = {
                            IconButton(onClick = { finish() }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.iconback),
                                    tint = Color(0xFF38008B),
                                    modifier = Modifier.size(15.dp),
                                    contentDescription = "Kembali"
                                )
                            }
                        }
                    )
                }
            ) { paddingValues ->
                notifikasi?.let { data ->
                    DetailNotifikasi(
                        data = data,
                        modifier = Modifier.padding(paddingValues)
                    )
                }
            }
        }
    }
}

