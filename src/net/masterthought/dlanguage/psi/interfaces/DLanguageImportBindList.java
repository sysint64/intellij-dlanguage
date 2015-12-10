// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.interfaces;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageImportBindList extends PsiElement {

  @NotNull
  DLanguageImportBind getImportBind();

  @Nullable
  DLanguageImportBindList getImportBindList();

  @Nullable
  PsiElement getOpComma();

}
