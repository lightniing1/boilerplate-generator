package org.example;

import com.squareup.javapoet.*;
import org.teavm.jso.JSExport;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Paths;

public class ServiceGenerator {
    @JSExport
    public static void generate(String className) throws IOException {
        ClassName baseService = ClassName.get("com.stfn2.ggas.core.abstractClasses", "BaseService");
        ClassName model = ClassName.get("com.stfn2.ggas.domain", className + "Model");
        ClassName dto = ClassName.get("com.stfn2.ggas.domain.dtos", className + "DTO");
        ClassName basicDTO = ClassName.get("com.stfn2.ggas.domain.dtos.basic", className + "BasicDTO");
        ClassName filterDTO = ClassName.get("com.stfn2.ggas.domain.dtos.filter", className + "FilterDTO");
        ClassName repository = ClassName.get("com.stfn2.ggas.repositories", className + "Repository");

        TypeSpec serviceClass = TypeSpec.classBuilder(className + "Service")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(ClassName.get("org.springframework.stereotype", "Service"))
                .superclass(ParameterizedTypeName.get(baseService, model, dto, basicDTO, filterDTO, repository))
                .build();

        JavaFile javaFile = JavaFile.builder("com.stfn2.ggas.services", serviceClass)
                .build();

        javaFile.writeTo(Paths.get("./src/main/java"));
    }
}

