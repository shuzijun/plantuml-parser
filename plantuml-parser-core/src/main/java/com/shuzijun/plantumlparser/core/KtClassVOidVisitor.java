package com.shuzijun.plantumlparser.core;


import com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.*;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;
import org.jetbrains.kotlin.resolve.ImportPath;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KtClassVOidVisitor extends KtTreeVisitor<PUml> implements MyVisitor {

    private final String packageName;

    private final ParserConfig parserConfig;

    public KtClassVOidVisitor(String packageName, ParserConfig parserConfig) {
        this.packageName = packageName;
        this.parserConfig = parserConfig;
    }


    @Override
    public Void visitClassOrObject(KtClassOrObject ktClass, PUml pUml) {

        if (!(pUml instanceof PUmlView)) {
            super.visitClassOrObject(ktClass, pUml);
            return null;
        }

        if (parserConfig.isExcludeClass(ktClass.getName())) {
            return null;
        }


        PUmlView pUmlView = (PUmlView) pUml;
        PUmlClass pUmlClass = createUmlClass();

        pUmlClass.setClassName(ktClass.getName());
        if (ktClass instanceof KtObjectDeclaration) {
            pUmlClass.setClassType("class");
        } else {
            if (((KtClass) ktClass).isInterface()) {
                pUmlClass.setClassType("interface");
            } else if (((KtClass) ktClass).isEnum()) {
                pUmlClass.setClassType("enum");
            } else {
                if (ktClass.hasModifier(KtTokens.ABSTRACT_KEYWORD)) {
                    pUmlClass.setClassType("abstract class");
                } else {
                    pUmlClass.setClassType("class");
                }

            }
        }

        if (parserConfig.isShowComment()) {
            if (ktClass.getDocComment() != null) {
                pUmlClass.setClassComment(ktClass.getDocComment().getText());
            }
        }

        if (ktClass.getPrimaryConstructor() != null) {
            ktClass.getPrimaryConstructor().accept(this, pUmlClass);
        }

        ktClass.getDeclarations().forEach(p -> {
            if (p instanceof KtProperty) {
                p.accept(this, pUmlClass);
            } else if (p instanceof KtConstructor) {
                p.accept(this, pUmlClass);
            } else if (p instanceof KtNamedFunction) {
                p.accept(this, pUmlClass);
            } else if (p instanceof KtEnumEntry) {
                p.accept(this, pUmlClass);
            } else if (p instanceof KtObjectDeclaration) {
                p.accept(this, pUml);
            } else if (p instanceof KtClassOrObject) {
                p.accept(this, pUml);
            }
        });

        pUmlView.addPUmlClass(pUmlClass);


        PsiElement node = ktClass.getParent();

        List<KtImportDirective> importDeclarations = parseImport(node, pUmlClass, pUmlView);

        Map<String, String> importMap = new HashMap<>();
        if (importDeclarations != null) {
            for (KtImportDirective importDeclaration : importDeclarations) {
                ImportPath importPath = importDeclaration.getImportPath();
                if (importPath.getAlias() == null) {
                    importMap.put(importPath.getImportedName().asString(), importPath.getFqName().toString());
                } else {
                    importMap.put(importPath.getAlias().asString(), importPath.getFqName().toString());
                }

            }
        }

        List<KtSuperTypeListEntry> superClassEntries = ktClass.getSuperTypeListEntries();
        for (KtSuperTypeListEntry superClassEntry : superClassEntries) {
            if (superClassEntry != null) {
                if (superClassEntry.getElementType() == KtStubElementTypes.SUPER_TYPE_ENTRY) {
                    PUmlRelation pUmlRelation = new PUmlRelation();
                    pUmlRelation.setTarget(getPackageNamePrefix(pUmlClass.getPackageName()) + pUmlClass.getClassName());
                    if (importMap.containsKey(superClassEntry.getText())) {
                        if (parserConfig.isShowPackage()) {
                            pUmlRelation.setSource(importMap.get(superClassEntry.getText()));
                        } else {
                            pUmlRelation.setSource(superClassEntry.getText());
                        }
                    } else {
                        pUmlRelation.setSource(getPackageNamePrefix(pUmlClass.getPackageName()) + superClassEntry.getText());
                    }
                    pUmlRelation.setRelation("<|..");
                    pUmlView.addPUmlRelation(pUmlRelation);
                } else if (superClassEntry.getElementType() == KtStubElementTypes.SUPER_TYPE_CALL_ENTRY) {
                    PUmlRelation pUmlRelation = new PUmlRelation();
                    pUmlRelation.setTarget(getPackageNamePrefix(pUmlClass.getPackageName()) + pUmlClass.getClassName());
                    if (importMap.containsKey(superClassEntry.getTypeReference().getText())) {
                        if (parserConfig.isShowPackage()) {
                            pUmlRelation.setSource(importMap.get(superClassEntry.getTypeReference().getText()));
                        } else {
                            pUmlRelation.setSource(superClassEntry.getTypeReference().getText());
                        }
                    } else {
                        pUmlRelation.setSource(getPackageNamePrefix(pUmlClass.getPackageName()) + superClassEntry.getTypeReference().getText());
                    }
                    pUmlRelation.setRelation("<|--");
                    pUmlView.addPUmlRelation(pUmlRelation);
                }

            }
        }
        return null;
    }

    @Override
    public Void visitProperty(KtProperty property, PUml pUml) {
        if (!(pUml instanceof PUmlClass)) {
            super.visitProperty(property, pUml);
            return null;
        }
        PUmlClass pUmlClass = (PUmlClass) pUml;
        PUmlField pUmlField = new PUmlField();

        pUmlField.setVisibility(VisibilityUtils.toVisibility(property));

        if (parserConfig.isFieldModifier(pUmlField.getVisibility())) {
            pUmlField.setType(getKtTypeReference(property.getTypeReference(), ""));
            pUmlField.setName(property.getName());
            pUmlClass.addPUmlFieldList(pUmlField);
        }

        if (parserConfig.isShowComment()) {
            if (property.getDocComment() != null) {
                pUmlField.setComment(property.getDocComment().getText());
            }
        }
        return null;
    }

    @Override
    public Void visitEnumEntry(KtEnumEntry enumEntry, PUml pUml) {

        if (!(pUml instanceof PUmlClass)) {
            super.visitEnumEntry(enumEntry, pUml);
            return null;
        }
        PUmlClass pUmlClass = (PUmlClass) pUml;
        PUmlField pUmlField = new PUmlField();

        pUmlField.setVisibility(Constant.VisibilityPublic);

        if (parserConfig.isFieldModifier(pUmlField.getVisibility())) {
            pUmlField.setType("");
            pUmlField.setName(enumEntry.getName());
            pUmlClass.addPUmlFieldList(pUmlField);
        }

        if (parserConfig.isShowComment()) {
            if (enumEntry.getDocComment() != null) {
                pUmlField.setComment(enumEntry.getDocComment().getText());
            }
        }
        return null;
    }

    private String getKtTypeReference(KtTypeReference reference, String defaultValue) {

        if (reference != null) {
            return reference.getText();
        }

        return defaultValue;
    }

    public Void visitPrimaryConstructor(KtPrimaryConstructor constructor, PUml pUml) {
        return this.visitConstructor(constructor, pUml);
    }

    public Void visitSecondaryConstructor(KtSecondaryConstructor constructor, PUml pUml) {
        return this.visitConstructor(constructor, pUml);
    }

    public Void visitConstructor(KtConstructor constructor, PUml pUml) {
        if (!(pUml instanceof PUmlClass)) {
            return null;
        }
        if (!parserConfig.isShowConstructors()) {
            return null;
        }
        PUmlClass pUmlClass = (PUmlClass) pUml;
        PUmlMethod pUmlMethod = new PUmlMethod();


        pUmlMethod.setVisibility(VisibilityUtils.toVisibility(constructor));

        if (parserConfig.isMethodModifier(pUmlMethod.getVisibility())) {
            pUmlMethod.setReturnType("<<Create>>");
            pUmlMethod.setName(constructor.getName());

            for (Object parameter : constructor.getValueParameters()) {
                pUmlMethod.addParam(getKtTypeReference(((KtParameter) parameter).getTypeReference(), ""));
            }
            pUmlClass.addPUmlMethodList(pUmlMethod);
        }
        if (parserConfig.isShowComment()) {
            if (constructor.getDocComment() != null) {
                pUmlMethod.setComment(constructor.getDocComment().getText());
            }
        }

        return null;
    }

    @Override
    public Void visitNamedFunction(KtNamedFunction function, PUml pUml) {
        if (!(pUml instanceof PUmlClass)) {
            super.visitNamedFunction(function, pUml);
            return null;
        }
        PUmlClass pUmlClass = (PUmlClass) pUml;

        PUmlMethod pUmlMethod = new PUmlMethod();

        pUmlMethod.setVisibility(VisibilityUtils.toVisibility(function));

        if (parserConfig.isMethodModifier(pUmlMethod.getVisibility())) {
            pUmlMethod.setReturnType(getKtTypeReference(function.getTypeReference(), "void"));
            pUmlMethod.setName(function.getName());
            for (Object parameter : function.getValueParameters()) {
                pUmlMethod.addParam(getKtTypeReference(((KtParameter) parameter).getTypeReference(), "void"));
            }
            pUmlClass.addPUmlMethodList(pUmlMethod);
        }

        if (parserConfig.isShowComment()) {
            if (function.getDocComment() != null) {
                pUmlMethod.setComment(function.getDocComment().getText());
            }
        }
        return null;
    }

    @Override
    public Void visitKtElement(KtElement element, PUml pUml) {
        super.visitKtElement(element, pUml);
        return null;
    }

    @Override
    public Void visitKtFile(KtFile file, PUml pUml) {
        super.visitKtFile(file, pUml);
        return null;
    }

    @Override
    public Void visitDeclaration(KtDeclaration dcl, PUml pUml) {
        super.visitDeclaration(dcl, pUml);
        return null;
    }

    private List<KtImportDirective> parseImport(PsiElement node, PUmlClass pUmlClass, PUmlView pUmlView) {
        if (node instanceof KtFile) {
            return ((KtFile) node).getImportDirectives();
        } else if (node != null) {
            if (node instanceof KtClassBody && node.getParent() instanceof KtClassOrObject) {
                pUmlClass.setClassName(((KtClassOrObject) node.getParent()).getName() + "$" + pUmlClass.getClassName());

                PUmlRelation pUmlRelation = new PUmlRelation();
                pUmlRelation.setTarget(getPackageNamePrefix(pUmlClass.getPackageName()) + pUmlClass.getClassName());
                pUmlRelation.setSource(getPackageNamePrefix(pUmlClass.getPackageName()) + pUmlClass.getClassName().substring(0, pUmlClass.getClassName().lastIndexOf("$")));
                pUmlRelation.setRelation("+..");
                pUmlView.addPUmlRelation(pUmlRelation);
            }
            parseImport(node.getParent(), pUmlClass, pUmlView);
        }
        return null;
    }

    @Override
    public String getPackageName() {
        return packageName;
    }

    @Override
    public ParserConfig getParserConfig() {
        return parserConfig;
    }
}
