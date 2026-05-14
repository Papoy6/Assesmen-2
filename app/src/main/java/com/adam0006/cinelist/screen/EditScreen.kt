package com.adam0006.cinelist.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.adam0006.cinelist.database.MainViewModel
import com.adam0006.cinelist.database.Film

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    navController: NavHostController,
    viewModel: MainViewModel,
    idFilm: Int
) {
    var judul by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("") }
    var tahun by remember { mutableStateOf("") }
    var filmAsli by remember { mutableStateOf<Film?>(null) }

    LaunchedEffect(idFilm) {
        val data = viewModel.getFilmById(idFilm)
        data?.let {
            filmAsli = it
            judul = it.judul
            genre = it.genre
            tahun = it.tahun
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Film") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            OutlinedTextField(value = judul, onValueChange = { judul = it }, label = { Text("Judul") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = genre, onValueChange = { genre = it }, label = { Text("Genre") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = tahun, onValueChange = { tahun = it }, label = { Text("Tahun") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    filmAsli?.let {
                        viewModel.editFilm(it.copy(judul = judul, genre = genre, tahun = tahun))
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Simpan Perubahan")
            }
        }
    }
}