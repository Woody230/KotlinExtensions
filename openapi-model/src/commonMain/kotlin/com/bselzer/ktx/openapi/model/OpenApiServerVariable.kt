package com.bselzer.ktx.openapi.model

import com.bselzer.ktx.openapi.model.base.OpenApiExtensible

/**
 * An object representing a Server Variable for server URL template substitution.
 */
data class OpenApiServerVariable(
    /**
     * An enumeration of string values to be used if the substitution options are from a limited set. The array MUST NOT be empty.
     */
    val enum: List<String> = emptyList(),

    /**
     * REQUIRED. The default value to use for substitution, which SHALL be sent if an alternate value is not supplied. Note this behavior is different than the Schema Object’s treatment of default values, because in those cases parameter values are optional. If the enum is defined, the value MUST exist in the enum’s values
     */
    val default: String,

    /**
     * An optional description for the server variable. CommonMark syntax MAY be used for rich text representation.
     */
    val description: OpenApiDescription? = null,

    override val extensions: OpenApiExtensions = emptyMap()
) : OpenApiExtensible