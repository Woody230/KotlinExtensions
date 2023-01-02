package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.reference.path.*
import com.bselzer.ktx.serialization.context.JsonContext.Default.decodeOrNull
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

internal open class OpenApiReferencePathSerializer<TReferencePath> : KSerializer<TReferencePath> where TReferencePath : OpenApiReferencePath {
    private companion object {
        private const val localReferencePathStart: String = "#/components"
    }

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("OpenApiReferencePath", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): TReferencePath = deserialize(decoder.decodeString())

    internal fun deserialize(value: String): TReferencePath {
        require(value.contains(localReferencePathStart)) { "Local reference path missing in `$this`." }

        // Should only have the component and component name.
        val localReferenceSegments = value.substringAfter(localReferencePathStart, "").split("/")
        require(localReferenceSegments.size == 2) { "Expecting exactly 2 components within the local reference path in `$this`." }

        val component = localReferenceSegments[0].decodeOrNull<ReferencePathComponent>()
        requireNotNull(component) { "Local reference path component must be one of `${ReferencePathComponent.values().joinToString()}` in path `$this`." }

        val componentName = localReferenceSegments[1]
        require(componentName.isNotBlank()) { "Local reference path component name is not provided in `$this`." }

        var documentPath: String? = value.substringBefore(localReferencePathStart, "")
        documentPath = documentPath?.ifBlank { null }

        val referencePath = createReferencePath(documentPath, component, componentName)
        return referencePath as TReferencePath
    }

    private fun createReferencePath(documentPath: String?, component: ReferencePathComponent, componentName: String): OpenApiReferencePath = when (component) {
        ReferencePathComponent.SCHEMA -> SchemaReferencePath(documentPath, componentName)
        ReferencePathComponent.RESPONSE -> ResponseReferencePath(documentPath, componentName)
        ReferencePathComponent.PARAMETER -> ParameterReferencePath(documentPath, componentName)
        ReferencePathComponent.EXAMPLE -> ExampleReferencePath(documentPath, componentName)
        ReferencePathComponent.REQUEST_BODY -> RequestBodyReferencePath(documentPath, componentName)
        ReferencePathComponent.HEADER -> HeaderReferencePath(documentPath, componentName)
        ReferencePathComponent.SECURITY_SCHEME -> SecuritySchemeReferencePath(documentPath, componentName)
        ReferencePathComponent.LINK -> LinkReferencePath(documentPath, componentName)
        ReferencePathComponent.CALLBACK -> CallbackReferencePath(documentPath, componentName)
        ReferencePathComponent.PATH_ITEM -> PathItemReferencePath(documentPath, componentName)
    }

    override fun serialize(encoder: Encoder, value: TReferencePath) {
        encoder.encodeString(value.toString())
    }
}