// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.interfaces;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageDeclarators extends PsiElement {

  @Nullable
  DLanguageDeclaratorIdentifierList getDeclaratorIdentifierList();

  @NotNull
  DLanguageDeclaratorInitializer getDeclaratorInitializer();

  @Nullable
  PsiElement getOpComma();

}
