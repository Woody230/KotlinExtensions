package com.bselzer.ktx.openapi.client.model.property

import com.bselzer.ktx.codegen.model.type.TypeName
import com.bselzer.ktx.openapi.model.reference.ReferenceOfOpenApiSchema
import com.bselzer.ktx.openapi.model.schema.OpenApiSchema

sealed class NestedPropertyResolver(
    private val resolvers: PropertyResolvers
) : PropertyResolver {
    protected fun nestedSchemaType(nestedSchemaReference: ReferenceOfOpenApiSchema, references: Map<String, OpenApiSchema>): TypeName {
        val nestedSchema = nestedSchemaReference.resolve(
            onValue = { nestedSchema -> nestedSchema },
            onReference = { reference -> references[reference.`$ref`.componentName] }
        )
        requireNotNull(nestedSchema) { "Unable to resolve the reference to the nested schema." }

        val nestedSchemaOutput = resolvers.resolver(nestedSchema, references).resolve(nestedSchema, references)
        return nestedSchemaOutput.typeName
    }
}