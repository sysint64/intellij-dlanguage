// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.interfaces;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageTemplateParameterList extends PsiElement {

  @NotNull
  DLanguageTemplateParameter getTemplateParameter();

  @Nullable
  DLanguageTemplateParameterList getTemplateParameterList();

  @Nullable
  PsiElement getOpComma();

}
