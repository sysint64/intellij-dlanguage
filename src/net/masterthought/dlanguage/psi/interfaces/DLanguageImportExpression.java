// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.interfaces;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageImportExpression extends PsiElement {

  @NotNull
  DLanguageAssignExpression getAssignExpression();

  @NotNull
  PsiElement getKwImport();

  @NotNull
  PsiElement getOpParLeft();

  @NotNull
  PsiElement getOpParRight();

}
