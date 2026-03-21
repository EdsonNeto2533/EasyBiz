package com.mctable.easybiz.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.mctable.easybiz.core.ds.utils.AppIcons
import kotlinx.serialization.Serializable

@Serializable
sealed interface Destination {
    val isLoggedArea: Boolean
    val title: String? get() = null

    @Serializable
    data object Login : Destination {
        override val isLoggedArea = false
    }

    @Serializable
    data object SearchBusiness : Destination {
        override val isLoggedArea = true
        override val title = "Buscar prestadores"
    }

    @Serializable
    data class BusinessDetails(val id: Int) : Destination {
        override val isLoggedArea = true
    }

    @Serializable
    data object RegisterBusiness : Destination {
        override val isLoggedArea = true
        override val title = "Cadastrar negócio"
    }

    @Serializable
    data class UpdateBusiness(val id: Int) : Destination {
        override val isLoggedArea = true
    }

    companion object {
        @Composable
        fun getIcon(destination: Destination): Painter {
            return when (destination) {
                is SearchBusiness -> AppIcons.accountCircle()
                else -> AppIcons.arrowBack()
            }
        }

        fun fromRoute(route: String?): Destination {
            return when {
                route?.contains("SearchBusiness") == true -> SearchBusiness
                route?.contains("RegisterBusiness") == true -> RegisterBusiness
                else -> Login
            }
        }

        val drawerDestinations = listOf<Destination>(
            SearchBusiness,
            RegisterBusiness
        )
    }
}
