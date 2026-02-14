package com.mctable.easybiz.core.ds.utils


import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import easybiz.composeapp.generated.resources.Res
import easybiz.composeapp.generated.resources.contact_mail
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
}
