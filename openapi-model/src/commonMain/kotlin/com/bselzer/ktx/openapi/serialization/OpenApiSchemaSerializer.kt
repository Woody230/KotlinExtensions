package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.OpenApiReferenceIdentifier
import com.bselzer.ktx.openapi.model.path.OpenApiEncodingName
import com.bselzer.ktx.openapi.model.path.OpenApiMediaTypeName
import com.bselzer.ktx.openapi.model.schema.OpenApiPropertyName
import com.bselzer.ktx.openapi.model.schema.OpenApiSchema
import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaComposite
import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType
import com.bselzer.ktx.serialization.context.*
import com.bselzer.ktx.serialization.context.JsonContext.Default.decode
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
        default = getObjectOrNull("default") { it.toOpenApiValue() },
        deprecated = getBooleanOrFalse("deprecated"),
        `$comment` = getContentOrNull("\$comment"),
        types = getContentListOrEmpty("types").map { content -> content.decode<OpenApiSchemaType>() }.toSet(),
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
        items = getObjectOrNull("items") { OpenApiReferenceOfSerializer(this@OpenApiSchemaSerializer).deserialize(it) },
        prefixItems = getObjectListOrEmpty("prefixItems") { it.toOpenApiSchemaReference() },
        contains = getObjectOrNull("contains") { it.toOpenApiSchemaReference() },
        minContains = getIntOrNull("minContains"),
        maxContains = getIntOrNull("maxContains"),
        minItems = getIntOrNull("minItems"),
        maxItems = getIntOrNull("maxItems"),
        uniqueItems = getBooleanOrNull("uniqueItems"),
        discriminator = getObjectOrNull("discriminator") { OpenApiDiscriminatorSerializer.deserialize(it) },
        xml = getObjectOrNull("xml") { OpenApiXmlSerializer.deserialize(it) },
        properties = getObjectMapOrEmpty("properties") { it.toOpenApiSchemaReference() }.mapKeys { entry -> OpenApiPropertyName(entry.key) },
        patternProperties = getObjectMapOrEmpty("patternProperties") { it.toOpenApiSchemaReference() }.mapKeys { entry -> OpenApiPropertyName(entry.key) },
        additionalProperties = getObjectOrNull("additionalProperties") { it.toOpenApiSchemaReference() },
        unevaluatedProperties = getBooleanOrNull("unevaluatedProperties"),
        required = getContentListOrEmpty("required").decode<OpenApiPropertyName>().toSet(),
        dependentRequired = getDependentRequired("dependentRequired"),
        dependentSchemas = getObjectMapOrEmpty("dependentSchemas") { it.toOpenApiSchemaReference() }.mapKeys { entry -> OpenApiPropertyName(entry.key) },
        propertyNames = getObjectOrNull("propertyNames") { it.toOpenApiSchemaReference() },
        minProperties = getIntOrNull("minProperties"),
        maxProperties = getIntOrNull("maxProperties"),
        minLength = getIntOrNull("minLength"),
        maxLength = getIntOrNull("maxLength"),
        pattern = getContentOrNull("pattern"),
        contentMediaType = getContentOrNull("contentMediaType")?.let { OpenApiMediaTypeName(it) },
        contentEncoding = getContentOrNull("contentEncoding")?.let { OpenApiEncodingName(it) },
        multipleOf = getDoubleOrNull("multipleOf"),
        minimum = getDoubleOrNull("minimum"),
        exclusiveMinimum = getDoubleOrNull("exclusiveMinimum"),
        maximum = getDoubleOrNull("maximum"),
        exclusiveMaximum = getDoubleOrNull("exclusiveMaximum"),
        `$id` = getContentOrNull("\$id")?.let { OpenApiReferenceIdentifier(it) },
        `$anchor` = getContentOrNull("\$anchor")?.let { OpenApiReferenceIdentifier(it) },
        `$defs` = getObjectMapOrEmpty("\$defs") { it.toOpenApiSchema() }.mapKeys { entry -> OpenApiReferenceIdentifier(entry.key) }
    )

    private fun JsonObject.getDependentRequired(key: String): Map<OpenApiPropertyName, Set<OpenApiPropertyName>> {
        val value = get(key) ?: return emptyMap()
        val map = value.jsonObject.toMap { element -> element.toContentList().decode<OpenApiPropertyName>().toSet() }
        return map.mapKeys { entry -> OpenApiPropertyName(entry.key) }
    }
}