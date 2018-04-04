package ru.ifmo.rain.baydyuk.implementor;

import info.kgeorgiy.java.advanced.implementor.ImplerException;
import info.kgeorgiy.java.advanced.implementor.JarImpler;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.jar.Attributes;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

/**
 * The class produces code implementing class or interface by its <tt>token</tt>.
 *
 * @author Vadim Baydyuk
 */
public class Implementor implements JarImpler {

    public static void main(String[] args) throws ClassNotFoundException, ImplerException {
        Class<?> token = Class.forName(args[0]);
        Path path = Paths.get(System.getProperty("user.dir") + "\\java");
        System.out.println(System.getProperty("user.dir"));
        new Implementor().implementJar(token, path);
    }

    /**
     * Default constructor
     */
    public Implementor() {

    }

    /**
     * Delete abstract and transient modifiers.
     *
     * @param mod modifier
     * @return modifier without abstract and transient
     */
    private int deleteModifiers(final int mod) {
        return mod & ~Modifier.ABSTRACT & ~Modifier.TRANSIENT;
    }

    /**
     * Generates method's or constructor's parameters and types of parameters.
     * One of the first two parameters is null because doesn't generate for method and constructor.
     *
     * @param method      method for which get parameters
     * @param constructor constructor for which get parameters
     * @param isMethod    if isMethod equals <tt>true</tt>, generate parameters for method, else for constructor
     * @return String string with parameters and parameters' types
     */
    private String getParameters(Method method, Constructor constructor, boolean isMethod) {
        StringBuilder result = new StringBuilder();
        Class[] parametersTypes = isMethod ? method.getParameterTypes() : constructor.getParameterTypes();
        for (int i = 0; i < parametersTypes.length; ++i) {
            result.append(parametersTypes[i].getCanonicalName()).append(" x").append(i);
            if (i < parametersTypes.length - 1) {
                result.append(", ");
            }
        }

        return result.toString();
    }

    /**
     * Writes the exceptions in the file that are thrown by a method or constructor.
     *
     * @param method      method of class or interface
     * @param constructor constructor
     * @param isMethod    if isMethod equals <tt>true</tt>, write exceptions for method, else for constructor
     * @param writer      writer of file
     * @throws IOException if writer has failed
     */
    private void writeExceptions(Method method, Constructor constructor, boolean isMethod, Writer writer) throws IOException {
        Class[] exceptionTypes = isMethod ? method.getExceptionTypes() : constructor.getExceptionTypes();
        if (exceptionTypes.length != 0) {
            writer.write("throws ");
            for (int i = 0; i < exceptionTypes.length; ++i) {
                writer.write(exceptionTypes[i].getCanonicalName());
                if (i < exceptionTypes.length - 1) {
                    writer.write(", ");
                }
            }
        }
        writer.write(" ");
    }

    /**
     * Writes the method in the file.
     * <p>
     * This function writes method with its parameters and default return value.
     *
     * @param method method of class or interface
     * @param writer writer of file
     * @throws IOException if writer has failed
     */
    private void writeMethod(Method method, Writer writer) throws IOException {
        int mod = method.getModifiers();
        mod = deleteModifiers(mod);
        if (!Modifier.isPrivate(mod) && !Modifier.isFinal(mod) && !Modifier.isVolatile(mod)) {
            writer.write("    " + Modifier.toString(mod) + " " + method.getReturnType().getCanonicalName() + " " +
                    method.getName() + "(" + getParameters(method, null, true) + ")");
            writeExceptions(method, null, true, writer);
            writer.write("{\n        return");
            if (!method.getReturnType().isPrimitive()) {
                writer.write(" null");
            } else {
                switch (method.getReturnType().toString()) {
                    case "boolean":
                        writer.write(" false");
                        break;
                    case "void":
                        writer.write("");
                        break;
                    default:
                        writer.write(" 0");
                }
            }
            writer.write(";\n    };\n\n");
        }
    }

    /**
     * Produced <tt>.java</tt> file implementing class or interface specified by provided <tt>token</tt>.
     * Generated class full name should be same as full name of the type token with <tt>Impl</tt> suffix added.
     *
     * @param token type token to create implementation for
     * @param root  root directory
     * @throws ImplerException if implementation has failed
     */
    @Override
    public void implement(Class<?> token, Path root) throws ImplerException {
        if (token == null) {
            throw new ImplerException("Token is null");
        }

        if (root == null) {
            throw new ImplerException("Token is null");
        }

        if (token.isPrimitive() || token.isArray() || Enum.class.equals(token)) {
            throw new ImplerException("Token isn't class or interface");
        }

        System.err.println(root.toString());
        System.out.println(token.getPackage().getName().replace('.', File.separatorChar));
        System.err.println(root.toString() + "\\" + token.getPackage().getName().replace('.', File.separatorChar) + File.separatorChar);
        File path = new File(root.toString() + "\\" + token.getPackage().getName().replace('.', File.separatorChar) + File.separatorChar);

        if (!path.exists() && !path.mkdirs()) {
            throw new ImplerException("Token's package doesn't exist or can't create directories");
        }

        if (Modifier.isFinal(token.getModifiers())) {
            throw new ImplerException("Token is final");
        }

        System.err.println(path.toString());
        System.err.println(token.getSimpleName());
        String fileName = path.toString() + File.separatorChar + token.getSimpleName() + "Impl.java";
        try (Writer writer = new UnicodeFilter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8))) {
            writeClassPackage(token, writer);

            writeClassSignature(token, writer);

            Constructor[] constructors = token.getDeclaredConstructors();
            boolean hasPublicOtProtected = false;
            for (Constructor constructor : constructors) {
                int modifiers = constructor.getModifiers();
                modifiers = deleteModifiers(modifiers);
                if (!Modifier.isPrivate(modifiers)) {
                    hasPublicOtProtected = true;
                    writer.write("    " + Modifier.toString(modifiers) + " " + token.getSimpleName() +
                            "Impl(" + getParameters(null, constructor, false) + ") ");
                    writeExceptions(null, constructor, false, writer);
                    writer.write("{\n        ");
                    writer.write("super(");
                    int parametrCount = constructor.getParameterCount();
                    for (int i = 0; i < parametrCount; ++i) {
                        writer.write("x" + i + (i < parametrCount - 1 ? ", " : ""));
                    }
                    writer.write(");\n    };\n\n");
                }
            }
            if (!hasPublicOtProtected && constructors.length != 0 && !(token.isInterface())) {
                throw new ImplerException("Hasn't public constructor");
            }

            Method[] methods = token.getMethods();
            Class<?> cs = token;
            Set<Method> abstractMethods = new HashSet<>(Arrays.asList(methods));

            while (cs != null) {
                if (!Modifier.isAbstract(cs.getModifiers())) {
                    break;
                }
                for (Method method : cs.getDeclaredMethods()) {
                    int m = method.getModifiers();
                    if (!Modifier.isPrivate(m) && !Modifier.isPublic(m)) {
                        abstractMethods.add(method);
                    }
                }
                cs = cs.getSuperclass();
            }
            abstractMethods.removeIf(method -> !Modifier.isAbstract(method.getModifiers()));
            for (Method method : abstractMethods) {
                writeMethod(method, writer);
            }
            writer.write("}");
        } catch (IOException e) {
            System.err.println("Error of writing: " + e.toString());
        }
    }

    private void writeClassPackage(Class<?> token, Writer writer) throws IOException {
        if (token.getPackage() != null && !token.getPackage().getName().equals("")) {
            writer.write("package " + token.getPackage().getName() + ";" + System.lineSeparator() + System.lineSeparator());
        }
    }

    private void writeClassSignature(Class<?> token, Writer writer) throws IOException {
        int modifier = deleteModifiers(token.getModifiers()) & ~Modifier.INTERFACE;
        writer.write(Modifier.toString(modifier) + " class " + token.getSimpleName() + "Impl "
                + (token.isInterface() ? "implements " : "extends ") + token.getSimpleName() + " {" + System.lineSeparator() + System.lineSeparator());

    }

    /**
     * Gets path to file for compilation java-file to class-file.
     *
     * @param root      path of class
     * @param thisClass class or interface for compilation
     * @return path to java-file of class or interface
     */
    private String getPath(final File root, final Class<?> thisClass) {
        final String path = thisClass.getCanonicalName().replace(".", File.separator) + "Impl.java";
        return new File(root, path).getAbsoluteFile().getPath();
    }

    /**
     * Produced <tt>.jar</tt> file implementing class or interface specified by provided <tt>token</tt>.
     * <p>
     * Generated class full name should be same as full name of the type token with <tt>Impl</tt> added.
     *
     * @param token   type token to create implementation for.
     * @param jarFile target <tt>.jar</tt> file
     * @throws ImplerException if make jar-file has failed
     */
    @Override
    public void implementJar(Class<?> token, Path jarFile) throws ImplerException {
        implement(token, jarFile.getParent());
        String tokenPath = token.getPackage().getName().replace('.', '/') + '/' + token.getSimpleName() + "Impl";
        String classToCompile = jarFile.getParent().resolve(tokenPath).toString();
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler.run(null, null, null, classToCompile + ".java") != 0) {
            throw new ImplerException("Error: cannot compile .java file");
        }

        try {
            if (jarFile.getParent() != null) {
                Files.createDirectories(jarFile.getParent());
            }
        } catch (Exception e) {
            throw new ImplerException("Error: can't create directories for .jar file");
        }

        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        try (JarOutputStream os = new JarOutputStream(new FileOutputStream(jarFile.toString()), manifest)) {
            os.putNextEntry(new ZipEntry(tokenPath + ".class"));
            Files.copy(Paths.get(classToCompile + ".class"), os);
            os.closeEntry();
        } catch (Exception e) {
            throw new ImplerException(e.getMessage());
        }

    }

    /**
     * The class converts characters to "\\uXXXX" sequences.
     */
    private class UnicodeFilter extends FilterWriter {

        /**
         * Constract the Filter on provided Writer.
         *
         * @param writer out writer to filter
         */
        UnicodeFilter(Writer writer) {
            super(writer);
        }

        /**
         * Print sumbols in correct charset.
         *
         * @param s   string to be written
         * @param off offset from which to start reading characters
         * @param len number of characters to be written
         * @throws IOException if an I/O error occurs
         */
        @Override
        public void write(String s, int off, int len) throws IOException {
            char[] buffer = s.substring(off, off + len).toCharArray();
            for (char aBuffer : buffer) {
                if (aBuffer >= 128) {
                    super.write(String.format("\\u%04X", (int) aBuffer));
                } else {
                    super.write(aBuffer);
                }
            }
        }
    }
}