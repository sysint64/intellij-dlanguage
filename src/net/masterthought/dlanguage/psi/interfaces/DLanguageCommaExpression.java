// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.interfaces;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageCommaExpression extends PsiElement {

  @NotNull
  DLanguageAssignExpression getAssignExpression();

  @Nullable
  DLanguageCommaExpression getCommaExpression();

  @Nullable
  PsiElement getOpComma();

}
