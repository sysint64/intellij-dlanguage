package io.github.intellij.dlanguage.types

import com.intellij.psi.util.PsiTreeUtil
import io.github.intellij.dlanguage.utils.FunctionDeclaration
import io.github.intellij.dlanguage.utils.Parameter

fun checkFunctionParameters(p1: Collection<Parameter>, p2: Collection<Parameter>): Boolean {
    val f1Parameters = p1.toTypedArray()
    val f2Parameters = p2.toTypedArray()

    if (f1Parameters.size != f2Parameters.size)
        return false

    for (i in 0 until f1Parameters.size) {
        if (!equalTypes(f1Parameters[i].type, f2Parameters[i].type))
            return false
    }

    return true
}

fun equalFunctionDeclarations(f1: FunctionDeclaration, f2: FunctionDeclaration): Boolean {
    if (!equalTypes(f1.type, f2.type))
        return false

    val f1Parameters = PsiTreeUtil.findChildrenOfType(f1, Parameter::class.java)
    val f2Parameters = PsiTreeUtil.findChildrenOfType(f2, Parameter::class.java)

    if (!checkFunctionParameters(f1Parameters, f2Parameters))
        return false

    return f1.name == f2.name
}
