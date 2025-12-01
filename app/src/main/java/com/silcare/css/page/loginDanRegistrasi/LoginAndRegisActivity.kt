package com.silcare.css.page.loginDanRegistrasi

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
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
import com.silcare.css.api.MakamViewModel
import com.silcare.css.page.loginDanRegistrasi.ui.theme.CSSTheme

class LoginAndRegisActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = "login"
            ) {
                composable("login") {
                    LoginPage(navController = navController, viewModel = MakamViewModel())
                }
                composable("registrasi") {
                    RegisterPage(navController = navController, viewModel = MakamViewModel())
                }
            }
        }
    }
}

