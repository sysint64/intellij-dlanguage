// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.interfaces;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageAllocator extends PsiElement {

  @Nullable
  DLanguageFunctionBody getFunctionBody();

  @NotNull
  DLanguageParameters getParameters();

  @NotNull
  PsiElement getKwNew();

  @Nullable
  PsiElement getOpScolon();

}
