package com.mctable.easybiz.core.ds.utils


import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import easybiz.composeapp.generated.resources.Res
import easybiz.composeapp.generated.resources.account_circle
import easybiz.composeapp.generated.resources.arrow_back
import easybiz.composeapp.generated.resources.contact_mail
import easybiz.composeapp.generated.resources.location_on
import easybiz.composeapp.generated.resources.menu
import easybiz.composeapp.generated.resources.search
import easybiz.composeapp.generated.resources.star
import easybiz.composeapp.generated.resources.visibility_off
import easybiz.composeapp.generated.resources.visibility_on
import org.jetbrains.compose.resources.painterResource

object AppIcons {

    @Composable
    fun contactEmail(): Painter {
        return painterResource(Res.drawable.contact_mail)
    }

    @Composable
    fun visibilityOff(): Painter {
        return painterResource(Res.drawable.visibility_off)
    }

    @Composable
    fun visibilityOn(): Painter {
        return painterResource(Res.drawable.visibility_on)
    }

    @Composable
    fun accountCircle(): Painter {
        return painterResource(Res.drawable.account_circle)
    }

    @Composable
    fun arrowBack(): Painter {
        return painterResource(Res.drawable.arrow_back)
    }

    @Composable
    fun locationOn(): Painter {
        return painterResource(Res.drawable.location_on)
    }

    @Composable
    fun star(): Painter {
        return painterResource(Res.drawable.star)
    }

    @Composable
    fun search(): Painter {
        return painterResource(Res.drawable.search)
    }

    @Composable
    fun menu(): Painter {
        return painterResource(Res.drawable.menu)
    }
}
