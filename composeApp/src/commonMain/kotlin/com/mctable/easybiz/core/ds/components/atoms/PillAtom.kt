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
import com.mctable.easybiz.core.ds.theme.md_light_success

sealed class PillType {
    object Ghost : PillType()
    object Success : PillType()
}

@Composable
fun PillAtom(pillType: PillType = PillType.Ghost, text: String, modifier: Modifier = Modifier) {
    when (pillType) {
        PillType.Ghost -> {
            Box(
                modifier = Modifier.border(
                    shape = RoundedCornerShape(50),
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)
                ).background(
                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(50)
                )
                    .padding(top = 4.dp, bottom = 4.dp, start = 18.dp, end = 18.dp)
                    .then(modifier)
            ) {
                Text(
                    text = text,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        PillType.Success -> {
            Box(
                modifier = Modifier.border(
                    shape = RoundedCornerShape(50),
                    width = 1.dp,
                    color = md_light_success
                ).background(
                    md_light_success.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(50)
                )
                    .padding(top = 4.dp, bottom = 4.dp, start = 18.dp, end = 18.dp)
                    .then(modifier)
            ) {
                Text(
                    text = text,
                    color = md_light_success,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
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
            PillAtom(pillType = PillType.Success, text = "Disponivel")
            Spacer(modifier = Modifier.height(8.dp))
            PillAtom(pillType = PillType.Ghost, text = "Ocupado")
        }
    }

}