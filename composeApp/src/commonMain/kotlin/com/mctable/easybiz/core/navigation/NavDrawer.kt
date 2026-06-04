package com.mctable.easybiz.core.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
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
import com.mctable.easybiz.core.ds.components.atoms.AvatarAtom
import com.mctable.easybiz.core.ds.theme.Dimens
import com.mctable.easybiz.core.ds.utils.AppIcons
import com.mctable.easybiz.core.helpers.ObserveAsEvents
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

data class UserData(
    val id: String,
    val name: String?,
    val email: String,
    val photoUrl: String?
)
val userChannel: Channel<UserData> = Channel()

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
    var userImage by remember {
        mutableStateOf<String?>(null)
    }
    ObserveAsEvents(userChannel.receiveAsFlow()) { action ->
        userName = action.name ?: ""
        userImage = action.photoUrl
    }
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = currentDestination.isLoggedArea,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.surface
            ) {
                if (currentDestination.isLoggedArea) {
                    Column(modifier = Modifier.padding(Dimens.screenPaddingHorizontal)) {

                        Spacer(Modifier.height(Dimens.spacingXxl))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(Dimens.spacingMd))
                                .clickable {
                                    scope.launch { drawerState.close() }
                                    onDestinationClicked(Destination.Profile)
                                }
                                .padding(Dimens.spacingSm)
                        ) {
                            AvatarAtom(
                                imageUrl = userImage,
                                contentDescription = userName,
                                size = Dimens.avatarSizeMd
                            )
                            Spacer(Modifier.width(Dimens.spacingMd))
                            Column {
                                Text(
                                    userName,
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(Modifier.height(Dimens.spacingXxs))
                                Text(
                                    "Editar perfil",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        Spacer(Modifier.height(Dimens.spacingXxl))
                        HorizontalDivider(
                            thickness = Dimens.dividerThickness,
                            color = MaterialTheme.colorScheme.outlineVariant
                        )
                        Spacer(Modifier.height(Dimens.spacingMd))

                        Destination.drawerDestinations.forEach { destination ->
                            NavigationDrawerItem(
                                label = {
                                    Text(
                                        text = destination.title ?: "",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                },
                                icon = {
                                    Icon(
                                        painter = Destination.getIcon(destination),
                                        contentDescription = null,
                                        tint = if (currentDestination::class == destination::class)
                                            MaterialTheme.colorScheme.primary
                                        else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                },
                                selected = currentDestination::class == destination::class,
                                onClick = {
                                    scope.launch { drawerState.close() }
                                    onDestinationClicked(destination)
                                },
                                colors = NavigationDrawerItemDefaults.colors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                                    selectedTextColor = MaterialTheme.colorScheme.primary,
                                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                                ),
                                shape = MaterialTheme.shapes.medium,
                                modifier = Modifier.padding(vertical = Dimens.spacingXxs)
                            )
                        }
                    }
                }
            }

        },
        content = content
    )
}
