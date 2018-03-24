package io.github.intellij.dlanguage.resolve

import com.intellij.psi.PsiElement
import com.intellij.util.AbstractQuery
import com.intellij.util.Processor
import com.intellij.util.Query
import io.github.intellij.dlanguage.psi.DLanguageClassDeclaration

/**
 * Finds implementations for psi node
 */

fun DLanguageClassDeclaration.searchForImplementations() : Set<PsiElement>? {
    val resolver = DResolveUtil.getInstance(project)
    val baseClasses = interfaceOrClass?.baseClassList?.baseClasss ?: return null
    val result = HashSet<PsiElement>()

    for (cls in baseClasses) {
        val instances = cls.identifierOrTemplateChain?.identifierOrTemplateInstances ?: continue
        val identifier = instances.firstOrNull()?.identifier ?: continue
        val nodes = resolver.findDefinitionNode(identifier, false)
        result.addAll(nodes)
    }

    return if (!result.isEmpty()) result else null
}

fun <U, V> Query<U>.mapQuery(f: (U) -> V) = object : AbstractQuery<V>() {
    override fun processResults(consumer: Processor<V>): Boolean {
        return this@mapQuery.forEach(Processor<U> { t -> consumer.process(f(t)) })
    }
}
