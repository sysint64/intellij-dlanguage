// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.interfaces;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageNonEmptyStatement extends PsiElement {

  @Nullable
  DLanguageCaseRangeStatement getCaseRangeStatement();

  @Nullable
  DLanguageCaseStatement getCaseStatement();

  @Nullable
  DLanguageDefaultStatement getDefaultStatement();

  @Nullable
  DLanguageNonEmptyStatementNoCaseNoDefault getNonEmptyStatementNoCaseNoDefault();

}
