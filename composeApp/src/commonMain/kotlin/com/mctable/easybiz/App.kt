package com.mctable.easybiz

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mctable.easybiz.core.ds.theme.EasyBizTheme
import com.mctable.easybiz.core.navigation.AppNavHost

@Composable
@Preview
fun App() {
    EasyBizTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding() // ✅ multiplatform-safe
        ) {
            AppNavHost()
        }

    }
}