// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.interfaces;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DLanguageSymbol extends DNamedElement {

    @NotNull
    DLanguageSymbolTail getSymbolTail();

    @Nullable
    PsiElement getOpDot();

    @Nullable
    PsiReference getReference();

    @NotNull
    String getName();

    @NotNull
    PsiElement setName(String newName);

    @NotNull
    PsiElement getNameIdentifier();

}
