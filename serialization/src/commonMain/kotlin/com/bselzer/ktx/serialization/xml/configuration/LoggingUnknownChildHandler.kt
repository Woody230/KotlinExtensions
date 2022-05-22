package com.bselzer.ktx.serialization.xml.configuration

import com.bselzer.ktx.logging.Logger
import nl.adaptivity.xmlutil.ExperimentalXmlUtilApi
import nl.adaptivity.xmlutil.QName
import nl.adaptivity.xmlutil.XmlReader
import nl.adaptivity.xmlutil.serialization.InputKind
import nl.adaptivity.xmlutil.serialization.UnknownChildHandler
import nl.adaptivity.xmlutil.serialization.XML
import nl.adaptivity.xmlutil.serialization.structure.XmlDescriptor

/**
 * An [UnknownChildHandler] that logs the failed deserialization of a child.
 */
@OptIn(ExperimentalXmlUtilApi::class)
class LoggingUnknownChildHandler : UnknownChildHandler {
    override fun handleUnknownChildRecovering(
        input: XmlReader,
        inputKind: InputKind,
        descriptor: XmlDescriptor,
        name: QName?,
        candidates: Collection<Any>
    ): List<XML.ParsedData<*>> {
        Logger.w("Unable to deserialize an unknown child of $name as $inputKind")
        return emptyList()
    }
}