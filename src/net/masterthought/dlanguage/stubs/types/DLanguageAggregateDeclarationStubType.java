package net.masterthought.dlanguage.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.impl.DLanguageAggregateDeclarationImpl;
import net.masterthought.dlanguage.psi.impl.DLanguageFuncDeclarationImpl;
import net.masterthought.dlanguage.psi.interfaces.DLanguageAggregateDeclaration;
import net.masterthought.dlanguage.psi.interfaces.DLanguageFuncDeclaration;
import net.masterthought.dlanguage.psi.references.DUtil;
import net.masterthought.dlanguage.stubs.DLanguageAggregateDeclarationStub;
import net.masterthought.dlanguage.stubs.DLanguageFuncDeclarationStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DLanguageAggregateDeclarationStubType extends DNamedStubElementType<DLanguageAggregateDeclarationStub, DLanguageAggregateDeclaration> {
    public DLanguageAggregateDeclarationStubType(String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageAggregateDeclaration createPsi(@NotNull DLanguageAggregateDeclarationStub stub) {
        return new DLanguageAggregateDeclarationImpl(stub, this);
    }

    @Override
    public boolean shouldCreateStub(ASTNode node) {
        return DUtil.definitionNode(node);
    }

    @Override
    public DLanguageAggregateDeclarationStub createStub(@NotNull DLanguageAggregateDeclaration psi, StubElement parentStub) {
        return new DLanguageAggregateDeclarationStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull DLanguageAggregateDeclarationStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DLanguageAggregateDeclarationStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new DLanguageAggregateDeclarationStub(parentStub, this, dataStream.readName());
    }
}
