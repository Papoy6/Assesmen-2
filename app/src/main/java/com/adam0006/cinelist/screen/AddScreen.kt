package com.adam0006.cinelist.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.adam0006.cinelist.R
import com.adam0006.cinelist.database.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(navController: NavHostController, viewModel: MainViewModel) {
    var judul by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("") }
    var tahun by remember { mutableStateOf("") }
    var sudahDitonton by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    val genreOptions = listOf("Action", "Comedy", "Drama", "Horror", "Sci-Fi", "Romance", "Thriller")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.tambah_film)) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = judul,
                onValueChange = { judul = it },
                label = { Text("Judul Film") },
                modifier = Modifier.fillMaxWidth()
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = genre,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Genre") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor(MenuAnchorType.PrimaryEditable, true)
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    genreOptions.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                genre = selectionOption
                                expanded = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = tahun,
                onValueChange = { if (it.all { char -> char.isDigit() }) tahun = it },
                label = { Text("Tahun Rilis") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Sudah Ditonton", style = MaterialTheme.typography.bodyLarge)
                Switch(
                    checked = sudahDitonton,
                    onCheckedChange = { sudahDitonton = it }
                )
            }

            Button(
                onClick = {
                    if (judul.isNotBlank() && genre.isNotBlank() && tahun.isNotBlank()) {
                        viewModel.tambahFilm(judul, genre, tahun, sudahDitonton)
                        navController.navigateUp()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = judul.isNotBlank() && genre.isNotBlank() && tahun.isNotBlank()
            ) {
                Text(stringResource(R.string.tombol_simpan))
            }
        }
    }
}
