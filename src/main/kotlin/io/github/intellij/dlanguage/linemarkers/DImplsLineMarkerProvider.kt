package io.github.intellij.dlanguage.linemarkers

import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.codeInsight.daemon.LineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import io.github.intellij.dlanguage.icons.DlangIcons
import io.github.intellij.dlanguage.psi.DLanguageBuiltinType
import io.github.intellij.dlanguage.resolve.DResolveUtil
import io.github.intellij.dlanguage.utils.*

class DImplsLineMarkerProvider : LineMarkerProvider {
    override fun getLineMarkerInfo(element: PsiElement): LineMarkerInfo<PsiElement>? = null

    override fun collectSlowLineMarkers(elements: MutableList<PsiElement>, result: MutableCollection<LineMarkerInfo<PsiElement>>) {
        for (el in elements) {
//            val targets: NotNullLazyValue<Collection<PsiElement>> = NotNullLazyValue.createValue { query }
            if (el !is FunctionDeclaration)
                continue

            val targets = getTargets(el)

            if (targets == null || targets.isEmpty())
                continue

            val info = NavigationGutterIconBuilder
                .create(DlangIcons.IMPLEMENTING_METHOD)
                .setTargets(targets)
                .setPopupTitle("Go to declaration")
                .setTooltipText("Implements function")
                .createLineMarkerInfo(el)

            result.add(info)
        }
    }

    private fun getTargets(declaration: FunctionDeclaration): Set<PsiElement>? {
        val set = HashSet<PsiElement>()

        val classDeclarationParent = PsiTreeUtil.findFirstParent(declaration) {
            it is ClassDeclaration
        } ?: return null

        val classDeclaration = classDeclarationParent as ClassDeclaration

        val resolver = DResolveUtil.getInstance(declaration.project)
        val baseClasses = classDeclaration.interfaceOrClass?.baseClassList?.baseClasss ?: return null

        for (cls in baseClasses) {
            val baseIdentifier = PsiTreeUtil.findChildOfType(cls, Identifier::class.java) ?: continue
            val nodes = resolver.findDefinitionNode(baseIdentifier, false)

            nodes.mapNotNullTo(set) { findDeclaration(declaration, it.parent) }
//            set.addAll(nodes)
        }

//        resolver.findDefinitionNode(identifier, false)

        return set
    }

    private fun findDeclaration(function: FunctionDeclaration, psi: PsiElement): PsiElement? {
        return when (psi) {
            is InterfaceDeclaration -> findDeclarationInInterface(function, psi)
            else -> null
        }
    }

    private fun findDeclarationInInterface(function: FunctionDeclaration, declaration: InterfaceDeclaration): PsiElement? {
        val methods = PsiTreeUtil.findChildrenOfType(declaration, FunctionDeclaration::class.java)

        for (method in methods) {
            if (equalFunctionDeclarations(method, function))
                return method
        }

        // Declaration was not found, try go deeper
        val baseClasses = declaration.interfaceOrClass?.baseClassList?.baseClasss ?: return null
        val resolver = DResolveUtil.getInstance(declaration.project)

        for (cls in baseClasses) {
            val baseIdentifier = PsiTreeUtil.findChildOfType(cls, Identifier::class.java) ?: continue
            val nodes = resolver.findDefinitionNode(baseIdentifier, false)

            for (node in nodes) {
                val result = findDeclaration(function, node.parent)
                if (result != null)
                    return result
            }
        }

        return null
    }

    private fun equalFunctionDeclarations(f1: FunctionDeclaration, f2: FunctionDeclaration): Boolean {
        if (!equalTypes(f1.type, f2.type))
            return false

        val f1Parameters = PsiTreeUtil.findChildrenOfType(f1, Parameter::class.java).toTypedArray()
        val f2Parameters = PsiTreeUtil.findChildrenOfType(f2, Parameter::class.java).toTypedArray()

        if (f1Parameters.size != f2Parameters.size)
            return false

        for (i in 0 until f1Parameters.size) {
            if (!equalTypes(f1Parameters[i].type, f2Parameters[i].type))
                return false
        }

        return f1.name == f2.name
    }

    private fun equalTypes(t1: Type?, t2: Type?): Boolean {
        return when {
            t1 == null && t2 == null -> true
            t1 == null || t2 == null -> false
            isBuiltinType(t1) && isBuiltinType(t2) -> t1.text.trim() == t2.text.trim()
            else -> {
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

                false
            }
        }
    }

    private fun isBuiltinType(type: Type): Boolean {
        return !PsiTreeUtil.findChildrenOfType(type, DLanguageBuiltinType::class.java).isEmpty()
    }
}
