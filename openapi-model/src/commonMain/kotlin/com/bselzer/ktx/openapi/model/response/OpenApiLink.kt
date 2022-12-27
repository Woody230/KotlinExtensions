package com.bselzer.ktx.openapi.model.response

import com.bselzer.ktx.openapi.model.OpenApiDescription
import com.bselzer.ktx.openapi.model.OpenApiExtensible
import com.bselzer.ktx.openapi.model.OpenApiExtensions
import com.bselzer.ktx.openapi.model.OpenApiValueOrRuntimeExpression
import com.bselzer.ktx.openapi.model.parameter.OpenApiParameterName
import com.bselzer.ktx.openapi.model.path.OpenApiOperationId
import com.bselzer.ktx.openapi.model.reference.OpenApiReferencePath
import com.bselzer.ktx.openapi.model.server.OpenApiServer

/**
 * The Link object represents a possible design-time link for a response. The presence of a link does not guarantee the caller’s ability to successfully invoke it, rather it provides a known relationship and traversal mechanism between responses and other operations.
 *
 * Unlike dynamic links (i.e. links provided in the response payload), the OAS linking mechanism does not require link information in the runtime response.
 *
 * For computing links, and providing instructions to execute them, a runtime expression is used for accessing values in an operation and using them as parameters while invoking the linked operation.
 */
data class OpenApiLink(
    /**
     * A relative or absolute URI reference to an OAS operation. This field is mutually exclusive of the operationId field, and MUST point to an Operation Object. Relative operationRef values MAY be used to locate an existing Operation Object in the OpenAPI definition. See the rules for resolving Relative References.
     */
    val operationRef: OpenApiReferencePath,

    /**
     * The name of an existing, resolvable OAS operation, as defined with a unique operationId. This field is mutually exclusive of the operationRef field.
     */
    val operationId: OpenApiOperationId? = null,

    /**
     * A map representing parameters to pass to an operation as specified with operationId or identified via operationRef. The key is the parameter name to be used, whereas the value can be a constant or an expression to be evaluated and passed to the linked operation. The parameter name can be qualified using the parameter location [{in}.]{name} for operations that use the same parameter name in different locations (e.g. path.id).
     */
    val parameters: Map<OpenApiParameterName, OpenApiValueOrRuntimeExpression> = emptyMap(),

    /**
     * A literal value or {expression} to use as a request body when calling the target operation.
     */
    val requestBody: OpenApiValueOrRuntimeExpression? = null,

    /**
     * A description of the link. CommonMark syntax MAY be used for rich text representation.
     */
    val description: OpenApiDescription? = null,

    /**
     * A server object to be used by the target operation.
     */
    val server: OpenApiServer? = null,

    override val extensions: OpenApiExtensions = emptyMap()
) : OpenApiExtensible