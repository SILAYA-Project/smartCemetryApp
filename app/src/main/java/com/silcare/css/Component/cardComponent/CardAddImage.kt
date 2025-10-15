package com.silcare.css.Component.cardComponent

import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import com.silcare.css.Component.textFieldCustom.CompCustomTextField
import com.silcare.css.R
import java.io.File

@Composable
fun CardAddImage(
    width: Int = 120,
    desc: String = "",
    title: String = "",
    onImageSelected: (Uri?) -> Unit = {}
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

    Box(contentAlignment = Alignment.CenterStart) {
        Box(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color(0x6138008b),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(15.dp)
                .width(width.dp)
                .clickable { showDialog = true },
            contentAlignment = Alignment.Center
        ) {
            if (imageUri == null) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = desc,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFB8B8B8),
                        modifier = Modifier.weight(1f)
                    )
                    Image(
                        painter = painterResource(R.drawable.iconupload),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.size(20.dp)
                    )
                }
            } else {
                Box {
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 200.dp)
                            .border(1.dp, Color(0x6138008b), RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Crop
                    )
                    IconButton(
                        onClick = {
                            imageUri = null
                            onImageSelected(null) // 👈 reset juga
                        },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .size(28.dp)
                            .background(
                                Color.Black.copy(alpha = 0.5f),
                                shape = MaterialTheme.shapes.small
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Remove",
                            tint = Color.White
                        )
                    }
                }
            }
        }

        CompCustomTextField(
            title = title,
            modifier = Modifier.offset(x = 5.dp, y = (-35).dp),
            backgroundColor = Color.White,
            textColor = Color(0x6138008b)
        )
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Pilih Sumber Gambar") },
            confirmButton = {},
            text = {
                Column {
                    Text(
                        "Ambil Foto",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showDialog = false
                                try {
                                    val photoFile = File(
                                        context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                                        "photo_${System.currentTimeMillis()}.jpg"
                                    )
                                    tempPhotoUri = FileProvider.getUriForFile(
                                        context,
                                        "${context.packageName}.provider",
                                        photoFile
                                    )
                                    cameraLauncher.launch(tempPhotoUri!!)
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                    tempPhotoUri = null
                                }
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

@Preview(showBackground = true)
@Composable
private fun ImageBoxPrev() {
    CardAddImage(title = "KTP ahli waris", desc = "Upload KTP Ahli Waris")
}