package io.github.intellij.dlanguage.types

import io.github.intellij.dlanguage.DLightPlatformCodeInsightFixtureTestCase
import io.github.intellij.dlanguage.utils.Type
import junit.framework.Assert
import org.intellij.lang.annotations.Language

class DTypeComparingTest : DLightPlatformCodeInsightFixtureTestCase("types/comparing") {
    private fun doTest(@Language("D") code: String, equals: Boolean) {
        InlineFile(code)

        val typeA = getType("^a")
        val typeB = getType("^b")

        Assert.assertEquals(equals, equalTypes(typeA, typeB))
    }

    private fun getType(marker: String) : Type {
        val (type) = findElementAndDataInEditor<Type>(marker = marker)
        return type
    }

    fun `test int should be builtin type`() {
        InlineFile("""
            int a;
          //^
        """)
        val type = getType("^")
        Assert.assertTrue(isBuiltinType(type))
    }

    fun `test string should not be builtin type`() {
        InlineFile("""
            string a;
          //^
        """)
        val type = getType("^")
        Assert.assertFalse(isBuiltinType(type))
    }

    fun `test delegate should not be builtin type`() {
        InlineFile("""
            void delegate(in int x) a;
          //^
        """)
        val type = getType("^")
        Assert.assertFalse(isBuiltinType(type))
    }

    fun `test function should not be builtin type`() {
        InlineFile("""
            void function(in int x) a;
          //^
        """)
        val type = getType("^")
        Assert.assertFalse(isBuiltinType(type))
    }

    fun `test delegate should be function literal type`() {
        InlineFile("""
            void delegate(in int x) a;
          //^
        """)
        val type = getType("^")
        Assert.assertTrue(isFunctionLiteral(type))
    }

    fun `test function should be function literal type`() {
        InlineFile("""
            void function(in int x) a;
          //^
        """)
        val type = getType("^")
        Assert.assertTrue(isFunctionLiteral(type))
    }

    fun `test two int builtin types should be equals`() = doTest("""
        int a;
      //^a
        int b;
      //^b
    """, equals = true)

    fun `test different class types should not be equals`() = doTest("""
        class AType {}
        class BType {}

        AType a;
      //^a
        BType b;
      //^b
    """, equals = false)

    fun `test same class types should not be equals`() = doTest("""
        class AType {}
        class BType {}

        AType a;
      //^a
        AType b;
      //^b
    """, equals = true)

    fun `test aliases types should be equals`() = doTest("""
        alias Listener = void delegate(in int key);

        Listener a;
      //^a
        Listener b;
      //^b
    """, equals = true)

    fun `test different aliases types should not be equals`() = doTest("""
        alias Listener = void delegate(in int key);
        alias MouseListener = void delegate(in int x, in int y);

        Listener a;
      //^a
        MouseListener b;
      //^b
    """, equals = false)

    fun `test same delegates should be equals`() = doTest("""
        void delegate(in int key) a;
      //^a
        void delegate(in int key) b;
      //^b
    """, equals = true)

    fun `test same functions should be equals`() = doTest("""
        void function(in int key) a;
      //^a
        void function(in int key) b;
      //^b
    """, equals = true)

    fun `test different delegates should not be equals`() = doTest("""
        void delegate(in int key) a;
      //^a
        void delegate(in int x, in int y) b;
      //^b
    """, equals = false)

    fun `test different functions should not be equals`() = doTest("""
        void function(in int key) a;
      //^a
        void function(in int x, in int y) b;
      //^b
    """, equals = false)

    fun `test same generic classes should be equals`() = doTest("""
        class Generic(T) {
            const T value;
        }

        Generic!int a;
      //^a
        Generic!int b;
      //^b
    """, equals = true)

    fun `test same generic structs should be equals`() = doTest("""
        struct Generic(T) {
            const T value;
        }

        Generic!int a;
      //^a
        Generic!int b;
      //^b
    """, equals = true)

    fun `test same generic interfaces should be equals`() = doTest("""
        interface Generic(T) {
            const T value;
        }

        Generic!int a;
      //^a
        Generic!int b;
      //^b
    """, equals = true)

    fun `test different generic classes should not be equals`() = doTest("""
        class Generic(T) {
            const T value;
        }

        Generic!int a;
      //^a
        Generic!long b;
      //^b
    """, equals = false)

    fun `test different generic structs should not be equals`() = doTest("""
        struct Generic(T) {
            const T value;
        }

        Generic!int a;
      //^a
        Generic!long b;
      //^b
    """, equals = false)

    fun `test different generic interfaces should not be equals`() = doTest("""
        interface Generic(T) {
            const T value;
        }

        Generic!int a;
      //^a
        Generic!long b;
      //^b
    """, equals = false)

    fun `test same complex generic classes should be equals`() = doTest("""
        class Generic(T) {
            const T value;
        }

        class Map(K, V) {}

        Map!(long, Map!(long, Generic!int)) a;
      //^a
        Map!(long, Map!(long, Generic!int)) b;
      //^b
    """, equals = true)

    fun `test different complex generic classes should not be equals`() = doTest("""
        class Generic(T) {
            const T value;
        }

        class Map(K, V) {}

        Map!(long, Map!(long, Generic!int)) a;
      //^a
        Map!(long, Map!(long, Generic!long)) b;
      //^b
    """, equals = false)
}
