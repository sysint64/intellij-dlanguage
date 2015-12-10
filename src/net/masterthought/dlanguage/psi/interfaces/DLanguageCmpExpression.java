// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.interfaces;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageCmpExpression extends PsiElement {

  @Nullable
  DLanguageEqualExpression getEqualExpression();

  @Nullable
  DLanguageIdentityExpression getIdentityExpression();

  @Nullable
  DLanguageInExpression getInExpression();

  @Nullable
  DLanguageRelExpression getRelExpression();

  @Nullable
  DLanguageShiftExpression getShiftExpression();

}
