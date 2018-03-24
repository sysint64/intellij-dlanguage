package io.github.intellij.dlanguage.linemarkers

import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.codeInsight.daemon.LineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import io.github.intellij.dlanguage.icons.DlangIcons
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

    private fun equalFunctionDeclarations(method: FunctionDeclaration, function: FunctionDeclaration) : Boolean {
        val methodIdentifier = method.identifier ?: return false

        return methodIdentifier.name == function.name
    }
}
