package com.bselzer.ktx.compose.ui.layout.merge

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.material.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

class TextFieldColorsMerger : ComponentMerger<TextFieldColors> {
    override val default: TextFieldColors = Default

    companion object {
        @Stable
        val Default = object : TextFieldColors {
            @Composable
            override fun backgroundColor(enabled: Boolean) = mutableStateOf(Color.Transparent)

            @Composable
            override fun cursorColor(isError: Boolean): State<Color> = mutableStateOf(Color.Transparent)

            @Composable
            override fun indicatorColor(enabled: Boolean, isError: Boolean, interactionSource: InteractionSource) = mutableStateOf(Color.Transparent)

            @Composable
            override fun labelColor(enabled: Boolean, error: Boolean, interactionSource: InteractionSource) = mutableStateOf(Color.Transparent)

            @Composable
            override fun leadingIconColor(enabled: Boolean, isError: Boolean) = mutableStateOf(Color.Transparent)

            @Composable
            override fun placeholderColor(enabled: Boolean) = mutableStateOf(Color.Transparent)

            @Composable
            override fun textColor(enabled: Boolean) = mutableStateOf(Color.Transparent)

            @Composable
            override fun trailingIconColor(enabled: Boolean, isError: Boolean) = mutableStateOf(Color.Transparent)
        }
    }
}