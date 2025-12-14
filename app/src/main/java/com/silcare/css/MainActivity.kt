package com.silcare.css

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.silcare.css.Component.FloatingActionButton.FloatingActionButtonCustom
import com.silcare.css.Component.bottomAppBar.BottomBarMain
import com.silcare.css.Component.bottomAppBar.BottomBarUserMain
import com.silcare.css.PopUp.AlertToLogin
import com.silcare.css.api.MakamViewModel
import com.silcare.css.page.BeritaPage
import com.silcare.css.page.DatabasePage
import com.silcare.css.page.HomePage
import com.silcare.css.page.NoMakamPage
import com.silcare.css.page.PageAddBlock
import com.silcare.css.page.PageNotifikasi
import com.silcare.css.page.ProfileDialog
import com.silcare.css.page.berita.UploadBeritaActivity
import com.silcare.css.page.loginDanRegistrasi.LoginAndRegisActivity
import com.silcare.css.page.pageAjuan.AjuanActivity

class MainActivity : ComponentActivity() {
    private lateinit var authPrefs: AuthPreferences
    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (!granted) {
                Log.e("Permission", "CAMERA permission ditolak")
            }
        }

    @OptIn(ExperimentalAnimationApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        permissionLauncher.launch(Manifest.permission.CAMERA)
        authPrefs = AuthPreferences(this)
        setContent {
            val navController = rememberNavController()
            var showDialog by remember { mutableStateOf(false) }
            var akses by remember { mutableStateOf<String?>(null) }
            var nama by remember { mutableStateOf<String?>(null) }
            var email by remember { mutableStateOf<String?>(null) }
            var imgUrl by remember { mutableStateOf<String?>(null) }
            var showProfile by remember { mutableStateOf(false) }
            var mustLogin by remember { mutableStateOf(false) }
            var direction by remember { mutableStateOf(0) }

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    100
                )
            }
            LaunchedEffect(Unit, showProfile) {
                val user = FirebaseAuth.getInstance().currentUser
                if (user != null) {
                    FirebaseFirestore.getInstance()
                        .collection("user")
                        .document(user.uid)
                        .get()
                        .addOnSuccessListener { doc ->
                            akses = doc.getString("akses")
                            nama = doc.getString("nama")
                            email = doc.getString("email")
                            imgUrl = doc.getString("profil")
                        }
                        .addOnFailureListener {
                            Log.e("MainActivity", "❌ Gagal ambil role user", it)
                        }
                }
            }
            if (akses == "admin") {
                Scaffold(
                    contentWindowInsets = WindowInsets.safeDrawing,
                    modifier = Modifier.fillMaxSize(),
                    content = { innerPadding ->
                        AnimatedNavHost(
                            navController = navController,
                            startDestination = "home",
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable(
                                "home",
                                enterTransition = { slideInHorizontally { full -> full * direction } },
                                exitTransition = { slideOutHorizontally { full -> -full * direction } },
                                popEnterTransition = { slideInHorizontally { full -> -full * direction } },
                                popExitTransition = { slideOutHorizontally { full -> full * direction } }
                            ) {
                                HomePage(
                                    navController = navController,
                                    uid = FirebaseAuth.getInstance().currentUser?.uid,
                                    onProfile = {
                                        Log.d(
                                            "get UID TRUE or FALSE",
                                            ((FirebaseAuth.getInstance().currentUser?.uid != null).toString())
                                        )
                                        if (FirebaseAuth.getInstance().currentUser?.uid != null) {
                                            showProfile = true
                                        } else {
                                            mustLogin = true
                                        }
                                    }
                                )
                            }
                            composable(
                                "database",
                                enterTransition = { slideInHorizontally { full -> full * direction } },
                                exitTransition = { slideOutHorizontally { full -> -full * direction } }
                            ) {
                                DatabasePage(
                                    onProfile = { showProfile = true },
                                )
                            }
                            composable(
                                "notifikasi",
                                enterTransition = { slideInHorizontally { full -> full * direction } },
                                exitTransition = { slideOutHorizontally { full -> -full * direction } }
                            ) { PageNotifikasi() }
                            composable(
                                "noMakamPage/{id}",
                                enterTransition = { slideInHorizontally { full -> full * direction } },
                                exitTransition = { slideOutHorizontally { full -> -full * direction } }
                            ) { backStackEntry ->
                                val id = backStackEntry.arguments?.getString("id") ?: "-----"
                                NoMakamPage(
                                    blokId = id,
                                    viewModel = MakamViewModel(),
                                    navController = navController,
                                    onProfile = { showProfile = true }
                                )
                            }
                        }
                        if (showDialog) {
                            PageAddBlock(
                                viewModel = MakamViewModel(),
                                onDismiss = {
                                    showDialog = false
                                }
                            )
                        }
                        if (showProfile) {
                            ProfileDialog(
                                namaAwal = nama ?: "-----",
                                emailAwal = email ?: "-----",
                                aksesAwal = akses ?: "-----",
                                imgUrl = imgUrl ?: "",
                                onDismiss = { showProfile = false },
                                onLogout = {
                                    FirebaseAuth.getInstance().signOut()
                                    val intent = Intent(this, LoginAndRegisActivity::class.java)
                                    this.startActivity(intent)
                                    showProfile = false
                                }
                            )
                        }
                        if (mustLogin) {
                            AlertToLogin(
                                onClick = {
                                    val intent = Intent(this, LoginAndRegisActivity::class.java)
                                    this.startActivity(intent)
                                    mustLogin = false
                                }
                            )
                        }
                    },
                    bottomBar = {
                        BottomBarMain(
                            navController = navController,
                            onDirectionChange = { dir -> direction = dir }
                        )
                    },
                    floatingActionButton = {
                        FloatingActionButtonCustom(
                            onClikAddJenazah = {
                                val intent = Intent(this, AjuanActivity::class.java)
                                this.startActivity(intent)
                            },
                            onClikAddBerita = {
                                val intent = Intent(this, UploadBeritaActivity::class.java)
                                this.startActivity(intent)
                            },
                            onClikAddKuburan = {
                                showDialog = true
                            }
                        )
                    }
                )
            } else {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    content = { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = "home",
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable("home") {
                                HomePage(
                                    navController = navController,
                                    uid = FirebaseAuth.getInstance().currentUser?.uid,
                                    onProfile = {
                                        Log.d(
                                            "get UID TRUE or FALSE",
                                            ((FirebaseAuth.getInstance().currentUser?.uid != null).toString())
                                        )
                                        if (FirebaseAuth.getInstance().currentUser?.uid != null) {
                                            showProfile = true
                                        } else {
                                            mustLogin = true
                                        }
                                    }
                                )
                            }
                            composable("berita") { BeritaPage() }
                            composable("noMakamPage/{id}") { backStackEntry ->
                                val id = backStackEntry.arguments?.getString("id") ?: "-----"
                                NoMakamPage(
                                    blokId = id,
                                    viewModel = MakamViewModel(),
                                    navController = navController,
                                    onProfile = { showProfile = true }
                                )
                            }
                        }
                        if (showProfile) {
                            ProfileDialog(
                                namaAwal = nama ?: "-----",
                                emailAwal = email ?: "-----",
                                aksesAwal = akses ?: "-----",
                                imgUrl = imgUrl ?: "",
                                onDismiss = { showProfile = false },
                                onLogout = {
                                    FirebaseAuth.getInstance().signOut()
                                    val intent = Intent(this, LoginAndRegisActivity::class.java)
                                    this.startActivity(intent)
                                    showProfile = false
                                }
                            )
                        }
                        if (mustLogin) {
                            AlertToLogin(
                                onDismis = {
                                    mustLogin = false
                                },
                                onClick = {
                                    val intent = Intent(this, LoginAndRegisActivity::class.java)
                                    this.startActivity(intent)
                                    mustLogin = false
                                }
                            )
                        }
                    },
                    bottomBar = {
                        BottomBarUserMain(
                            navController = navController
                        )
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                if (FirebaseAuth.getInstance().currentUser?.uid != null) {
                                    val intent = Intent(this, AjuanActivity::class.java)
                                    this.startActivity(intent)
                                } else {
                                    mustLogin = true
                                }
                            },
                            shape = CircleShape,
                            containerColor = Color(0xFFF3EDF7),
                            contentColor = Color(0xFF38008B),
                            content = {
                                Icon(Icons.Default.Add, contentDescription = "Add")
                            }
                        )
                    }
                )
            }
        }
    }
}