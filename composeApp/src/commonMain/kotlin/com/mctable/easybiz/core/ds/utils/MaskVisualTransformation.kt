package com.mctable.easybiz.core.ds.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class MaskVisualTransformation(private val mask: String) : VisualTransformation {

    private val specialChars = mask.filterNot { it == '#' }

    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed =
            if (text.text.length >= mask.length) text.text.substring(0..mask.lastIndex) else text.text

        val annotatedString = AnnotatedString.Builder().run {
            var textIndex = 0
            mask.forEach { maskChar ->
                if (textIndex < trimmed.length) {
                    if (maskChar != '#') {
                        append(maskChar)
                    } else {
                        append(trimmed[textIndex])
                        textIndex++
                    }
                }
            }
            toAnnotatedString()
        }

        val offsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                val offsetValue = offset.coerceAtMost(mask.count { it == '#' })
                val masked =
                    mask.take(offsetValue + mask.take(offsetValue).count { it in specialChars })
                return masked.length
            }

            override fun transformedToOriginal(offset: Int): Int {
                return mask.take(offset).count { it == '#' }
            }
        }

        return TransformedText(annotatedString, offsetTranslator)
    }
}