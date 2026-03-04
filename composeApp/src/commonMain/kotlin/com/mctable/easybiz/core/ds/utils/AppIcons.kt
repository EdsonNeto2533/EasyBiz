package com.mctable.easybiz.core.ds.utils


import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import easybiz.composeapp.generated.resources.Res
import easybiz.composeapp.generated.resources.account_circle
import easybiz.composeapp.generated.resources.arrow_back
import easybiz.composeapp.generated.resources.contact_mail
import easybiz.composeapp.generated.resources.location_on
import easybiz.composeapp.generated.resources.star
import easybiz.composeapp.generated.resources.visibility_off
import easybiz.composeapp.generated.resources.visibility_on
import org.jetbrains.compose.resources.painterResource

object AppIcons {

    @Composable
    fun ContactEmail(): Painter {
        return painterResource(Res.drawable.contact_mail)
    }

    @Composable
    fun VisibilityOff(): Painter {
        return painterResource(Res.drawable.visibility_off)
    }

    @Composable
    fun VisibilityOn(): Painter {
        return painterResource(Res.drawable.visibility_on)
    }

    @Composable
    fun AccountCircle(): Painter {
        return painterResource(Res.drawable.account_circle)
    }

    @Composable
    fun ArrowBack(): Painter {
        return painterResource(Res.drawable.arrow_back)
    }

    @Composable
    fun LocationOn(): Painter {
        return painterResource(Res.drawable.location_on)
    }

    @Composable
    fun Star(): Painter {
        return painterResource(Res.drawable.star)
    }
}
