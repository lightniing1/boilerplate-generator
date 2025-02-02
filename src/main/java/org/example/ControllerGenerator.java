package org.example;

import com.squareup.javapoet.*;
import org.teavm.jso.JSExport;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Paths;

public class ControllerGenerator {
    @JSExport
    public static void generate(String className) throws IOException {
        ClassName baseController = ClassName.get("com.stfn2.ggas.core.abstractClasses", "BaseController");
        ClassName model = ClassName.get("com.stfn2.ggas.domain", className + "Model");
        ClassName dto = ClassName.get("com.stfn2.ggas.domain.dtos", className + "DTO");
        ClassName basicDTO = ClassName.get("com.stfn2.ggas.domain.dtos.basic", className + "BasicDTO");
        ClassName filterDTO = ClassName.get("com.stfn2.ggas.domain.dtos.filter", className + "FilterDTO");
        ClassName repository = ClassName.get("com.stfn2.ggas.repositories", className + "Repository");
        ClassName service = ClassName.get("com.stfn2.ggas.services", className + "Service");

        TypeSpec controllerClass = TypeSpec.classBuilder(className + "Controller")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(ClassName.get("org.springframework.web.bind.annotation", "RestController"))
                .addAnnotation(AnnotationSpec.builder(ClassName.get("org.springframework.web.bind.annotation", "RequestMapping"))
                        .addMember("value", "$S", "/" + className.toLowerCase())
                        .build())
                .addAnnotation(AnnotationSpec.builder(ClassName.get("io.swagger.v3.oas.annotations.tags", "Tag"))
                        .addMember("name", "$S", className)
                        .addMember("description", "$S", "Endpoints para gerenciamento de " + className)
                        .build())
                .superclass(ParameterizedTypeName.get(baseController, model, dto, basicDTO, filterDTO, repository, service))
                .build();

        JavaFile javaFile = JavaFile.builder("com.stfn2.ggas.controllers", controllerClass)
                .build();

        javaFile.writeTo(Paths.get("./src/main/java"));
    }
}

