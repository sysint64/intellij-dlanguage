// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.interfaces;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageOutStatement extends PsiElement {

  @NotNull
  DLanguageBlockStatement getBlockStatement();

  @Nullable
  DLanguageIdentifier getIdentifier();

  @NotNull
  PsiElement getKwOut();

  @Nullable
  PsiElement getOpParLeft();

  @Nullable
  PsiElement getOpParRight();

}
