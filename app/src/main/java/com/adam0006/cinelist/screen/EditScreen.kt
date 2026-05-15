package com.adam0006.cinelist.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.adam0006.cinelist.database.MainViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun EditScreen(
    navController: NavHostController,
    viewModel: MainViewModel,
    idFilm: Int
) {
    val filmFlow = remember(idFilm) { viewModel.getFilmById(idFilm) }
    val filmAsli by filmFlow.collectAsState(initial = null)

    var judul by remember { mutableStateOf("") }
    val selectedGenres = remember { mutableStateListOf<String>() }
    var tahun by remember { mutableStateOf("") }
    var sudahDitonton by remember { mutableStateOf(false) }
    var rating by remember { mutableFloatStateOf(0f) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var isFavorite by remember { mutableStateOf(false) }

    val genreOptions = listOf("Action", "Adventure", "Animation", "Comedy", "Crime", "Documentary", "Drama", "Family", "Fantasy", "History", "Horror", "Kids", "Music", "Mystery", "Romance", "Science Fiction", "TV Movie", "Thriller", "War", "Western")

    LaunchedEffect(filmAsli) {
        filmAsli?.let {
            judul = it.judul
            tahun = it.tahun
            sudahDitonton = it.sudahDitonton
            rating = it.rating
            imageUri = it.imageUri?.toUri()
            isFavorite = it.isFavorite
            
            selectedGenres.clear()
            if (it.genre.isNotBlank()) {
                selectedGenres.addAll(it.genre.split(", ").map { g -> g.trim() })
            }
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Film") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali"
                        )
                    }
                }
            )
        }
    ) { padding ->
        if (filmAsli == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Upload Gambar
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clickable { launcher.launch("image/*") },
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    if (imageUri != null) {
                        AsyncImage(
                            model = imageUri,
                            contentDescription = "Film Poster",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Default.AddPhotoAlternate, contentDescription = null, modifier = Modifier.size(48.dp))
                                Text("Tambah Gambar (Opsional)")
                            }
                        }
                    }
                }

                OutlinedTextField(
                    value = judul,
                    onValueChange = { judul = it },
                    label = { Text("Judul Film") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Multiple Genre Selection
                Text("Pilih Genre", fontWeight = FontWeight.Bold)
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    genreOptions.forEach { genre ->
                        val isSelected = selectedGenres.contains(genre)
                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                if (isSelected) selectedGenres.remove(genre)
                                else selectedGenres.add(genre)
                            },
                            label = { Text(genre) }
                        )
                    }
                }

                OutlinedTextField(
                    value = tahun,
                    onValueChange = { if (it.all { char -> char.isDigit() }) tahun = it },
                    label = { Text("Tahun Rilis") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                // Rating
                Column {
                    Text("Rating", fontWeight = FontWeight.Bold)
                    Row {
                        (1..5).forEach { index ->
                            Icon(
                                imageVector = if (index <= rating) Icons.Default.Star else Icons.Default.StarBorder,
                                contentDescription = null,
                                tint = if (index <= rating) Color(0xFFFFC107) else Color.Gray,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clickable { rating = index.toFloat() }
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Tambah ke Favorit", style = MaterialTheme.typography.bodyLarge)
                    Switch(
                        checked = isFavorite,
                        onCheckedChange = { isFavorite = it }
                    )
                }

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
                        filmAsli?.let {
                            viewModel.editFilm(it.copy(
                                judul = judul,
                                genre = selectedGenres.joinToString(", "),
                                tahun = tahun,
                                sudahDitonton = sudahDitonton,
                                rating = rating,
                                imageUri = imageUri?.toString(),
                                isFavorite = isFavorite
                            ))
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = judul.isNotBlank() && selectedGenres.isNotEmpty() && tahun.isNotBlank()
                ) {
                    Text("Simpan Perubahan")
                }
            }
        }
    }
}
