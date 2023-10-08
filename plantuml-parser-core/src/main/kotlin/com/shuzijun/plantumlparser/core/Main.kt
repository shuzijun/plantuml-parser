package com.shuzijun.plantumlparser.core

import com.intellij.openapi.util.Disposer
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.idea.KotlinFileType
import org.jetbrains.kotlin.psi.*


fun parseKotlinCode(code: String) {

    val disposable = Disposer.newDisposable()
    val env = KotlinCoreEnvironment.createForProduction(
        disposable, CompilerConfiguration(), EnvironmentConfigFiles.JVM_CONFIG_FILES)

    // 使用 PsiFileFactory 创建 KtFile
    val ktFile = PsiFileFactory.getInstance(env.project).createFileFromText("example.kt",KotlinFileType.INSTANCE, code) as KtFile

    // 创建访问者以提取类名和变量名
    val visitor = object : KtVisitorVoid() {

        override fun visitElement(element: PsiElement) {
            element.acceptChildren(this)
        }

       override fun visitProperty(property: KtProperty) {
           println("Property Name: ${property.name}")
            super.visitProperty(property)
        }

        override fun visitClass( klass: KtClass) {
            println("Class Name: ${klass.name}")
            for (property in klass.getProperties()) {
                property.accept(this)
            }


            // 获取接口实现和继承类
            val superClassEntries: List<KtSuperTypeListEntry> = klass.getSuperTypeListEntries()
            for (superClassEntry in superClassEntries) {
                if (superClassEntry != null) {
                    superClassEntry.typeReference!!.text
                }
            }

            super.visitClassOrObject(klass)
        }

        override fun visitComment(comment: PsiComment) {
            println("PsiComment : ${comment.text}")
            super.visitComment(comment)
        }

        override fun visitNamedFunction(function: KtNamedFunction) {
            super.visitNamedFunction(function)
        }

        override fun visitKtElement( element: KtElement) {
            super.visitKtElement(element)
        }

        override fun visitKtFile( file: KtFile) {
            super.visitKtFile(file)
        }

        override fun visitDeclaration( dcl: KtDeclaration) {
            super.visitDeclaration(dcl)
        }
    }

    // 遍历 KtFile 并使用访问者提取信息
    ktFile.acceptChildren(visitor)
}

fun main() {
    var code = """
        // 声明一个包含 Kotlin 所有关键字的类
        class KotlinKeywordsExample {
            // 声明属性关键字
            val myVal: Int = 42
            var myVar: String = "Hello"
            lateinit var myLateInitVar: String
            const val myConstVal: Double = 3.14
        
            // 声明函数关键字
            fun myFunction():String {
                // 控制流关键字
                if (myVal > 0) {
                    println("myVal is positive")
                } else {
                    println("myVal is non-positive")
                }
        
                when (myVar) {
                    "Hello" -> println("myVar is Hello")
                    else -> println("myVar is not Hello")
                }
        
        
            }
        
            // 类修饰符关键字
            data class MyDataClass(val name: String)
            open class MyBaseClass
            interface MyInterface
            enum class MyEnum { ONE, TWO, THREE }
        
            // 继承和实现关键字
            class MyDerivedClass : MyBaseClass(), MyInterface {
                override fun toString(): String {
                    return "Derived Class"
                }
            }
        }
    """.trimIndent()
    parseKotlinCode(code)
}
