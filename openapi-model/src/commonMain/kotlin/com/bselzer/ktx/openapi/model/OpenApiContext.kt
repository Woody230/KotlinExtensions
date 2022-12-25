package com.bselzer.ktx.openapi.model

import com.bselzer.ktx.openapi.model.expression.RuntimeExpressionFactory
import com.bselzer.ktx.openapi.model.information.OpenApiContact
import com.bselzer.ktx.openapi.model.information.OpenApiInformation
import com.bselzer.ktx.openapi.model.information.OpenApiLicense
import com.bselzer.ktx.openapi.model.parameter.OpenApiParameter
import com.bselzer.ktx.openapi.model.parameter.OpenApiParameterIn
import com.bselzer.ktx.openapi.model.parameter.OpenApiParameterName
import com.bselzer.ktx.openapi.model.parameter.OpenApiParameterStyle
import com.bselzer.ktx.openapi.model.path.*
import com.bselzer.ktx.openapi.model.response.OpenApiHttpStatusCode
import com.bselzer.ktx.openapi.model.response.OpenApiLink
import com.bselzer.ktx.openapi.model.response.OpenApiResponse
import com.bselzer.ktx.openapi.model.response.OpenApiResponses
import com.bselzer.ktx.openapi.model.schema.*
import com.bselzer.ktx.openapi.model.security.flow.*
import com.bselzer.ktx.openapi.model.security.scheme.*
import com.bselzer.ktx.openapi.model.server.OpenApiServer
import com.bselzer.ktx.openapi.model.server.OpenApiServerVariable
import com.bselzer.ktx.openapi.model.value.*
import com.bselzer.ktx.serialization.context.*
import com.bselzer.ktx.serialization.context.JsonContext.Default.decode
import kotlinx.serialization.json.*

object OpenApiContext {

    fun JsonObject.toOpenApi(): OpenApi = OpenApi(
        openapi = getContentOrNull("openapi") ?: "3.1.0",
        info = getObject("info") { it.toOpenApiInformation() },
        jsonSchemaDialect = getContentOrNull("jsonSchemaDialect") ?: "https://spec.openapis.org/oas/3.1/dialect/base",
        servers = getObjectListOrEmpty("servers") { it.toOpenApiServer() },
        paths = getObject("paths") { it.toOpenApiPaths() },
        webhooks = getObjectMapOrEmpty("webhooks") { it.toOpenApiPathItemReference() },
        components = getObjectOrNull("components") { it.toOpenApiComponents() },
        security = getSecurityRequirements("security"),
        tags = getObjectListOrEmpty("tags") { it.toOpenApiTag() },
        externalDocs = getExternalDocumentationOrNull("externalDocs"),
        extensions = getOpenApiExtensions(),
    )

    fun JsonObject.toOpenApiTag(): OpenApiTag = OpenApiTag(
        name = getContent("name"),
        description = getDescriptionOrNull("description"),
        externalDocs = getExternalDocumentationOrNull("externalDocs"),
        extensions = getOpenApiExtensions()
    )

    fun JsonObject.toOpenApiComponents(): OpenApiComponents = OpenApiComponents(
        schemas = getObjectMapOrEmpty("schemas") { it.toOpenApiSchema() },
        responses = getObjectMapOrEmpty("responses") { it.toOpenApiResponseReference() },
        parameters = getObjectMapOrEmpty("parameters") { it.toOpenApiParameterReference() }.mapKeys { entry -> OpenApiParameterName(entry.key) },
        examples = getObjectMapOrEmpty("examples") { it.toOpenApiExampleReference() },
        requestBodies = getObjectMapOrEmpty("requestBodies") { it.toOpenApiRequestBodyReference() },
        headers = getObjectMapOrEmpty("headers") { it.toOpenApiHeaderReference() },
        securitySchemes = getObjectMapOrEmpty("securitySchemes") { it.toOpenApiSecuritySchemeReference() }.mapKeys { entry -> OpenApiSecuritySchemeName(entry.key) },
        links = getObjectMapOrEmpty("links") { it.toOpenApiLinkReference() },
        callbacks = getObjectMapOrEmpty("callbacks") { it.toOpenApiCallbackReference() },
        pathItems = getObjectMapOrEmpty("pathItems") { it.toOpenApiPathItemReference() },
        extensions = getOpenApiExtensions()
    )

    fun JsonObject.toOpenApiSecurityScheme(): OpenApiSecurityScheme {
        val type = getContent("type").decode<OpenApiSecuritySchemeType>()
        val description = getDescriptionOrNull("description")
        return when (type) {
            OpenApiSecuritySchemeType.API_KEY -> ApiKeySecurityScheme(
                description = description,
                name = OpenApiParameterName(getContent("name")),
                `in` = getContent("in").decode()
            )
            OpenApiSecuritySchemeType.HTTP -> HttpSecurityScheme(
                description = description,
                scheme = getContent("scheme"),
                bearerFormat = getContentOrNull("bearerFormat")
            )
            OpenApiSecuritySchemeType.MUTUAL_TLS -> MutualTlsSecurityScheme(
                description = description
            )
            OpenApiSecuritySchemeType.OAUTH_2 -> OAuth2SecurityScheme(
                description = description,
                flows = getObject("flows") { it.toOAuthFlows() }
            )
            OpenApiSecuritySchemeType.OPEN_ID_CONNECT -> OpenIdConnectSecurityScheme(
                description = description,
                openIdConnectUrl = getUrl("openIdConnectUrl")
            )
        }
    }

    private fun JsonObject.toOpenApiSecuritySchemeReference(): OpenApiReferenceOf<OpenApiSecurityScheme> {
        if (containsKey("\$ref")) {
            val reference = toOpenApiReference()
            return OpenApiReferenceOf(reference)
        }

        val securityScheme = toOpenApiSecurityScheme()
        return OpenApiReferenceOf(securityScheme)
    }

    fun JsonObject.toOAuthFlows(): OAuthFlows = OAuthFlows(
        implicit = getObjectOrNull("implicit") { it.toImplicitOAuthFlow() },
        password = getObjectOrNull("password") { it.toPasswordOAuthFlow() },
        clientCredentials = getObjectOrNull("clientCredentials") { it.toClientCredentialsOAuthFlow() },
        authorizationCode = getObjectOrNull("authorizationCode") { it.toAuthorizationCodeOAuthFlow() },
        extensions = getOpenApiExtensions()
    )

    fun JsonObject.toImplicitOAuthFlow(): ImplicitOAuthFlow = ImplicitOAuthFlow(
        refreshUrl = getUrlOrNull("refreshUrl"),
        scopes = getContentMapOrEmpty("scopes").mapKeys { entry -> OpenApiSecurityScope(entry.key) },
        extensions = getOpenApiExtensions(),
        authorizationUrl = getUrl("authorizationUrl")
    )

    fun JsonObject.toPasswordOAuthFlow(): PasswordOAuthFlow = PasswordOAuthFlow(
        refreshUrl = getUrlOrNull("refreshUrl"),
        scopes = getContentMapOrEmpty("scopes").mapKeys { entry -> OpenApiSecurityScope(entry.key) },
        extensions = getOpenApiExtensions(),
        tokenUrl = getUrl("tokenUrl")
    )

    fun JsonObject.toClientCredentialsOAuthFlow(): ClientCredentialsOAuthFlow = ClientCredentialsOAuthFlow(
        refreshUrl = getUrlOrNull("refreshUrl"),
        scopes = getContentMapOrEmpty("scopes").mapKeys { entry -> OpenApiSecurityScope(entry.key) },
        extensions = getOpenApiExtensions(),
        tokenUrl = getUrl("tokenUrl")
    )

    fun JsonObject.toAuthorizationCodeOAuthFlow(): AuthorizationCodeOAuthFlow = AuthorizationCodeOAuthFlow(
        refreshUrl = getUrlOrNull("refreshUrl"),
        scopes = getContentMapOrEmpty("scopes").mapKeys { entry -> OpenApiSecurityScope(entry.key) },
        extensions = getOpenApiExtensions(),
        authorizationUrl = getUrl("authorizationUrl"),
        tokenUrl = getUrl("tokenUrl")
    )

    fun JsonObject.toOpenApiInformation(): OpenApiInformation = OpenApiInformation(
        title = getContent("title"),
        summary = getContentOrNull("summary"),
        description = getDescriptionOrNull("description"),
        termsOfService = getContentOrNull("termsOfService"),
        contact = getObjectOrNull("contact") { it.toOpenApiContact() },
        license = getObjectOrNull("license") { it.toOpenApiLicense() },
        version = getContent("version"),
        extensions = getOpenApiExtensions()
    )

    fun JsonObject.toOpenApiServer(): OpenApiServer = OpenApiServer(
        url = getUrl("url"),
        description = getDescriptionOrNull("description"),
        variables = getObjectMapOrEmpty("variables") { it.toOpenApiServerVariable() },
        extensions = getOpenApiExtensions()
    )

    fun JsonObject.toOpenApiServerVariable(): OpenApiServerVariable = OpenApiServerVariable(
        enum = getContentListOrEmpty("enum"),
        default = getContent("default"),
        description = getDescriptionOrNull("description"),
        extensions = getOpenApiExtensions()
    )

    fun JsonObject.toOpenApiContact(): OpenApiContact = OpenApiContact(
        name = getContentOrNull("name"),
        url = getUrlOrNull("url"),
        email = getEmailOrNull("email"),
        extensions = getOpenApiExtensions()
    )

    fun JsonObject.toOpenApiLicense(): OpenApiLicense = OpenApiLicense(
        name = getContent("name"),
        identifier = getContentOrNull("identifier"),
        url = getUrlOrNull("url"),
        extensions = getOpenApiExtensions()
    )

    fun JsonObject.toOpenApiPaths(): OpenApiPaths = OpenApiPaths(
        pathItems = getObjectMapOrEmpty("pathItems") { it.toOpenApiPathItem() },
        extensions = getOpenApiExtensions()
    )

    fun JsonObject.toOpenApiPathItem(): OpenApiPathItem = OpenApiPathItem(
        `$ref` = getReferenceIdentifierOrNull("\$ref"),
        summary = getContentOrNull("summary"),
        description = getDescriptionOrNull("description"),
        get = getObjectOrNull("get") { it.toOpenApiOperation() },
        put = getObjectOrNull("put") { it.toOpenApiOperation() },
        post = getObjectOrNull("post") { it.toOpenApiOperation() },
        delete = getObjectOrNull("delete") { it.toOpenApiOperation() },
        options = getObjectOrNull("options") { it.toOpenApiOperation() },
        head = getObjectOrNull("head") { it.toOpenApiOperation() },
        patch = getObjectOrNull("patch") { it.toOpenApiOperation() },
        trace = getObjectOrNull("trace") { it.toOpenApiOperation() },
        servers = getObjectListOrEmpty("servers") { it.toOpenApiServer() },
        parameters = getObjectListOrEmpty("parameters") { it.toOpenApiParameterReference() },
        extensions = getOpenApiExtensions()
    )

    private fun JsonObject.toOpenApiPathItemReference(): OpenApiReferenceOf<OpenApiPathItem> {
        if (containsKey("\$ref")) {
            val reference = toOpenApiReference()
            return OpenApiReferenceOf(reference)
        }

        val pathItem = toOpenApiPathItem()
        return OpenApiReferenceOf(pathItem)
    }

    fun JsonObject.toOpenApiOperation(): OpenApiOperation = OpenApiOperation(
        tags = getObjectListOrEmpty("tags") { element -> OpenApiTagName(element.toContent()) },
        summary = getContentOrNull("summary"),
        description = getDescriptionOrNull("description"),
        externalDocs = getExternalDocumentationOrNull("externalDocs"),
        operationId = getContentOrNull("operationId")?.let { OpenApiOperationId(it) },
        parameters = getObjectListOrEmpty("parameters") { it.toOpenApiParameterReference() },
        requestBody = getObjectOrNull("requestBody") { it.toOpenApiRequestBodyReference() },
        responses = getObject("responses") { it.toOpenApiResponses() },
        callbacks = getObjectMapOrEmpty("callbacks") { it.toOpenApiCallbackReference() },
        deprecated = getBooleanOrFalse("deprecated"),
        security = getSecurityRequirements("security"),
        servers = getObjectListOrEmpty("servers") { it.toOpenApiServer() },
        extensions = getOpenApiExtensions()
    )

    private fun JsonObject.getSecurityRequirements(key: String): OpenApiSecurityRequirements = getObjectListOrEmpty(key) {
        val names = mapKeys { entry -> OpenApiSecuritySchemeName(entry.key) }.mapValues { entry ->
            entry.value.jsonArray.map { element ->
                OpenApiSecurityScope(element.toContent())
            }
        }

        OpenApiSecurityRequirement(names)
    }

    fun JsonObject.toOpenApiCallback(): OpenApiCallback = OpenApiCallback(
        pathItems = getObjectMapOrEmpty("pathItems") { it.toOpenApiPathItemReference() },
        extensions = getOpenApiExtensions()
    )

    private fun JsonObject.toOpenApiCallbackReference(): OpenApiReferenceOf<OpenApiCallback> {
        if (containsKey("\$ref")) {
            val reference = toOpenApiReference()
            return OpenApiReferenceOf(reference)
        }

        val callback = toOpenApiCallback()
        return OpenApiReferenceOf(callback)
    }

    fun JsonObject.toOpenApiRequestBody(): OpenApiRequestBody = OpenApiRequestBody(
        description = getDescriptionOrNull("description"),
        content = getObjectMapOrEmpty("content") { it.toOpenApiMediaType() }.mapKeys { entry -> OpenApiMediaTypeName(entry.key) },
        required = getBooleanOrFalse("required"),
        extensions = getOpenApiExtensions()
    )

    fun JsonObject.toOpenApiResponses(): OpenApiResponses = OpenApiResponses(
        default = getObjectOrNull("default") { it.toOpenApiResponseReference() },
        responses = getObjectMapOrEmpty("responses") { it.toOpenApiResponseReference() }.mapKeys { entry -> OpenApiHttpStatusCode(entry.key) },
        extensions = getOpenApiExtensions()
    )

    fun JsonObject.toOpenApiResponse(): OpenApiResponse = OpenApiResponse(
        description = OpenApiDescription(getContent("description")),
        headers = getObjectMapOrEmpty("headers") { it.toOpenApiHeaderReference() },
        content = getObjectMapOrEmpty("content") { it.toOpenApiMediaType() }.mapKeys { entry -> OpenApiMediaTypeName(entry.key) },
        links = getObjectMapOrEmpty("links") { it.toOpenApiLinkReference() },
        extensions = getOpenApiExtensions()
    )

    private fun JsonObject.toOpenApiResponseReference(): OpenApiReferenceOf<OpenApiResponse> {
        if (containsKey("\$ref")) {
            val reference = toOpenApiReference()
            return OpenApiReferenceOf(reference)
        }

        val response = toOpenApiResponse()
        return OpenApiReferenceOf(response)
    }

    fun JsonObject.toOpenApiLink(): OpenApiLink = OpenApiLink(
        operationRef = OpenApiReferenceIdentifier(getContent("operationRef")),
        operationId = getContentOrNull("operationId")?.let { OpenApiOperationId(it) },
        parameters = getMapOrEmpty("parameters") { it.toOpenApiValueOrRuntimeExpression() }.mapKeys { entry -> OpenApiParameterName(entry.key) },
        requestBody = get("requestBody")?.toOpenApiValueOrRuntimeExpression(),
        description = getDescriptionOrNull("description"),
        server = getObject("server") { it.toOpenApiServer() },
        extensions = getOpenApiExtensions()
    )

    private fun JsonObject.toOpenApiLinkReference(): OpenApiReferenceOf<OpenApiLink> {
        if (containsKey("\$ref")) {
            val reference = toOpenApiReference()
            return OpenApiReferenceOf(reference)
        }

        val link = toOpenApiLink()
        return OpenApiReferenceOf(link)
    }

    fun JsonElement.toOpenApiValueOrRuntimeExpression(): OpenApiValueOrRuntimeExpression {
        if (this is JsonPrimitive && isString) {
            val expression = RuntimeExpressionFactory(content).expression
            return OpenApiValueOrRuntimeExpression(expression)
        }

        val value = jsonObject.toOpenApiValue()
        return OpenApiValueOrRuntimeExpression(value)
    }

    private fun JsonObject.toOpenApiRequestBodyReference(): OpenApiReferenceOf<OpenApiRequestBody> {
        if (containsKey("\$ref")) {
            val reference = toOpenApiReference()
            return OpenApiReferenceOf(reference)
        }

        val requestBody = toOpenApiRequestBody()
        return OpenApiReferenceOf(requestBody)
    }

    fun JsonObject.toOpenApiParameter(): OpenApiParameter {
        val `in`: OpenApiParameterIn = with(JsonContext) { getContent("in").decode() }
        val defaultRequired = `in` == OpenApiParameterIn.PATH
        val defaultStyle = when (`in`) {
            OpenApiParameterIn.QUERY -> OpenApiParameterStyle.FORM
            OpenApiParameterIn.HEADER -> OpenApiParameterStyle.SIMPLE
            OpenApiParameterIn.PATH -> OpenApiParameterStyle.SIMPLE
            OpenApiParameterIn.COOKIE -> OpenApiParameterStyle.FORM
        }
        val style = getContentOrNull("style")?.decode() ?: defaultStyle
        val defaultExplode = when (style) {
            OpenApiParameterStyle.FORM -> true
            else -> false
        }
        return OpenApiParameter(
            name = OpenApiParameterName(getContent("name")),
            `in` = `in`,
            description = getDescriptionOrNull("description"),
            required = getBooleanOrNull("required") ?: defaultRequired,
            deprecated = getBooleanOrFalse("deprecated"),
            allowEmptyValue = getBooleanOrFalse("allowEmptyValue"),
            style = style,
            explode = getBooleanOrNull("explode") ?: defaultExplode,
            allowReserved = getBooleanOrFalse("allowReserved"),
            schema = getObject("schema") { it.toOpenApiSchemaReference() },
            example = getObject("example") { OpenApiExampleValue(it.toOpenApiValue()) },
            examples = getObjectMapOrEmpty("examples") { it.toOpenApiExampleReference() },
            content = getObjectMapOrEmpty("content") { it.toOpenApiMediaType() }.mapKeys { entry -> OpenApiMediaTypeName(entry.key) },
            extensions = getOpenApiExtensions()
        )
    }

    private fun JsonObject.toOpenApiParameterReference(): OpenApiReferenceOf<OpenApiParameter> {
        if (containsKey("\$ref")) {
            val reference = toOpenApiReference()
            return OpenApiReferenceOf(reference)
        }

        val parameter = toOpenApiParameter()
        return OpenApiReferenceOf(parameter)
    }

    fun JsonObject.toOpenApiSchema(): OpenApiSchema = OpenApiSchemaComposite(
        example = getObject("example") { OpenApiExampleValue(it.toOpenApiValue()) },
        examples = getListOrEmpty("examples") { OpenApiExampleValue(it.toOpenApiValue()) },
        title = getContentOrNull("title"),
        description = getDescriptionOrNull("description"),
        readOnly = getBooleanOrFalse("readOnly"),
        writeOnly = getBooleanOrFalse("writeOnly"),
        default = getOpenApiValueOrNull("default"),
        deprecated = getBooleanOrFalse("deprecated"),
        `$comment` = getContentOrNull("\$comment"),
        types = getContentListOrEmpty("types").map { content -> content.decode<OpenApiSchemaType>() }.toSet(),
        format = getContentOrNull("format"),
        externalDocs = getExternalDocumentationOrNull("externalDocs"),
        extensions = getOpenApiExtensions(),
        enum = getObjectListOrEmpty("enum") { it.toOpenApiValue() },
        const = getOpenApiValueOrNull("const"),
        allOf = getObjectListOrEmpty("allOf") { it.toOpenApiSchema() },
        anyOf = getObjectListOrEmpty("anyOf") { it.toOpenApiSchema() },
        oneOf = getObjectListOrEmpty("oneOf") { it.toOpenApiSchema() },
        not = getObjectOrNull("not") { it.toOpenApiSchema() },
        `if` = getObjectOrNull("if") { it.toOpenApiSchema() },
        then = getObjectOrNull("then") { it.toOpenApiSchema() },
        `else` = getObjectOrNull("else") { it.toOpenApiSchema() },
        items = getObjectOrNull("items") { it.toOpenApiSchemaReference() },
        prefixItems = getObjectListOrEmpty("prefixItems") { it.toOpenApiSchemaReference() },
        contains = getObjectOrNull("contains") { it.toOpenApiSchemaReference() },
        minContains = getIntOrNull("minContains"),
        maxContains = getIntOrNull("maxContains"),
        minItems = getIntOrNull("minItems"),
        maxItems = getIntOrNull("maxItems"),
        uniqueItems = getBooleanOrNull("uniqueItems"),
        discriminator = getObjectOrNull("discriminator") { it.toOpenApiDiscriminator() },
        xml = getObjectOrNull("xml") { it.toOpenApiXml() },
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

    fun JsonObject.toOpenApiXml(): OpenApiXml = OpenApiXml(
        name = getContentOrNull("name"),
        namespace = getContentOrNull("namespace"),
        prefix = getContentOrNull("prefix"),
        attribute = getBooleanOrFalse("attribute"),
        wrapped = getBooleanOrFalse("wrapped"),
        extensions = getOpenApiExtensions()
    )

    fun JsonObject.toOpenApiDiscriminator(): OpenApiDiscriminator = OpenApiDiscriminator(
        propertyName = getContent("propertyName"),
        mapping = getContentMapOrEmpty("mapping").mapValues { entry -> OpenApiReferenceIdentifier(entry.value) },
        extensions = getOpenApiExtensions()
    )

    fun JsonObject.toOpenApiReference(): OpenApiReference = OpenApiReference(
        `$ref` = OpenApiReferenceIdentifier(getContent("\$ref")),
        summary = getContentOrNull("summary"),
        description = getDescriptionOrNull("description")
    )

    fun JsonObject.toOpenApiExternalDocumentation(): OpenApiExternalDocumentation = OpenApiExternalDocumentation(
        description = getDescriptionOrNull("description"),
        url = getUrl("url"),
        extensions = getOpenApiExtensions()
    )

    fun JsonObject.getOpenApiExtensions(): OpenApiExtensions {
        val prefixed = filterKeys { key -> key.startsWith("x-") }
        return prefixed.mapValues { entry -> entry.value.toOpenApiValue().toOpenApiExtension() }
    }

    fun JsonObject.toOpenApiSchemaReference(): OpenApiSchemaReference {
        if (containsKey("\$ref")) {
            val reference = toOpenApiReference()
            return OpenApiReferenceOf(reference)
        }

        val schema = toOpenApiSchema()
        return OpenApiReferenceOf(schema)
    }

    fun JsonObject.toOpenApiExample(): OpenApiExample = OpenApiExample(
        summary = getContentOrNull("summary"),
        description = getDescriptionOrNull("description"),
        value = getOpenApiValueOrNull("value"),
        externalValue = getContentOrNull("externalValue"),
        extensions = getOpenApiExtensions()
    )

    private fun JsonObject.toOpenApiExampleReference(): OpenApiReferenceOf<OpenApiExample> {
        if (containsKey("\$ref")) {
            val reference = toOpenApiReference()
            return OpenApiReferenceOf(reference)
        }

        val example = toOpenApiExample()
        return OpenApiReferenceOf(example)
    }

    fun JsonObject.toOpenApiMediaType(): OpenApiMediaType = OpenApiMediaType(
        schema = getObjectOrNull("schema") { it.toOpenApiSchema() },
        example = getObject("example") { OpenApiExampleValue(it.toOpenApiValue()) },
        examples = getObjectMapOrEmpty("examples") { it.toOpenApiExampleReference() },
        encoding = getObjectMapOrEmpty("encoding") { it.toOpenApiEncoding() }.mapKeys { entry -> OpenApiEncodingName(entry.key) },
        extensions = getOpenApiExtensions()
    )

    fun JsonObject.toOpenApiEncoding(): OpenApiEncoding = OpenApiEncoding(
        contentType = getContentOrNull("contentType"),
        headers = getObjectMapOrEmpty("headers") { it.toOpenApiHeaderReference() },
        style = getContentOrNull("style")?.decode(),
        explode = getBooleanOrNull("explode")
    )

    fun JsonObject.toOpenApiHeader(): OpenApiHeader {
        val style = getContentOrNull("style")?.decode() ?: OpenApiParameterStyle.SIMPLE
        val defaultExplode = when (style) {
            OpenApiParameterStyle.FORM -> true
            else -> false
        }
        return OpenApiHeader(
            description = getDescriptionOrNull("description"),
            required = getBooleanOrFalse("required"),
            deprecated = getBooleanOrFalse("deprecated"),
            allowEmptyValue = getBooleanOrFalse("allowEmptyValue"),
            style = style,
            explode = getBooleanOrNull("explode") ?: defaultExplode,
            allowReserved = getBooleanOrFalse("allowReserved"),
            schema = getObject("schema") { it.toOpenApiSchemaReference() },
            example = getObject("example") { OpenApiExampleValue(it.toOpenApiValue()) },
            examples = getObjectMapOrEmpty("examples") { it.toOpenApiExampleReference() },
            content = getObjectMapOrEmpty("content") { it.toOpenApiMediaType() }.mapKeys { entry -> OpenApiMediaTypeName(entry.key) },
            extensions = getOpenApiExtensions()
        )
    }

    private fun JsonObject.toOpenApiHeaderReference(): OpenApiReferenceOf<OpenApiHeader> {
        if (containsKey("\$ref")) {
            val reference = toOpenApiReference()
            return OpenApiReferenceOf(reference)
        }

        val header = toOpenApiHeader()
        return OpenApiReferenceOf(header)
    }

    fun JsonElement.toOpenApiValue(): OpenApiValue = when (this) {
        is JsonNull -> OpenApiNull
        is JsonPrimitive -> toOpenApiValue()
        is JsonObject -> toOpenApiValue()
        is JsonArray -> toOpenApiValue()
    }

    fun JsonArray.toOpenApiValue(): OpenApiValue {
        val values = map { value -> value.toOpenApiValue() }
        return OpenApiList(values)
    }

    fun JsonObject.toOpenApiValue(): OpenApiValue {
        val entries = mapValues { entry -> entry.value.toOpenApiValue() }
        return OpenApiMap(entries)
    }

    fun JsonPrimitive.toOpenApiValue(): OpenApiValue = when {
        this is JsonNull -> OpenApiNull
        isString -> OpenApiString(content) // Need to check isString before the rest to verify we don't have a quoted literal
        intOrNull != null -> OpenApiNumber(int)
        longOrNull != null -> OpenApiNumber(long)
        floatOrNull != null -> OpenApiNumber(float)
        doubleOrNull != null -> OpenApiNumber(double)
        booleanOrNull != null -> OpenApiBoolean(boolean)
        else -> OpenApiString(content)
    }

    //region
    private fun OpenApiValue.toOpenApiExtension() = OpenApiExtension(this)

    private fun JsonObject.getDependentRequired(key: String): Map<OpenApiPropertyName, Set<OpenApiPropertyName>> {
        val value = get(key) ?: return emptyMap()
        val map = value.jsonObject.toMap { element -> element.toContentList().decode<OpenApiPropertyName>().toSet() }
        return map.mapKeys { entry -> OpenApiPropertyName(entry.key) }
    }

    private fun JsonObject.getOpenApiValueOrNull(key: String): OpenApiValue? {
        val value = get(key) ?: return null
        return value.toOpenApiValue()
    }

    private fun JsonObject.getReferenceIdentifierOrNull(key: String): OpenApiReferenceIdentifier? {
        val value = getContentOrNull(key) ?: return null
        return OpenApiReferenceIdentifier(value)
    }

    private fun JsonObject.getEmailOrNull(key: String): OpenApiEmail? {
        val value = getContentOrNull(key) ?: return null
        return OpenApiEmail(value)
    }

    private fun JsonObject.getUrl(key: String): OpenApiUrl {
        val value = getContent(key)
        return OpenApiUrl(value)
    }

    private fun JsonObject.getUrlOrNull(key: String): OpenApiUrl? {
        val value = getContentOrNull(key) ?: return null
        return OpenApiUrl(value)
    }

    private fun JsonObject.getDescriptionOrNull(key: String): OpenApiDescription? {
        val value = getContentOrNull(key) ?: return null
        return OpenApiDescription(value)
    }

    private fun JsonObject.getExternalDocumentationOrNull(key: String): OpenApiExternalDocumentation? = getObjectOrNull(key) {
        it.toOpenApiExternalDocumentation()
    }
    //endregion
}