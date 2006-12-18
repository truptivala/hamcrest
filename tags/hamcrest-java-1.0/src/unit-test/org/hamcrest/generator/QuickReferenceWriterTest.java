package org.hamcrest.generator;

import junit.framework.TestCase;

import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class QuickReferenceWriterTest extends TestCase {

    private ByteArrayOutputStream actualBuffer;
    private ByteArrayOutputStream expectedBuffer;
    private PrintStream expected;
    private QuickReferenceWriter writer;

    protected void setUp() throws Exception {
        super.setUp();
        actualBuffer = new ByteArrayOutputStream();
        writer = new QuickReferenceWriter(new PrintStream(actualBuffer));

        expectedBuffer = new ByteArrayOutputStream();
        expected = new PrintStream(expectedBuffer);
    }

    public void testWritesSimplifiedSummaryOfMatchers() throws IOException {
        FactoryMethod namedMethod = new FactoryMethod("SomeClass", "someMethod");
        namedMethod.addParameter("Cheese", "a");
        namedMethod.addParameter("int", "b");
        namedMethod.setGenerifiedType("String");
        writer.writeMethod("namedMethod", namedMethod);

        FactoryMethod anotherMethod = new FactoryMethod("SomeClass", "anotherMethod");
        anotherMethod.setGenerifiedType("int");
        writer.writeMethod("anotherMethod", anotherMethod);

        expected.println("        [String] namedMethod(Cheese, int)");
        expected.println("           [int] anotherMethod()");
        verify();
    }

    public void testRemovesPackageNames() throws IOException {
        FactoryMethod namedMethod = new FactoryMethod("SomeClass", "someMethod");
        namedMethod.addParameter("com.blah.Foo", "a");
        namedMethod.addParameter("com.foo.Cheese<x.y.Zoo>", "b");
        namedMethod.setGenerifiedType("java.lang.Cheese");
        writer.writeMethod("namedMethod", namedMethod);

        expected.println("        [Cheese] namedMethod(Foo, Cheese<Zoo>)");
        verify();
    }

    private void verify() {
        assertEquals(new String(expectedBuffer.toByteArray()), new String(actualBuffer.toByteArray()));
    }

}
