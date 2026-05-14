package com.adam0006.cinelist.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.adam0006.cinelist.R
import com.adam0006.cinelist.database.Film
import com.adam0006.miniproject.database.MainViewModel
import com.adam0006.cinelist.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController, viewModel: MainViewModel) {
    // Membaca data dari database (Modul 09)
    val listFilm by viewModel.dataFilm.collectAsState(initial = emptyList())

    // State untuk Dialog (Modul 10)
    var showDialog by remember { mutableStateOf(false) }

    // State untuk input data film baru
    var judul by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("") }
    var tahun by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CineList", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.Settings.route) }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Film")
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            if (listFilm.isEmpty()) {
                // Tampilan saat data kosong (Modul 07)
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(stringResource(R.string.list_kosong))
                }
            } else {
                // Scrollable List (Modul 07)
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(listFilm) { film ->
                        ListItem(film = film) {
                            // Navigasi ke Detail (Modul 08)
                            navController.navigate(Screen.Detail.withId(film.id))
                        }
                        HorizontalDivider()
                    }
                }
            }
        }

        // --- LOGIKA DIALOG TAMBAH FILM (Modul 10) ---
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(stringResource(R.string.tambah_film)) },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = judul,
                            onValueChange = { judul = it },
                            label = { Text("Judul Film") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = genre,
                            onValueChange = { genre = it },
                            label = { Text("Genre") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = tahun,
                            onValueChange = { tahun = it },
                            label = { Text("Tahun Rilis") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (judul.isNotBlank()) {
                                viewModel.tambahFilm(judul, genre, tahun)
                                // Reset input dan tutup dialog
                                judul = ""; genre = ""; tahun = ""
                                showDialog = false
                            }
                        }
                    ) {
                        Text(stringResource(R.string.tombol_simpan))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text(stringResource(R.string.tombol_batal))
                    }
                }
            )
        }
    }
}

// Komponen Helper untuk Item List (Modul 07)
@Composable
fun ListItem(film: Film, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Text(
            text = film.judul,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "${film.genre} • ${film.tahun}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}