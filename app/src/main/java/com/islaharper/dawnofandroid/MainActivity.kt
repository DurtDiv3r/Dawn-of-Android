package com.islaharper.dawnofandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import com.islaharper.dawnofandroid.ui.theme.DawnOfAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DawnOfAndroidTheme {
                Text(text = "Main Activity")
            }
        }
    }
}
