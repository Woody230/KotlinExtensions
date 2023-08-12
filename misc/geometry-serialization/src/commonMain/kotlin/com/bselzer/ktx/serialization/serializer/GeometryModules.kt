package com.bselzer.ktx.serialization.serializer

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import kotlinx.serialization.modules.plus

object GeometryModules {
    val TWO_DIMENSIONAL = SerializersModule {
        contextual(DigonSerializer())
        contextual(Dimension2DSerializer())
        contextual(Point2DSerializer())
        contextual(QuadrilateralSerializer())
    }

    val THREE_DIMENSIONAL = SerializersModule {
        contextual(Point3DSerializer())
    }

    val ALL: SerializersModule = TWO_DIMENSIONAL + THREE_DIMENSIONAL
}