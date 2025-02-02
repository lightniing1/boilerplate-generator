package org.example;

import com.squareup.javapoet.*;
import org.teavm.jso.JSExport;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Paths;

public class RepositoryImplGenerator {
    @JSExport
    public static void generate(String className) throws IOException {
        ClassName iRepository = ClassName.get("com.stfn2.ggas.core.abstractClasses", "IRepository");
        ClassName model = ClassName.get("com.stfn2.ggas.domain", className + "Model");
        ClassName filterDTO = ClassName.get("com.stfn2.ggas.domain.dtos.filter", className + "FilterDTO");
        ClassName entityManager = ClassName.get("javax.persistence", "EntityManager");

        MethodSpec constructor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(entityManager, "em")
                .addStatement("super(em, $S)", "id")
                .build();

        TypeSpec repositoryImpl = TypeSpec.classBuilder(className + "RepositoryImpl")
                .addModifiers(Modifier.PUBLIC)
                .superclass(ParameterizedTypeName.get(iRepository, model, filterDTO))
                .addMethod(constructor)
                .build();

        JavaFile javaFile = JavaFile.builder("com.stfn2.ggas.repositories.impl", repositoryImpl)
                .build();

        javaFile.writeTo(Paths.get("./src/main/java"));
    }
}

