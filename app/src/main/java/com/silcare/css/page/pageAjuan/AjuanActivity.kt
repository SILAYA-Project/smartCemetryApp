package com.silcare.css.page.pageAjuan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.silcare.css.api.AdminNotifikasi
import com.silcare.css.page.pageAjuan.ui.theme.CSSTheme

class AjuanActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            Scaffold(
                content = {
                    NavHost(
                        modifier = Modifier.padding(it),
                        navController = navController,
                        startDestination = "ajuan1"
                    ) {
                        composable("ajuan1") {
                            Ajuan1Page(
                                navController = navController,
                                data = AdminNotifikasi()
                            )
                        }
                        composable("ajuan2") {
                            Ajuan2Page(
                                navController = navController,
                                data = AdminNotifikasi()
                            )
                        }
                        composable("ajuan3") { Ajuan3Page(navController = navController, data = AdminNotifikasi()) }
                    }
                }
            )
        }
    }
}

