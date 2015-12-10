package net.masterthought.dlanguage.psi.interfaces;

import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.stubs.DLanguageAggregateDeclarationStub;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageAggregateDeclaration extends DNamedElement, StubBasedPsiElement<DLanguageAggregateDeclarationStub> {

    @NotNull
    PsiElement getSymbol();

    @NotNull
    String getName();

    @Nullable
    PsiElement getNameIdentifier();

    @Nullable
    PsiElement setName(String newName);

    @NotNull
    ItemPresentation getPresentation();
}
