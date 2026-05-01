package com.mctable.easybiz.features.user_data.presentation.ui.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mctable.easybiz.features.user_data.presentation.event.UserDataEvent
import com.mctable.easybiz.features.user_data.presentation.state.UserDataState

@Composable
fun UserDataPage(
    state: UserDataState,
    onEvent: (UserDataEvent) -> Unit
) {
    LaunchedEffect(Unit) {
        onEvent(UserDataEvent.LoadUserData)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "User Data Page")
    }
}
