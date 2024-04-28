package com.eep.dam.android.aplicacion_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.eep.dam.android.aplicacion_android.ui.theme.Aplicacion_androidTheme
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private val retrofit = Retrofit.Builder()
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Aplicacion_androidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("EPP")
                    val retrofit = Retrofit.Builder()
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .baseUrl("http://127.0.0.1:8080")
                        .build()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Alumnos $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Aplicacion_androidTheme {
        Greeting("Android")
    }
}