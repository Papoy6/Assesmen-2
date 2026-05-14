package com.adam0006.cinelist.screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController, viewModel: MainViewModel) {
    val listFilm by viewModel.dataFilm.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("CineList") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    ) { innerPadding ->
        if (listFilm.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(innerPadding), Alignment.Center) {
                Text(stringResource(R.string.list_kosong))
            }
        } else {
            LazyColumn(Modifier.padding(innerPadding)) {
                items(listFilm) { film ->
                    ListItem(film) {
                        navController.navigate(Screen.Detail.withId(film.id))
                    }
                    HorizontalDivider()
                }
            }
        }
        // Logika Dialog Tambah diletakkan di sini (menggunakan AlertDialog) [cite: 1961]
    }
}