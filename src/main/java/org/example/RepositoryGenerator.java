package org.example;

import com.squareup.javapoet.*;
import org.teavm.jso.JSExport;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Paths;

public class RepositoryGenerator {
    @JSExport
    public static void generate(String className) throws IOException {
        ClassName baseRepository = ClassName.get("com.stfn2.ggas.core.abstractClasses", "BaseRepository");
        ClassName model = ClassName.get("com.stfn2.ggas.domain", className + "Model");
        ClassName filterDTO = ClassName.get("com.stfn2.ggas.domain.dtos.filter", className + "FilterDTO");

        TypeSpec repositoryInterface = TypeSpec.interfaceBuilder(className + "Repository")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(ClassName.get("org.springframework.stereotype", "Repository"))
                .addSuperinterface(ParameterizedTypeName.get(baseRepository, model, filterDTO))
                .build();

        JavaFile javaFile = JavaFile.builder("com.stfn2.ggas.repositories", repositoryInterface)
                .build();

        javaFile.writeTo(Paths.get("./src/main/java"));
    }
}

