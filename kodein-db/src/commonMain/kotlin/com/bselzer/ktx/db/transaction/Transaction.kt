package com.bselzer.ktx.db.transaction

import org.kodein.db.Batch
import org.kodein.db.DBRead

/**
 * Represents an abstraction for writing a batch.
 */
interface Transaction : Batch, DBRead