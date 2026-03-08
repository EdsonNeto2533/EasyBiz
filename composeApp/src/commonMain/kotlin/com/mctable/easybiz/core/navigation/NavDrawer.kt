package com.mctable.easybiz.core.navigation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun NavDrawer(
    drawerState: DrawerState,
    currentDestination: Destination,
    onDestinationClicked: (Destination) -> Unit,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = currentDestination.isLoggedArea,
        drawerContent = {
            ModalDrawerSheet {
                if (currentDestination.isLoggedArea) {
                    Spacer(Modifier.height(12.dp))
                    Destination.drawerDestinations.forEach { destination ->
                        NavigationDrawerItem(
                            label = { Text(text = destination.title ?: destination.route) },
                            selected = currentDestination == destination,
                            onClick = {
                                scope.launch { drawerState.close() }
                                onDestinationClicked(destination)
                            },
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )
                    }
                }
            }

        },
        content = content
    )
}
