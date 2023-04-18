package com.bselzer.ktx.openapi.client.model.property

import com.bselzer.ktx.codegen.model.type.name.TypeName
import com.bselzer.ktx.openapi.model.reference.ReferenceOfOpenApiSchema

sealed class NestedPropertyResolver(
    private val resolvers: PropertyResolvers
) : PropertyResolver {
    protected fun nestedSchemaType(nestedSchemaReference: ReferenceOfOpenApiSchema, input: PropertyInput): TypeName {
        val nestedSchema = nestedSchemaReference.resolve(
            onValue = { nestedSchema -> nestedSchema },
            onReference = { reference -> input.references[reference.`$ref`.componentName] }
        )
        requireNotNull(nestedSchema) { "Unable to resolve the reference to the nested schema." }

        val nestedInput = input.copy(schema = nestedSchema)
        val nestedOutput = resolvers.resolver(nestedInput).resolve(nestedInput)
        return nestedOutput.type
    }
}