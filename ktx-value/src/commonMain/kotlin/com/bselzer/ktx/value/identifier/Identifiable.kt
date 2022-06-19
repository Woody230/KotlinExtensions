package com.bselzer.ktx.value.identifier

interface Identifiable<Id : Identifier<Value>, Value> {
    val id: Id
}