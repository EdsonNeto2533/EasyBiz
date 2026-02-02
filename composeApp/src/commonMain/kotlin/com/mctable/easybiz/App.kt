package com.mctable.easybiz

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mctable.easybiz.core.ds.theme.EasyBizTheme
import com.mctable.easybiz.core.navigation.AppNavHost

@Composable
@Preview
fun App() {
    EasyBizTheme {
        AppNavHost()
    }
}