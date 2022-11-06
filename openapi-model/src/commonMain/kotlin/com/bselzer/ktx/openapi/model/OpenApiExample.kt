package com.bselzer.ktx.openapi.model

data class OpenApiExample(
    /**
     * Short description for the example.
     */
    val summary: String? = null,

    /**
     * Long description for the example. CommonMark syntax MAY be used for rich text representation.
     */
    val description: OpenApiDescription? = null,

    /**
     * Embedded literal example. The value field and externalValue field are mutually exclusive. To represent examples of media types that cannot naturally represented in JSON or YAML, use a string value to contain the example, escaping where necessary.
     */
    val value: Any? = null,

    /**
     * A URI that points to the literal example. This provides the capability to reference examples that cannot easily be included in JSON or YAML documents. The value field and externalValue field are mutually exclusive. See the rules for resolving Relative References.
     */
    val externalValue: String? = null,

    override val extensions: OpenApiExtensions = emptyMap()
) : OpenApiExtensible