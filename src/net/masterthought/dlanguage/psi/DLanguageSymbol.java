// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;

import com.intellij.psi.PsiReference;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

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
