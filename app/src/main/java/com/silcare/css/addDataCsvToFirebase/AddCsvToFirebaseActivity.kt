package com.silcare.css.addDataCsvToFirebase

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
import com.silcare.css.addDataCsvToFirebase.ui.theme.CSSTheme

class AddCsvToFirebaseActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val importer = CsvImporter(this)
        importer.importCsv("data.csv") { success ->
            if (success) {
                println("Import selesai")
            } else {
                println("Import gagal")
            }
        }
    }
}