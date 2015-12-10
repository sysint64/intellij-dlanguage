package net.masterthought.dlanguage.psi.interfaces;

import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.stubs.DLanguageFuncDeclarationStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DLanguageFuncDeclaration extends DNamedElement, StubBasedPsiElement<DLanguageFuncDeclarationStub> {

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
