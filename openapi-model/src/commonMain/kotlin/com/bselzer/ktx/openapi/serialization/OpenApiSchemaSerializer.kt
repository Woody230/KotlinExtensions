package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.path.OpenApiEncodingName
import com.bselzer.ktx.openapi.model.path.OpenApiMediaTypeName
import com.bselzer.ktx.openapi.model.reference.OpenApiReferencePath
import com.bselzer.ktx.openapi.model.schema.OpenApiPropertyName
import com.bselzer.ktx.openapi.model.schema.OpenApiSchema
import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaComposite
import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType
import com.bselzer.ktx.serialization.context.*
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject

object OpenApiSchemaSerializer : OpenApiObjectSerializer<OpenApiSchema>() {
    override fun JsonObject.deserialize(): OpenApiSchema = OpenApiSchemaComposite(
        example = getElementOrNull("example", OpenApiValueSerializer::deserialize),
        examples = getListOrEmpty("examples", OpenApiValueSerializer::deserialize),
        title = getContentOrNull("title"),
        description = getDescriptionOrNull("description"),
        readOnly = getBooleanOrFalse("readOnly"),
        writeOnly = getBooleanOrFalse("writeOnly"),
        default = getObjectOrNull("default", OpenApiValueSerializer::deserialize),
        deprecated = getBooleanOrFalse("deprecated"),
        `$comment` = getContentOrNull("\$comment"),
        types = getEnumListOrEmpty<OpenApiSchemaType>("types").toSet(),
        format = getContentOrNull("format"),
        externalDocs = getExternalDocumentationOrNull("externalDocs"),
        extensions = getOpenApiExtensions(),
        enum = getListOrEmpty("enum", OpenApiValueSerializer::deserialize),
        const = getElementOrNull("const", OpenApiValueSerializer::deserialize),
        allOf = getObjectListOrEmpty("allOf", ::deserialize),
        anyOf = getObjectListOrEmpty("anyOf", ::deserialize),
        oneOf = getObjectListOrEmpty("oneOf", ::deserialize),
        not = getObjectOrNull("not", ::deserialize),
        `if` = getObjectOrNull("if", ::deserialize),
        then = getObjectOrNull("then", ::deserialize),
        `else` = getObjectOrNull("else", ::deserialize),
        items = getObjectOrNull("items", OpenApiReferenceOfSerializer(OpenApiSchemaSerializer)::deserialize),
        prefixItems = getObjectListOrEmpty("prefixItems", OpenApiReferenceOfSerializer(OpenApiSchemaSerializer)::deserialize),
        contains = getObjectOrNull("contains", OpenApiReferenceOfSerializer(OpenApiSchemaSerializer)::deserialize),
        minContains = getIntOrNull("minContains"),
        maxContains = getIntOrNull("maxContains"),
        minItems = getIntOrNull("minItems"),
        maxItems = getIntOrNull("maxItems"),
        uniqueItems = getBooleanOrNull("uniqueItems"),
        discriminator = getObjectOrNull("discriminator", OpenApiDiscriminatorSerializer::deserialize),
        xml = getObjectOrNull("xml", OpenApiXmlSerializer::deserialize),
        properties = getObjectMapOrEmpty("properties", OpenApiReferenceOfSerializer(OpenApiSchemaSerializer)::deserialize).mapKeys { entry -> OpenApiPropertyName(entry.key) },
        patternProperties = getObjectMapOrEmpty(
            "patternProperties",
            OpenApiReferenceOfSerializer(OpenApiSchemaSerializer)::deserialize
        ).mapKeys { entry -> OpenApiPropertyName(entry.key) },
        additionalProperties = getObjectOrNull("additionalProperties", OpenApiReferenceOfSerializer(OpenApiSchemaSerializer)::deserialize),
        unevaluatedProperties = getBooleanOrNull("unevaluatedProperties"),
        required = getEnumListOrEmpty<OpenApiPropertyName>("required").toSet(),
        dependentRequired = getDependentRequired("dependentRequired"),
        dependentSchemas = getObjectMapOrEmpty("dependentSchemas", OpenApiReferenceOfSerializer(OpenApiSchemaSerializer)::deserialize).mapKeys { entry ->
            OpenApiPropertyName(
                entry.key
            )
        },
        propertyNames = getObjectOrNull("propertyNames", OpenApiReferenceOfSerializer(OpenApiSchemaSerializer)::deserialize),
        minProperties = getIntOrNull("minProperties"),
        maxProperties = getIntOrNull("maxProperties"),
        minLength = getIntOrNull("minLength"),
        maxLength = getIntOrNull("maxLength"),
        pattern = getContentOrNull("pattern"),
        contentMediaType = getContentOrNull("contentMediaType")?.let(::OpenApiMediaTypeName),
        contentEncoding = getContentOrNull("contentEncoding")?.let(::OpenApiEncodingName),
        multipleOf = getDoubleOrNull("multipleOf"),
        minimum = getDoubleOrNull("minimum"),
        exclusiveMinimum = getDoubleOrNull("exclusiveMinimum"),
        maximum = getDoubleOrNull("maximum"),
        exclusiveMaximum = getDoubleOrNull("exclusiveMaximum"),
        `$id` = getContentOrNull("\$id")?.let(::OpenApiReferencePath),
        `$anchor` = getContentOrNull("\$anchor")?.let(::OpenApiReferencePath),
        `$defs` = getObjectMapOrEmpty("\$defs", ::deserialize).mapKeys { entry -> OpenApiReferencePath(entry.key) }
    )

    private fun JsonObject.getDependentRequired(key: String): Map<OpenApiPropertyName, Set<OpenApiPropertyName>> {
        val value = get(key) ?: return emptyMap()
        val map = value.jsonObject.toMap { element -> element.toEnumList<OpenApiPropertyName>().toSet() }
        return map.mapKeys { entry -> OpenApiPropertyName(entry.key) }
    }
}