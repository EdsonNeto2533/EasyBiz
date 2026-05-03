package com.mctable.easybiz.core.ds.components.atoms

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mctable.easybiz.core.ds.theme.EasyBizTheme
import com.mctable.easybiz.core.ds.theme.ErrorRed
import com.mctable.easybiz.core.ds.theme.ErrorRedContainer
import com.mctable.easybiz.core.ds.theme.SuccessGreen
import com.mctable.easybiz.core.ds.theme.SuccessGreenContainer
import com.mctable.easybiz.core.ds.theme.WarningYellow
import com.mctable.easybiz.core.ds.theme.WarningYellowContainer

sealed class PillType {
    object Ghost : PillType()
    object Success : PillType()
    object Warning : PillType()
    object Error : PillType()
}

@Composable
fun PillAtom(pillType: PillType = PillType.Ghost, text: String, modifier: Modifier = Modifier) {
    val (bgColor, textColor, borderColor) = when (pillType) {
        PillType.Ghost -> Triple(
            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            MaterialTheme.colorScheme.onSurfaceVariant,
            MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
        )
        PillType.Success -> Triple(
            SuccessGreenContainer,
            SuccessGreen,
            SuccessGreen.copy(alpha = 0.3f)
        )
        PillType.Warning -> Triple(
            WarningYellowContainer,
            WarningYellow,
            WarningYellow.copy(alpha = 0.3f)
        )
        PillType.Error -> Triple(
            ErrorRedContainer,
            ErrorRed,
            ErrorRed.copy(alpha = 0.3f)
        )
    }

    Box(
        modifier = Modifier
            .border(
                shape = RoundedCornerShape(50),
                width = 1.dp,
                color = borderColor
            )
            .background(
                bgColor,
                shape = RoundedCornerShape(50)
            )
            .padding(horizontal = 14.dp, vertical = 5.dp)
            .then(modifier)
    ) {
        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.labelMedium
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PillAtomPreview() {
    EasyBizTheme {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            PillAtom(pillType = PillType.Success, text = "Disponível")
            Spacer(modifier = Modifier.height(8.dp))
            PillAtom(pillType = PillType.Warning, text = "Em espera")
            Spacer(modifier = Modifier.height(8.dp))
            PillAtom(pillType = PillType.Error, text = "Indisponível")
            Spacer(modifier = Modifier.height(8.dp))
            PillAtom(pillType = PillType.Ghost, text = "Padrão")
        }
    }
}
