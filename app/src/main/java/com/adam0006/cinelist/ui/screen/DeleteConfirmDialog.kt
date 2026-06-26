package com.adam0006.cinelist.ui.screen

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.adam0006.cinelist.model.Film
import com.adam0006.cinelist.R

@Composable
fun DeleteConfirmDialog(
    film: Film,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    AlertDialog(
        title = { Text(text = stringResource(R.string.hapus_judul)) },
        text = { Text(text = stringResource(R.string.hapus_konfirmasi, film.judul)) },
        confirmButton = {
            TextButton(onClick = { onConfirmation() }) {
                Text(
                    text = stringResource(R.string.hapus),
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(text = stringResource(R.string.batal))
            }
        },
        onDismissRequest = { onDismissRequest() }
    )
}