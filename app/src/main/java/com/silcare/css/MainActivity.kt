package com.silcare.css

import android.content.Intent
import android.os.Build
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.silcare.css.Component.FloatingActionButton.FloatingActionButtonCustom
import com.silcare.css.Component.bottomAppBar.BottomBarMain
import com.silcare.css.api.MakamViewModel
import com.silcare.css.page.DatabasePage
import com.silcare.css.page.HomePage
import com.silcare.css.page.NoMakamPage
import com.silcare.css.page.PageNotifikasi
import com.silcare.css.page.pageAjuan.AjuanActivity

class MainActivity : ComponentActivity() {
    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (!granted) {
                Log.e("Permission", "CAMERA permission ditolak")
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        permissionLauncher.launch(Manifest.permission.CAMERA)
        setContent {
            val navController = rememberNavController()
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    100
                )
            }
            Scaffold(
                contentWindowInsets = WindowInsets.safeDrawing,
                modifier = Modifier.fillMaxSize(),
                content = { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home") { HomePage(navController = navController) }
                        composable("database") { DatabasePage() }
                        composable("notifikasi") { PageNotifikasi() }
                        composable("noMakamPage/{id}") { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id") ?: "-----"
                            NoMakamPage(
                                blokId = id,
                                viewModel = MakamViewModel(),
                                navController = navController
                            )
                        }
                    }
                },
                bottomBar = {
                    BottomBarMain(
                        navController = navController
                    )
                },
                floatingActionButton = {
                    FloatingActionButtonCustom(
                        onClikAddJenazah = {
                            val intent = Intent(this, AjuanActivity::class.java)
                            startActivity(intent)
                        },
                        onClikAddBerita = {},
                        onClikAddKuburan = {}
                    )
                }
            )
        }
    }
}