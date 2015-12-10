// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.interfaces;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageDebugCondition extends PsiElement {

  @Nullable
  DLanguageIdentifier getIdentifier();

  @Nullable
  PsiElement getIntegerLiteral();

  @NotNull
  PsiElement getKwDebug();

  @Nullable
  PsiElement getOpParLeft();

  @Nullable
  PsiElement getOpParRight();

}
