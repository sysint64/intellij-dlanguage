// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.stubs.IStubElementType;
import net.masterthought.dlanguage.psi.interfaces.DLanguageFuncDeclaration;
import net.masterthought.dlanguage.psi.interfaces.DLanguageTypes;
import net.masterthought.dlanguage.psi.interfaces.DLanguageVisitor;
import net.masterthought.dlanguage.stubs.DLanguageFuncDeclarationStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DLanguageFuncDeclarationImpl extends DNamedStubbedPsiElementBase<DLanguageFuncDeclarationStub> implements DLanguageFuncDeclaration {

    public DLanguageFuncDeclarationImpl(ASTNode node) {
        super(node);
    }

    public DLanguageFuncDeclarationImpl(DLanguageFuncDeclarationStub stub, IStubElementType nodeType) {
        super(stub, nodeType);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor) visitor).visitFuncDeclaration(this);
        else super.accept(visitor);
    }

    @Nullable
    public PsiElement getNameIdentifier() {
        return DPsiImplUtil.getNameIdentifier(this);
    }

    @NotNull
    public String getName() {
        return DPsiImplUtil.getName(this);
    }

    @Nullable
    public PsiElement setName(String newName) {
        return DPsiImplUtil.setName(this, newName);
    }

    // child element of this element
    @Override
    @NotNull
    public PsiElement getSymbol() {
        return findNotNullChildByType(DLanguageTypes.SYMBOL);
    }

    @NotNull
    public ItemPresentation getPresentation() {
        return DPsiImplUtil.getPresentation(this);
    }



}
