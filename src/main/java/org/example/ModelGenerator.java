package org.example;

import com.squareup.javapoet.*;
import org.teavm.jso.JSExport;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Paths;

public class ModelGenerator {
    @JSExport
    public static void generate(String className) throws IOException {
        ClassName baseEntity = ClassName.get("com.stfn2.ggas.core.abstractClasses", "BaseEntity");

        TypeSpec modelClass = TypeSpec.classBuilder(className + "Model")
                .addModifiers(Modifier.PUBLIC)
                .superclass(baseEntity)
                .addAnnotation(ClassName.get("javax.persistence", "Entity"))
                .addAnnotation(AnnotationSpec.builder(ClassName.get("javax.persistence", "Table"))
                        .addMember("name", "$S", "TABLE_NAME")
                        .build())
                .addAnnotation(ClassName.get("lombok", "Getter"))
                .addAnnotation(ClassName.get("lombok", "Setter"))
                .addAnnotation(ClassName.get("lombok", "AllArgsConstructor"))
                .addAnnotation(ClassName.get("lombok", "NoArgsConstructor"))
                .build();

        JavaFile javaFile = JavaFile.builder("com.stfn2.ggas.domain", modelClass)
                .build();

        javaFile.writeTo(Paths.get("./src/main/java"));
    }
}
