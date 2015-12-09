// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import net.masterthought.dlanguage.psi.*;

public class DLanguageAsmXorExpImpl extends ASTWrapperPsiElement implements DLanguageAsmXorExp {

  public DLanguageAsmXorExpImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitAsmXorExp(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public DLanguageAsmAndExp getAsmAndExp() {
    return findNotNullChildByClass(DLanguageAsmAndExp.class);
  }

  @Override
  @Nullable
  public DLanguageAsmXorExp getAsmXorExp() {
    return findChildByClass(DLanguageAsmXorExp.class);
  }

  @Override
  @Nullable
  public PsiElement getOpXor() {
    return findChildByType(OP_XOR);
  }

}
