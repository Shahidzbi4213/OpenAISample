package com.example.openaisample.ui.screen

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.example.openaisample.data.MainViewModel
import com.example.openaisample.data.NetworkResult


// Created by Shahid Iqbal on 2/14/2023.

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun MainScreen(viewModel: MainViewModel) {

    val apiData = viewModel.apiData.collectAsState().value

    var prompt by remember {
        mutableStateOf("")
    }
    var error by remember {
        mutableStateOf("")
    }
    var buttonState by remember {
        mutableStateOf(true)
    }
    var heighten by remember {
        mutableStateOf(true)
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val copy = LocalClipboardManager.current
   val context = LocalContext.current



    Column(modifier = Modifier.fillMaxSize()) {

        when (apiData) {
            NetworkResult.Empty -> {
                heighten = true
            }
            is NetworkResult.Error -> {
                Text(text = apiData.error, modifier = Modifier.weight(1f))
                buttonState = true
            }
            NetworkResult.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally).padding(
                    20.dp
                ),
                color = Color.Yellow)
                buttonState = false
                heighten = true
            }
            is NetworkResult.Success -> {
                val data = apiData.data.choices.first().text.trim()
                Text(
                    text = data,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp, end = 10.dp,top = 10.dp)
                        .verticalScroll(
                            rememberScrollState(0)
                        )
                        .combinedClickable(
                            onClick = {},
                            onLongClick = {
                                copy.setText(AnnotatedString(data))
                                    Toast
                                        .makeText(
                                           context ,
                                            "Copied",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()

                            }
                        )


                )
                buttonState = true
                heighten = false
            }

        }

        if (heighten) Spacer(modifier = Modifier.weight(1f))

        Spacer(modifier = Modifier.height(30.dp))

        TextField(
            value = prompt,
            onValueChange = {
                prompt = it
                error = ""
            },
            placeholder = { Text(text = "Type your Request") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            enabled = buttonState
        )
        if (error.isNotBlank())
            Text(
                text = error, color = Color.Red, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)
            )

        Spacer(modifier = Modifier.height(5.dp))
        Button(
            onClick = {
                if (prompt.isNotBlank())
                    viewModel.sendRequest(prompt)
                else error = "Field is Empty"

                keyboardController?.hide()
            },
            enabled = buttonState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
        ) {
            Text(text = "Send")
        }

        Spacer(modifier = Modifier.height(10.dp))

    }
}