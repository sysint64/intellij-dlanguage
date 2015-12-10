// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.interfaces;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageSliceExpression extends PsiElement {

  @Nullable
  DLanguageMultipleAssign getMultipleAssign();

  @Nullable
  DLanguagePostfixExpression getPostfixExpression();

  @NotNull
  PsiElement getOpBracketLeft();

  @NotNull
  PsiElement getOpBracketRight();

}
