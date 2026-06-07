package com.mctable.easybiz

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mctable.easybiz.core.notification.DeeplinkResolver
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val deeplinkResolver: DeeplinkResolver by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        handleNotificationIntent(intent)
        setContent {
            App()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleNotificationIntent(intent)
    }

    private fun handleNotificationIntent(intent: Intent?) {
        val extras = intent?.extras ?: return
        if (!extras.containsKey("type")) return
        val data = extras.keySet().associateWith { extras.getString(it) }
        deeplinkResolver.resolve(data)
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}