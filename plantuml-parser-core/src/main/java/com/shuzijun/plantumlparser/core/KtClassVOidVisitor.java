package com.shuzijun.plantumlparser.core;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.*;

import java.util.HashMap;
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


        if (ktClass.getPrimaryConstructor() == null && myVisitor.isShowDefaultConstructors()) {
            // TODO
            //defaultConstructor.accept(this, pUmlClass);
        } else {
            ktClass.getPrimaryConstructor().accept(this,pUmlClass);
        }

        ktClass.getDeclarations().forEach(p -> {
            if (p instanceof KtNamedFunction){
                p.accept(this,pUmlClass);
            }
        });


       pUmlView.addPUmlClass(pUmlClass);



       //PsiElement node = ktClass.
//
//        NodeList<ImportDeclaration> importDeclarations = parseImport(node, pUmlClass, pUmlView);
//
//        Map<String, String> importMap = new HashMap<>();
//        if (importDeclarations != null) {
//            for (ImportDeclaration importDeclaration : importDeclarations) {
//                importMap.put(importDeclaration.getName().getIdentifier(), importDeclaration.getName().toString());
//            }
//        }
//        if (cORid.getImplementedTypes().size() != 0) {
//            for (ClassOrInterfaceType implementedType : cORid.getImplementedTypes()) {
//                PUmlRelation pUmlRelation = new PUmlRelation();
//                pUmlRelation.setTarget(getPackageNamePrefix(pUmlClass.getPackageName()) + pUmlClass.getClassName());
//                if (importMap.containsKey(implementedType.getNameAsString())) {
//                    if (parserConfig.isShowPackage()) {
//                        pUmlRelation.setSource(importMap.get(implementedType.getNameAsString()));
//                    } else {
//                        pUmlRelation.setSource(implementedType.getNameAsString());
//                    }
//                } else {
//                    pUmlRelation.setSource(getPackageNamePrefix(pUmlClass.getPackageName()) + implementedType.getNameAsString());
//                }
//                pUmlRelation.setRelation("<|..");
//                pUmlView.addPUmlRelation(pUmlRelation);
//            }
//        }
//
//        if (cORid.getExtendedTypes().size() != 0) {
//            for (ClassOrInterfaceType extendedType : cORid.getExtendedTypes()) {
//                PUmlRelation pUmlRelation = new PUmlRelation();
//                pUmlRelation.setTarget(getPackageNamePrefix(pUmlClass.getPackageName()) + pUmlClass.getClassName());
//                if (importMap.containsKey(extendedType.getNameAsString())) {
//                    if (parserConfig.isShowPackage()) {
//                        pUmlRelation.setSource(importMap.get(extendedType.getNameAsString()));
//                    } else {
//                        pUmlRelation.setSource(extendedType.getNameAsString());
//                    }
//                } else {
//                    pUmlRelation.setSource(getPackageNamePrefix(pUmlClass.getPackageName()) + extendedType.getNameAsString());
//                }
//                pUmlRelation.setRelation("<|--");
//                pUmlView.addPUmlRelation(pUmlRelation);
//
//            }
//        }
//        super.visit(cORid, pUml);
//
//
//        super.visitClassOrObject(classOrObject, pUml);
        return null;
    }

    @Override
    public Void visitProperty( KtProperty property, PUml pUml) {
        super.visitProperty(property, pUml);
        return null;
    }

    public Void visitPrimaryConstructor(KtPrimaryConstructor constructor, PUml pUml) {

        return null;
    }

    @Override
    public Void visitNamedFunction( KtNamedFunction function, PUml pUml) {
        super.visitNamedFunction(function, pUml);
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
}
