package com.silcare.css.page

import android.net.Uri
import android.os.Build
import android.os.Environment
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.silcare.css.R
import com.silcare.css.api.MakamViewModel
import com.silcare.css.api.UploadRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileDialog(
    namaAwal: String,
    emailAwal: String,
    aksesAwal: String,
    imgUrl: String,
    onDismiss: () -> Unit,
    onLogout: () -> Unit,
    viewModel: MakamViewModel = viewModel()
) {
    val uploadRepository = UploadRepository()

    var nama by remember { mutableStateOf(namaAwal) }
    var email by remember { mutableStateOf(emailAwal) }
    var password by remember { mutableStateOf("") }
    var selectedImage by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val finalImage = selectedImage?.toString() ?: imgUrl

    val adaPerubahan =
        nama != namaAwal ||
                email != emailAwal ||
                password.isNotEmpty() ||
                selectedImage != null

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                ProfileImagePicker(
                    currentImageUrl = finalImage,
                    onImageSelected = { uri ->
                        selectedImage = uri
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Profil Kamu",
                    color = Color(0xFF38008B),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = nama,
                    onValueChange = { nama = it },
                    label = { Text("Nama") }
                )
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") }
                )
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = aksesAwal,
                    onValueChange = {},
                    enabled = false,
                    label = { Text("Akses") }
                )
            }
        },
        confirmButton = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5F5F5)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF38008B)),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    if (adaPerubahan) {
                        TextButton(
                            onClick = {
                                val uid = FirebaseAuth.getInstance().currentUser?.uid

                                CoroutineScope(Dispatchers.IO).launch {
                                    try {
                                        val imageUrl = selectedImage?.let { uploadRepository.uploadToCloudinary(context, it) }

                                        if (uid != null) {
                                            withContext(Dispatchers.Main) {
                                                viewModel.updateUserToFirestore(
                                                    uid,
                                                    nama,
                                                    email,
                                                    aksesAwal,
                                                    imageUrl,
                                                    onDone = {
                                                        Toast.makeText(context, "Profil berhasil diperbarui", Toast.LENGTH_SHORT).show()
                                                        onDismiss()
                                                    },
                                                    onError = { msg ->
                                                        Toast.makeText(context, "Error: $msg", Toast.LENGTH_SHORT).show()
                                                    }
                                                )
                                            }
                                        }
                                    } catch (e: Exception) {
                                        withContext(Dispatchers.Main) {
                                            Toast.makeText(context, "Gagal upload: ${e.message}", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            }
                        ) {
                            Text(
                                "Simpan",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }


                    TextButton(onClick = { onLogout() }) {
                        Text(
                            "Logout",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        },
        shape = RoundedCornerShape(20.dp),
        containerColor = Color.White
    )
    if (errorMessage != null) {
        AlertDialog(
            onDismissRequest = { errorMessage = null },
            confirmButton = {
                TextButton(onClick = { errorMessage = null }) {
                    Text("OK")
                }
            },
            title = { Text("Login Gagal") },
            text = { Text(errorMessage ?: "") }
        )
    }
}


@Composable
fun ProfileImagePicker(
    currentImageUrl: String?,
    onImageSelected: (Uri?) -> Unit
) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var tempPhotoUri by remember { mutableStateOf<Uri?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            imageUri = tempPhotoUri
            onImageSelected(tempPhotoUri)
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        imageUri = uri
        onImageSelected(uri)
    }

    val finalImage =
        imageUri ?: currentImageUrl?.let { Uri.parse(it) }

    Box(
        modifier = Modifier
            .size(110.dp)
            .clip(CircleShape)
            .background(Color(0xFFE0E0E0))
            .clickable { showDialog = true },
        contentAlignment = Alignment.Center
    ) {

        if (finalImage != null) {
            AsyncImage(
                model = finalImage,
                contentDescription = null,
                modifier = Modifier.matchParentSize().clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Black.copy(alpha = 0.25f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(26.dp)
                )
            }
        } else {
            Icon(
                Icons.Default.Person,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(50.dp)
            )
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Pilih Sumber Gambar") },
            confirmButton = {},
            text = {
                Column {
                    Text(
                        "Ambil dari Kamera",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showDialog = false

                                val photoFile = File(
                                    context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                                    "profile_${System.currentTimeMillis()}.jpg"
                                )

                                tempPhotoUri = FileProvider.getUriForFile(
                                    context,
                                    "${context.packageName}.provider",
                                    photoFile
                                )

                                cameraLauncher.launch(tempPhotoUri!!)
                            }
                            .padding(16.dp)
                    )

                    Divider()

                    Text(
                        "Pilih dari Galeri",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showDialog = false
                                galleryLauncher.launch("image/*")
                            }
                            .padding(16.dp)
                    )
                }
            }
        )
    }
}



//@Composable
//fun ProfileDialog(
//    namaAwal: String,
//    emailAwal: String,
//    aksesAwal: String,
//    imgUrl: String,
//    onDismiss: () -> Unit,
//    onLogout: () -> Unit,
//    onSave: (String, String, String) -> Unit
//) {
//    var nama by remember { mutableStateOf(namaAwal) }
//    var email by remember { mutableStateOf(emailAwal) }
//    var password by remember { mutableStateOf("") }
//
//    val adaPerubahan = nama != namaAwal || email != emailAwal || password.isNotEmpty()
//
//    AlertDialog(
//        onDismissRequest = { onDismiss() },
//        title = {
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                AsyncImage(
//                    model = imgUrl,
//                    contentDescription = "Foto Profil",
//                    modifier = Modifier
//                        .size(100.dp)
//                        .clip(CircleShape)
//                        .background(Color(0xFFE0E0E0)),
//                    contentScale = ContentScale.Crop
//                )
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                Text(
//                    text = "profil kamu",
//                    color = Color(0xFF38008B),
//                    fontSize = 20.sp,
//                    fontWeight = FontWeight.Bold
//                )
//            }
//        },
//        text = {
//            Column(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalAlignment = Alignment.Start
//            ) {
//                OutlinedTextField(
//                    value = nama,
//                    onValueChange = { nama = it },
//                    label = { Text("Nama") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//                OutlinedTextField(
//                    value = email,
//                    onValueChange = { email = it },
//                    label = { Text("Email") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//
//                Spacer(modifier = Modifier.height(8.dp))
//                OutlinedTextField(
//                    value = aksesAwal,
//                    onValueChange = {},
//                    enabled = false,
//                    label = { Text("Akses") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//            }
//        },
//        confirmButton = {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(Color(0xFFF5F5F5)),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Row(
//                    modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).background(Color(0xFF38008B)),
//                    horizontalArrangement = Arrangement.SpaceEvenly
//                ) {
//                    if (adaPerubahan) {
//                        TextButton(onClick = { onSave(nama, email, password) }) {
//                            Text(
//                                text = "Simpan",
//                                color = Color(0xFF38008B),
//                                fontWeight = FontWeight.Bold
//                            )
//                        }
//                    }
//
//                    TextButton(
//                        onClick = { onLogout() },
//                        shape = RoundedCornerShape(10.dp)
//                    ) {
//                        Text(
//                            text = "Logout",
//                            color = Color.White,
//                            fontWeight = FontWeight.Bold
//                        )
//                    }
//                }
//            }
//        },
//        shape = RoundedCornerShape(20.dp),
//        containerColor = Color.White
//    )
//}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun ProfileDialogPrev() {
    ProfileDialog(
        namaAwal = "-----",
        emailAwal = "-----",
        aksesAwal = "-----",
        onDismiss = {},
        onLogout = {},
        imgUrl = "",
        viewModel = MakamViewModel()
    )
}