package com.bselzer.ktx.openapi.client.property

import com.bselzer.ktx.openapi.client.type.name.TypeName
import com.bselzer.ktx.openapi.model.reference.ReferenceOfOpenApiSchema
import com.bselzer.ktx.openapi.model.schema.OpenApiSchema

sealed class NestedPropertyHandler(
    private val propertyHandlerResolver: PropertyHandlerResolver
) : PropertyHandler {
    protected fun nestedSchemaType(nestedSchemaReference: ReferenceOfOpenApiSchema, references: Map<String, OpenApiSchema>): TypeName {
        val nestedSchema = nestedSchemaReference.resolve(
            onValue = { nestedSchema -> nestedSchema },
            onReference = { reference -> references[reference.`$ref`.componentName] }
        )
        requireNotNull(nestedSchema) { "Unable to resolve the reference to the nested schema." }

        val nestedSchemaOutput = propertyHandlerResolver.handler(nestedSchema, references).resolve(nestedSchema, references)
        return nestedSchemaOutput.typeName
    }
}