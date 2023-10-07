package com.shuzijun.plantumlparser.core;

import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.stubs.IStubElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.*;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KtClassVOidVisitor extends KtTreeVisitor<PUml> {

    private final MyVisitor myVisitor;

    public KtClassVOidVisitor(String packageName, ParserConfig parserConfig) {
        this.myVisitor = new MyVisitor(packageName,parserConfig);
    }

    @Override
    public Void visitClass( KtClass ktClass, PUml pUml) {

        if (!(pUml instanceof PUmlView)) {
            super.visitClassOrObject(ktClass, pUml);
            return null;
        }

        if (myVisitor.isExcludeClass(ktClass.getName())){
            return null;
        }


        PUmlView pUmlView = (PUmlView) pUml;
        PUmlClass pUmlClass = myVisitor.createUmlClass();

        pUmlClass.setClassName(ktClass.getName());
        if (ktClass.isInterface()) {
            pUmlClass.setClassType("interface");
        } else {
            if (ktClass.hasModifier(KtTokens.ABSTRACT_KEYWORD)) {
                pUmlClass.setClassType("abstract class");
            } else {
                pUmlClass.setClassType("class");
            }

        }

        if (myVisitor.isShowComment()) {
            if (ktClass.getDocComment() != null) {
                pUmlClass.setClassComment(ktClass.getDocComment().getText());
            }
        }

        ktClass.getProperties().forEach(p -> p.accept(this, pUmlClass));

        ktClass.getDeclarations().forEach( p -> {
            if (p instanceof KtConstructor){
                p.accept(this,pUmlClass);
            } else if (p instanceof KtNamedFunction){
                p.accept(this,pUmlClass);
            }
        });

       pUmlView.addPUmlClass(pUmlClass);



       PsiElement node = ktClass.getParent();

       List<KtImportDirective>  importDeclarations = parseImport(node, pUmlClass, pUmlView);

        Map<String, String> importMap = new HashMap<>();
        if (importDeclarations != null) {
            for (KtImportDirective importDeclaration : importDeclarations) {
                importMap.put(importDeclaration.getAliasName(), importDeclaration.getName());
            }
        }

        List<KtSuperTypeListEntry> superClassEntries = ktClass.getSuperTypeListEntries();
        for (KtSuperTypeListEntry superClassEntry : superClassEntries) {
            if (superClassEntry != null) {
                if (superClassEntry.getElementType() == KtStubElementTypes.SUPER_TYPE_ENTRY){
                        PUmlRelation pUmlRelation = new PUmlRelation();
                        pUmlRelation.setTarget(myVisitor.getPackageNamePrefix(pUmlClass.getPackageName()) + pUmlClass.getClassName());
                        if (importMap.containsKey(superClassEntry.getText())) {
                            if (myVisitor.isShowPackage()) {
                                pUmlRelation.setSource(importMap.get(superClassEntry.getText()));
                            } else {
                                pUmlRelation.setSource(superClassEntry.getText());
                            }
                        } else {
                            pUmlRelation.setSource(myVisitor.getPackageNamePrefix(pUmlClass.getPackageName()) + superClassEntry.getText());
                        }
                        pUmlRelation.setRelation("<|..");
                        pUmlView.addPUmlRelation(pUmlRelation);
                } else if (superClassEntry.getElementType() == KtStubElementTypes.SUPER_TYPE_CALL_ENTRY){
                    PUmlRelation pUmlRelation = new PUmlRelation();
                    pUmlRelation.setTarget(myVisitor.getPackageNamePrefix(pUmlClass.getPackageName()) + pUmlClass.getClassName());
                    if (importMap.containsKey(superClassEntry.getText())) {
                        if (myVisitor.isShowPackage()) {
                            pUmlRelation.setSource(importMap.get(superClassEntry.getText()));
                        } else {
                            pUmlRelation.setSource(superClassEntry.getText());
                        }
                    } else {
                        pUmlRelation.setSource(myVisitor.getPackageNamePrefix(pUmlClass.getPackageName()) + superClassEntry.getText());
                    }
                    pUmlRelation.setRelation("<|--");
                    pUmlView.addPUmlRelation(pUmlRelation);
               }

            }
        }
        return null;
    }

    @Override
    public Void visitProperty( KtProperty property, PUml pUml) {
        if (!(pUml instanceof PUmlClass)) {
            super.visitProperty(property, pUml);
            return null;
        }
        PUmlClass pUmlClass = (PUmlClass) pUml;
        PUmlField pUmlField = new PUmlField();

        pUmlField.setVisibility(VisibilityUtils.toVisibility(property));

        if (myVisitor.isFieldModifier(pUmlField.getVisibility())) {
            pUmlField.setType(getKtTypeReference(property.getTypeReference()));
            pUmlField.setName(property.getName());
            pUmlClass.addPUmlFieldList(pUmlField);
        }

        if (myVisitor.isShowComment()) {
            if (property.getDocComment() != null) {
                pUmlField.setComment(property.getDocComment().getText());
            }
        }
        return null;
    }

    private String getKtTypeReference(KtTypeReference reference)  {

        if (reference != null) {
            return reference.getText();
        }

        return  "Unknown";
    }

    public Void visitPrimaryConstructor(KtPrimaryConstructor constructor, PUml pUml) {
        return this.visitConstructor(constructor,pUml);
    }

    public Void visitSecondaryConstructor(KtSecondaryConstructor constructor, PUml pUml) {
        return this.visitConstructor(constructor,pUml);
    }

    public Void visitConstructor(KtConstructor constructor, PUml pUml) {
        if (!(pUml instanceof PUmlClass)) {
            return null;
        }
        if (!myVisitor.isShowConstructors()) {
            return null;
        }
        PUmlClass pUmlClass = (PUmlClass) pUml;
        PUmlMethod pUmlMethod = new PUmlMethod();


        pUmlMethod.setVisibility(VisibilityUtils.toVisibility(constructor));

        if (myVisitor.isMethodModifier(pUmlMethod.getVisibility())) {
            pUmlMethod.setReturnType("<<Create>>");
            pUmlMethod.setName(constructor.getName());

            for (Object parameter : constructor.getValueParameters()) {
                pUmlMethod.addParam(getKtTypeReference(((KtParameter)parameter).getTypeReference()));
            }
            pUmlClass.addPUmlMethodList(pUmlMethod);
        }
        if (myVisitor.isShowComment()) {
            if  (constructor.getDocComment() != null) {
                pUmlMethod.setComment(constructor.getDocComment().getText());
            }
        }

        return null;
    }

    @Override
    public Void visitNamedFunction( KtNamedFunction function, PUml pUml) {
        if (!(pUml instanceof PUmlClass)) {
            super.visitNamedFunction(function, pUml);
            return null;
        }
        PUmlClass pUmlClass = (PUmlClass) pUml;

        PUmlMethod pUmlMethod = new PUmlMethod();

        pUmlMethod.setVisibility(VisibilityUtils.toVisibility(function));

        if (myVisitor.isMethodModifier(pUmlMethod.getVisibility())) {
            pUmlMethod.setReturnType(getKtTypeReference(function.getTypeReference()));
            pUmlMethod.setName(function.getName());
            for (Object parameter : function.getValueParameters()) {
                pUmlMethod.addParam(getKtTypeReference(((KtParameter)parameter).getTypeReference()));
            }
            pUmlClass.addPUmlMethodList(pUmlMethod);
        }

        if (myVisitor.isShowComment()) {
            if (function.getDocComment() != null) {
                pUmlMethod.setComment(function.getDocComment().getText());
            }
        }
        return null;
    }

    @Override
    public Void visitKtElement( KtElement element, PUml pUml) {
        super.visitKtElement(element, pUml);
        return null;
    }

    @Override
    public Void visitKtFile( KtFile file, PUml pUml) {
        super.visitKtFile(file, pUml);
        return null;
    }

    @Override
    public Void visitDeclaration( KtDeclaration dcl, PUml pUml) {
        super.visitDeclaration(dcl, pUml);
        return null;
    }

    private List<KtImportDirective> parseImport(PsiElement node, PUmlClass pUmlClass, PUmlView pUmlView) {
        if (node instanceof KtFile) {
            return ((KtFile) node).getImportDirectives();
        } else if (node != null) {
            parseImport(node.getParent(), pUmlClass, pUmlView);
        }
        return null;
    }
}
