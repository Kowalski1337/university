package ru.ifmo.rain.baydyuk.implementor;

import info.kgeorgiy.java.advanced.implementor.Impler;
import info.kgeorgiy.java.advanced.implementor.ImplerException;

import java.io.BufferedWriter;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;


public class Implementor implements Impler {

    public void implement(Class<?> token, Path root) throws ImplerException {
        if (token == null) {
            throw new ImplerException("Error: token is null");
        }
        if (root == null) {
            throw new ImplerException("Error: root is null");
        }
        if (!token.isInterface()) {
            throw new ImplerException("Error: token is not an interface");
        }

        String classname = token.getSimpleName() + "Impl";
        Path path = root.resolve(token.getPackage().getName().replace('.', File.separatorChar));

        try {
            Files.createDirectories(path);
        } catch (Exception e) {
            throw new ImplerException("Error: can't create directories");
        }

        try (BufferedWriter w = Files.newBufferedWriter(Paths.get(path.toString() + File.separatorChar + classname + ".java"))) {
            if (token.getPackage() != null && !token.getPackage().getName().equals("")) {
                w.write("package " + token.getPackage().getName() + ";" + System.lineSeparator());
            }

            w.write(Modifier.toString(token.getModifiers() & ~(Modifier.ABSTRACT | Modifier.INTERFACE)) + " class " + classname + " implements " + token.getSimpleName() + " {" + System.lineSeparator());

            for (Method m : token.getMethods()) {
                if (m.isDefault()) {
                    continue;
                }

                w.write("\n\t@Override\n");

                for (Annotation annotation : m.getAnnotations()){
                    w.write('@' + annotation.annotationType().getCanonicalName() + '\n');
                }

                w.write("    " + Modifier.toString(m.getModifiers() & ~(Modifier.TRANSIENT | Modifier.ABSTRACT)) + " " + m.getReturnType().getSimpleName() + " " + m.getName() + " (");

                w.write(Arrays.stream(m.getParameters())
                        .map(p -> p.getType().getSimpleName() + " " + p.getName())
                        .collect(Collectors.joining(", ")));
                w.write(")");

                if (m.getExceptionTypes().length != 0) {
                    w.write(" throws ");
                    w.write(Arrays.stream(m.getExceptionTypes())
                            .map(Class::getCanonicalName)
                            .collect(Collectors.joining(", ")));
                }

                w.write(" {" + System.lineSeparator());
                if (m.getReturnType() != void.class) {
                    w.write("    " + "    " + "return ");
                    if (m.getReturnType() == boolean.class) {
                        w.write("false;");
                    } else if (m.getReturnType().isPrimitive()) {
                        w.write("0;");
                    } else {
                        w.write("null;");
                    }
                }
                w.write(System.lineSeparator() + "    " + "}" + System.lineSeparator());
            }
            w.write("}");
            w.close();
        } catch (Exception e) {
            throw new ImplerException(e.getMessage());
        }
    }
}