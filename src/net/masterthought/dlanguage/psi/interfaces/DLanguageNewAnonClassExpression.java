// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.interfaces;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageNewAnonClassExpression extends PsiElement {

  @NotNull
  DLanguageAggregateBody getAggregateBody();

  @Nullable
  DLanguageAllocatorArguments getAllocatorArguments();

  @Nullable
  DLanguageClassArguments getClassArguments();

  @Nullable
  DLanguageInterfaces getInterfaces();

  @Nullable
  DLanguageSuperClass getSuperClass();

  @NotNull
  PsiElement getKwClass();

  @NotNull
  PsiElement getKwNew();

}
