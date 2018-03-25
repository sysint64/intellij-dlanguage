package io.github.intellij.dlanguage.types

import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import io.github.intellij.dlanguage.psi.DLanguageBuiltinType
import io.github.intellij.dlanguage.psi.DlangTypes
import io.github.intellij.dlanguage.resolve.DResolveUtil
import io.github.intellij.dlanguage.utils.Identifier
import io.github.intellij.dlanguage.utils.Type

fun equalTypes(t1: PsiElement?, t2: PsiElement?): Boolean {
    return when {
        t1 == null && t2 == null -> true
        t1 == null || t2 == null -> false
        isFunctionLiteral(t1) && isFunctionLiteral(t2) -> checkFunctionLiterals(t1, t2)
        isBuiltinType(t1) && isBuiltinType(t2) -> t1.text.trim() == t2.text.trim()
        else -> checkReferences(t1, t2)
    }
}

private fun checkFunctionLiterals(t1: PsiElement, t2: PsiElement): Boolean {
    if (t1 !is Type || t2 !is Type)
        return false

    // Check return type
    if (!equalTypes(t1.type_2, t2.type_2))
        return false

    // Check arguments
    val typeSuffix1 = t1.typeSuffixs.firstOrNull() ?: return false
    val typeSuffix2 = t2.typeSuffixs.firstOrNull() ?: return false

    val p1 = typeSuffix1.parameters?.parameters ?: return false
    val p2 = typeSuffix2.parameters?.parameters ?: return false

    return checkFunctionParameters(p1, p2)
}

private fun checkReferences(t1: PsiElement, t2: PsiElement): Boolean {
    val resolver1 = DResolveUtil.getInstance(t1.project)
    val resolver2 = DResolveUtil.getInstance(t2.project)

    val t1Identifier = PsiTreeUtil.findChildOfType(t1, Identifier::class.java) ?: return false
    val t2Identifier = PsiTreeUtil.findChildOfType(t2, Identifier::class.java) ?: return false

    val set1 = resolver1.findDefinitionNode(t1Identifier, false)
    val set2 = resolver2.findDefinitionNode(t2Identifier, false)

    if (set1.isEmpty() || set2.isEmpty())
        return false

    for (i1 in set1) {
        for (i2 in set2) {
            if (i1 == i2)
                return true
        }
    }

    return false
}

fun isFunctionLiteral(type: PsiElement): Boolean {
    if (type !is Type)
        return false

    val typeSuffix = type.typeSuffixs.firstOrNull() ?: return false

    if (typeSuffix.node.findChildByType(DlangTypes.KW_DELEGATE) != null)
        return true

    if (typeSuffix.node.findChildByType(DlangTypes.KW_FUNCTION) != null)
        return true

    return false
}

fun isBuiltinType(type: PsiElement): Boolean {
    return PsiTreeUtil.findChildOfType(type, DLanguageBuiltinType::class.java) != null &&
        !isFunctionLiteral(type)
}
