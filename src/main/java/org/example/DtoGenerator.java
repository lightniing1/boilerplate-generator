package org.example;

import com.squareup.javapoet.*;
import org.teavm.jso.JSExport;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Paths;

public class DtoGenerator {
    @JSExport
    public static void generate(String className) throws IOException {
        ClassName baseDTO = ClassName.get("com.stfn2.ggas.core.abstractClasses", "BaseDTO");

        TypeSpec dtoClass = TypeSpec.classBuilder(className + "DTO")
                .addModifiers(Modifier.PUBLIC)
                .superclass(baseDTO)
                .addAnnotation(ClassName.get("lombok", "Getter"))
                .addAnnotation(ClassName.get("lombok", "Setter"))
                .addAnnotation(ClassName.get("lombok", "AllArgsConstructor"))
                .addAnnotation(ClassName.get("lombok", "NoArgsConstructor"))
                .build();

        JavaFile javaFile = JavaFile.builder("com.stfn2.ggas.domain.dtos", dtoClass)
                .build();

        javaFile.writeTo(Paths.get("./src/main/java"));
    }
}
