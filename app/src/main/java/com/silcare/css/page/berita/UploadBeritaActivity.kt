package com.silcare.css.page.berita

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.google.firebase.firestore.FirebaseFirestore
import com.silcare.css.R
import com.silcare.css.api.UploadRepository
import kotlinx.coroutines.launch

class UploadBeritaActivity : ComponentActivity() {

    private var titleState: String = ""
    private var descState: String = ""
    private var imageUriState: Uri? = null

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val context = this
            val uploadRepository = UploadRepository()
            val db = FirebaseFirestore.getInstance()

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                content = { innerPadding ->
                    PostingBerita(
                        modifier = Modifier.padding(innerPadding),
                        uploadRepository = uploadRepository
                    ) { t, d, uri ->
                        titleState = t
                        descState = d
                        imageUriState = uri
                    }
                },
                topBar = {
                    TopAppBar(
                        title = {},
                        navigationIcon = {
                            IconButton(onClick = { finish() }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.iconback),
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        },
                        actions = {
                            TextButton(
                                onClick = {
                                    if (titleState.isNotEmpty() && descState.isNotEmpty() && imageUriState != null) {
                                        val scope = lifecycleScope
                                        scope.launch {
                                            val imageUrl = uploadRepository.uploadToCloudinary(context, imageUriState!!)
                                            if (imageUrl != null) {
                                                val data = hashMapOf(
                                                    "title" to titleState,
                                                    "desc" to descState,
                                                    "img_url" to imageUrl
                                                )
                                                db.collection("berita")
                                                    .add(data)
                                                    .addOnSuccessListener {
                                                        Toast.makeText(context, "Berita berhasil diupload", Toast.LENGTH_SHORT).show()
                                                        finish()
                                                    }
                                                    .addOnFailureListener {
                                                        Toast.makeText(context, "Gagal menyimpan data", Toast.LENGTH_SHORT).show()
                                                    }
                                            } else {
                                                Toast.makeText(context, "Upload gambar gagal", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    } else {
                                        Toast.makeText(context, "Lengkapi semua field!", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            ) {
                                Text(text = "Upload")
                            }
                        }
                    )
                }
            )
        }
    }
}
