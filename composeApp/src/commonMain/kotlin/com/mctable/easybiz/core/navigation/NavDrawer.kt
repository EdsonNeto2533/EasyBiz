package com.mctable.easybiz.core.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mctable.easybiz.core.ds.utils.AppIcons
import com.mctable.easybiz.core.helpers.ObserveAsEvents
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

val userChannel: Channel<String> = Channel()

@Composable
fun NavDrawer(
    drawerState: DrawerState,
    currentDestination: Destination,
    onDestinationClicked: (Destination) -> Unit,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    var userName by remember {
        mutableStateOf("")
    }
    ObserveAsEvents(userChannel.receiveAsFlow()) { action ->
        userName = action
    }
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = currentDestination.isLoggedArea,
        drawerContent = {
            ModalDrawerSheet {
                if (currentDestination.isLoggedArea) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(painter = AppIcons.accountCircle(), contentDescription = null)
                            Column(
                                modifier = Modifier.padding(start = 12.dp)
                            ) {
                                Text(userName, style = MaterialTheme.typography.titleMedium)
                                Text("Cliente", style = MaterialTheme.typography.bodySmall)
                            }
                        }

                        Spacer(Modifier.height(24.dp))
                        HorizontalDivider()
                        Spacer(Modifier.height(12.dp))

                        Destination.drawerDestinations.forEach { destination ->
                            NavigationDrawerItem(
                                label = { Text(text = destination.title ?: "") },
                                icon = {
                                    Icon(
                                        painter = Destination.getIcon(destination),
                                        contentDescription = null
                                    )
                                },
                                selected = currentDestination::class == destination::class,
                                onClick = {
                                    scope.launch { drawerState.close() }
                                    onDestinationClicked(destination)
                                },
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                        }
                    }
                }
            }

        },
        content = content
    )
}
