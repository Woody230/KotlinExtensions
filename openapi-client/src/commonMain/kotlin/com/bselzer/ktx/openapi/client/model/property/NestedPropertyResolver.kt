package com.bselzer.ktx.openapi.client.model.property

import com.bselzer.ktx.codegen.model.property.Property
import com.bselzer.ktx.openapi.model.reference.ReferenceOfOpenApiSchema

sealed class NestedPropertyResolver : BasePropertyResolver() {
    internal fun PropertyResolver.nestedProperty(nestedSchemaReference: ReferenceOfOpenApiSchema, input: PropertyContext): Property {
        val nestedSchema = nestedSchemaReference.resolve(
            onValue = { nestedSchema -> nestedSchema },
            onReference = { reference -> input.references[reference.`$ref`.componentName] }
        )
        requireNotNull(nestedSchema) { "Unable to resolve the reference to the nested schema." }

        val nestedInput = input.copy(schema = nestedSchema)
        return resolve(nestedInput)
    }
}