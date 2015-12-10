// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.interfaces;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageAndExpression extends PsiElement {

  @Nullable
  DLanguageAndExpression getAndExpression();

  @NotNull
  DLanguageShiftExpression getShiftExpression();

  @Nullable
  PsiElement getOpAnd();

}
