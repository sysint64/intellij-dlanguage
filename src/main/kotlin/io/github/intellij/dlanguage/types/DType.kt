package io.github.intellij.dlanguage.types

/**
 * Base class for all types.
 * @param module: module where type is placed.
 */
abstract class DType(val module: String? = null) {
    abstract fun typeToString(): String
    override fun toString(): String = typeToString()

    open fun implicitConversionTo(toType: DType) = DTypeUnknown
}

class DTypeConst(val ty: DType) : DType() {
    override fun typeToString(): String = "const($ty)"
}

open class DTypeImmutable(val ty: DType) : DType() {
    override fun typeToString(): String = "immutable($ty)"
}

open class DTypeShared(val ty: DType) : DType() {
    override fun typeToString(): String = "shared($ty)"
}
