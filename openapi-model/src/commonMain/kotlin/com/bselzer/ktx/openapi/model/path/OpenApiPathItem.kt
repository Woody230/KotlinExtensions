package com.bselzer.ktx.openapi.model.path

import com.bselzer.ktx.openapi.model.*
import com.bselzer.ktx.openapi.model.reference.OpenApiReferencePath

/**
 * Describes the operations available on a single path. A Path Item MAY be empty, due to ACL constraints. The path itself is still exposed to the documentation viewer but they will not know which operations and parameters are available.
 */
data class OpenApiPathItem(
    /**
     * Allows for a referenced definition of this path item. The referenced structure MUST be in the form of a Path Item Object. In case a Path Item Object field appears both in the defined object and the referenced object, the behavior is undefined. See the rules for resolving Relative References.
     */
    val `$ref`: OpenApiReferencePath? = null,

    /**
     * An optional, string summary, intended to apply to all operations in this path.
     */
    val summary: String? = null,

    /**
     * An optional, string description, intended to apply to all operations in this path. CommonMark syntax MAY be used for rich text representation.
     */
    val description: OpenApiDescription? = null,

    /**
     * A definition of a GET operation on this path.
     */
    val get: OpenApiOperation? = null,

    /**
     * A definition of a PUT operation on this path.
     */
    val put: OpenApiOperation? = null,

    /**
     * A definition of a POST operation on this path.
     */
    val post: OpenApiOperation? = null,

    /**
     * A definition of a DELETE operation on this path.
     */
    val delete: OpenApiOperation? = null,

    /**
     * A definition of a OPTIONS operation on this path
     */
    val options: OpenApiOperation? = null,

    /**
     * A definition of a HEAD operation on this path.
     */
    val head: OpenApiOperation? = null,

    /**
     * A definition of a PATCH operation on this path.
     */
    val patch: OpenApiOperation? = null,

    /**
     * A definition of a TRACE operation on this path.
     */
    val trace: OpenApiOperation? = null,

    /**
     * An alternative server array to service all operations in this path.
     */
    val servers: OpenApiServers = emptyList(),

    /**
     *  A list of parameters that are applicable for all the operations described under this path. These parameters can be overridden at the operation level, but cannot be removed there. The list MUST NOT include duplicated parameters. A unique parameter is defined by a combination of a name and location. The list can use the Reference Object to link to parameters that are defined at the OpenAPI Object’s components/parameters.
     */
    val parameters: OpenApiParameterList = emptyList(),

    override val extensions: OpenApiExtensions = emptyMap()
) : OpenApiExtensible