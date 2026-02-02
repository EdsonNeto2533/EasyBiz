package com.mctable.easybiz.core.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mctable.easybiz.core.ds.components.atoms.ButtonAtom
import org.koin.compose.getKoin

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    val navigator = getKoin()
        .get<Navigator>() as NavigatorImpl

    SideEffect {
        navigator.attach(navController)
    }

    NavHost(
        navController = navController,
        startDestination = Destination.Login.route
    ) {
        composable(Destination.Login.route) {
            Scaffold() {
                Column(
                    modifier = Modifier.padding(it),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("oi")
                    ButtonAtom("ola") {}
                }
            }
        }
    }
}
