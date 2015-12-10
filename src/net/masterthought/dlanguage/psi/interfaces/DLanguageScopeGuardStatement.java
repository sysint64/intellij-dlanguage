// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.interfaces;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageScopeGuardStatement extends PsiElement {

  @NotNull
  DLanguageStatement getStatement();

  @NotNull
  PsiElement getKwScope();

  @NotNull
  PsiElement getOpParLeft();

  @NotNull
  PsiElement getOpParRight();

}
