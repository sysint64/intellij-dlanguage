package io.github.intellij.dlanguage.types

import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import io.github.intellij.dlanguage.psi.DLanguageBuiltinType
import io.github.intellij.dlanguage.psi.DlangTypes
import io.github.intellij.dlanguage.resolve.DResolveUtil
import io.github.intellij.dlanguage.utils.*

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
    if (!checkTemplateArgumentsList(t1, t2))
        return false

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

fun getTemplateArgumentList(psi: PsiElement) : TemplateArgumentList? {
    return PsiTreeUtil.findChildOfType(psi, TemplateArgumentList::class.java)
}

fun getTemplateArguments(psi: PsiElement) : TemplateArguments? {
    return PsiTreeUtil.findChildOfType(psi, TemplateArguments::class.java)
}

private fun checkTemplateArgumentsList(t1: PsiElement, t2: PsiElement): Boolean {
    val a1 = getTemplateArguments(t1)
    val a2 = getTemplateArguments(t2)

    if (a1 == null && a2 == null)
        return true

    if (a1 == null || a2 == null)
        return false

    if (a1.templateSingleArgument != null) {
        if (a2.templateSingleArgument == null)
            return false

        return equalTypes(a1.templateSingleArgument, a2.templateSingleArgument)
    }

    val a1List = getTemplateArgumentList(a1) ?: return false
    val a2List = getTemplateArgumentList(a2) ?: return false

    if (a1List.templateArguments.size != a2List.templateArguments.size)
        return false

    for (i in 0 until a1List.templateArguments.size) {
        val arg1 = a1List.templateArguments[i]
        val arg2 = a2List.templateArguments[i]

        if (!equalTypes(arg1.type, arg2.type))
            return false
    }

    return true
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
    return when {
        type is TemplateSingleArgument -> {
            // Not sure about this, but with builin types we don't have identifier
            PsiTreeUtil.findChildOfType(type, Identifier::class.java) == null
        }
        isFunctionLiteral(type) -> false
        type is Type -> type.type_2?.children?.firstOrNull() is DLanguageBuiltinType
        type is Type_2 -> type.children.firstOrNull() is DLanguageBuiltinType
        else -> false
    }
}
