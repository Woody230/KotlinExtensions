package com.bselzer.ktx.openapi.model.schema

import com.bselzer.ktx.openapi.model.OpenApiDescription
import com.bselzer.ktx.openapi.model.OpenApiExampleValue
import com.bselzer.ktx.openapi.model.OpenApiExternalDocumentation
import com.bselzer.ktx.openapi.model.base.OpenApiExtensible

// TODO conditionals https://json-schema.org/understanding-json-schema/reference/conditionals.html
// TODO complex https://json-schema.org/understanding-json-schema/structuring.html
// TODO dialect https://json-schema.org/understanding-json-schema/reference/schema.html
sealed interface OpenApiSchemaCore : OpenApiExtensible {
    /**
     * A free-form property to include an example of an instance for this schema. To represent examples that cannot be naturally represented in JSON or YAML, a string value can be used to contain the example with escaping where necessary.
     */
    @Deprecated("The example property has been deprecated in favor of the JSON Schema examples keyword. Use of example is discouraged, and later versions of this specification may remove it.")
    val example: OpenApiExampleValue?

    /**
     * A free-form property to include examples of instances for this schema. To represent examples that cannot be naturally represented in JSON or YAML, a string value can be used to contain the example with escaping where necessary.
     */
    val examples: List<OpenApiExampleValue>

    /**
     * A “title” will preferably be short, whereas a “description” will provide a more lengthy explanation about the purpose of the data described by the schema.
     */
    val title: String?

    /**
     * A “title” will preferably be short, whereas a “description” will provide a more lengthy explanation about the purpose of the data described by the schema.
     */
    val description: OpenApiDescription?

    /**
     * readOnly indicates that a value should not be modified.
     */
    val readOnly: Boolean

    /**
     * writeOnly indicates that a value may be set, but will remain hidden.
     */
    val writeOnly: Boolean

    /**
     * The default value represents what would be assumed by the consumer of the input as the value of the schema if one is not provided. Unlike JSON Schema, the value MUST conform to the defined type for the Schema Object defined at the same level. For example, if type is string, then default can be "foo" but cannot be 1.
     */
    val default: Any?

    /**
     * The deprecated keyword is a boolean that indicates that the instance value the keyword applies to should not be used and may be removed in the future.
     */
    val deprecated: Boolean

    /**
     * The $comment keyword is strictly intended for adding comments to a schema. Its value must always be a string. Unlike the annotations title, description, and examples, JSON schema implementations aren’t allowed to attach any meaning or behavior to it whatsoever, and may even strip them at any time.
     */
    val `$comment`: String?

    /**
     * Whether the [OpenApiSchemaType.NULL] type is used.
     */
    val isNullable: Boolean

    /**
     * The data types.
     */
    val types: Set<OpenApiSchemaType>

    /**
     * The format keyword allows for basic semantic identification of certain kinds of values that are commonly used.
     */
    val format: String?

    /**
     * Additional external documentation for this schema.
     */
    val externalDocs: OpenApiExternalDocumentation?
}