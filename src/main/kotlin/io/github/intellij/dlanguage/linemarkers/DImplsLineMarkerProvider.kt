package io.github.intellij.dlanguage.linemarkers

import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.codeInsight.daemon.LineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import io.github.intellij.dlanguage.icons.DlangIcons
import io.github.intellij.dlanguage.resolve.DResolveUtil
import io.github.intellij.dlanguage.types.equalTypes
import io.github.intellij.dlanguage.utils.*

class DImplsLineMarkerProvider : LineMarkerProvider {
    override fun getLineMarkerInfo(element: PsiElement): LineMarkerInfo<PsiElement>? = null

    override fun collectSlowLineMarkers(elements: MutableList<PsiElement>, result: MutableCollection<LineMarkerInfo<PsiElement>>) {
        for (el in elements) {
//            val targets: NotNullLazyValue<Collection<PsiElement>> = NotNullLazyValue.createValue { query }
            if (el !is FunctionDeclaration)
                continue

            val (source, target) = getTargets(el) ?: continue

            val infoBuilder = NavigationGutterIconBuilder
                .create(DlangIcons.IMPLEMENTING_METHOD)
                .setTarget(target)
                .setPopupTitle("Go to declaration")

            val identifier = PsiTreeUtil.findChildOfType(source, Identifier::class.java)

            if (identifier != null) {
                infoBuilder.setTooltipText("Implements function in '${identifier.text.trim()}'")
            } else {
                infoBuilder.setTooltipText("Implements function")
            }

            result.add(infoBuilder.createLineMarkerInfo(el))
        }
    }

    private fun getTargets(declaration: FunctionDeclaration): Pair<PsiElement, PsiElement>? {
        val classDeclarationParent = PsiTreeUtil.findFirstParent(declaration) {
            it is ClassDeclaration
        } ?: return null

        val classDeclaration = classDeclarationParent as ClassDeclaration

        val resolver = DResolveUtil.getInstance(declaration.project)
        val baseClasses = classDeclaration.interfaceOrClass?.baseClassList?.baseClasss ?: return null

        for (cls in baseClasses) {
            val baseIdentifier = PsiTreeUtil.findChildOfType(cls, Identifier::class.java) ?: continue
            val nodes = resolver.findDefinitionNode(baseIdentifier, false)

            for (node in nodes) {
                val pair = findDeclaration(declaration, node.parent)
                if (pair != null)
                    return pair
            }
        }

        return null
    }

    private fun findDeclaration(function: FunctionDeclaration, psi: PsiElement): Pair<PsiElement, PsiElement>? {
        return when (psi) {
            is InterfaceDeclaration -> findDeclarationInInterface(function, psi)
            else -> null
        }
    }

    private fun findDeclarationInInterface(function: FunctionDeclaration, declaration: InterfaceDeclaration): Pair<PsiElement, PsiElement>? {
        val methods = PsiTreeUtil.findChildrenOfType(declaration, FunctionDeclaration::class.java)

        for (method in methods) {
            if (equalFunctionDeclarations(method, function))
                return Pair(declaration, method)
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
}
