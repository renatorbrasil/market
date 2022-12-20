package com.eventsourcing.market.config.mongo;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.convert.TypeInformationMapper;
import org.springframework.data.mapping.Alias;
import org.springframework.data.util.TypeInformation;

import java.util.*;

/**
 * @author Piotr `Athlan` Pelczar
 * Taken from https://athlan.pl/spring-data-mongodb-remove-_class-define-explicitly/
 * and https://gist.github.com/athlan/6497c74cc515131e1336
 */
public class AnnotationTypeInformationMapper implements TypeInformationMapper {

    @Override
    public TypeInformation<?> resolveTypeFrom(Alias alias) {
        if(aliasToTypeMap.containsKey(alias)) {
            return aliasToTypeMap.get(alias);
        }

        return null;
    }

    private final Map<TypeInformation<?>, Alias> typeToAliasMap;
    private final Map<Alias, TypeInformation<?>> aliasToTypeMap;

    private AnnotationTypeInformationMapper(List<String> basePackagesToScan) {
        typeToAliasMap = new HashMap<>();
        aliasToTypeMap = new HashMap<>();

        populateTypeMap(basePackagesToScan);
    }

    private void populateTypeMap(List<String> basePackagesToScan) {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);

        scanner.addIncludeFilter(new AnnotationTypeFilter(DocumentType.class));

        for (String basePackage : basePackagesToScan) {
            for (BeanDefinition bd : scanner.findCandidateComponents(basePackage)) {
                try {
                    Class< ?> clazz = Class.forName(bd.getBeanClassName());
                    DocumentType documentTypeAnnotation = clazz.getAnnotation(DocumentType.class);

                    TypeInformation< ?> type = TypeInformation.of(clazz);
                    String alias = documentTypeAnnotation.value();

                    typeToAliasMap.put(type, Alias.of(alias));
                    aliasToTypeMap.put(Alias.of(alias), type);

                } catch (ClassNotFoundException e) {
                    throw new IllegalStateException(String.format("Class [%s] could not be loaded.", bd.getBeanClassName()), e);
                }
            }
        }
    }

    public Alias createAliasFor(TypeInformation< ?> type) {
        TypeInformation< ?> typeClass = (TypeInformation< ?>) type;

        if(typeToAliasMap.containsKey(typeClass)) {
            return typeToAliasMap.get(typeClass);
        }

        return null;
    }

    public static class Builder {
        List<String> basePackagesToScan;

        public Builder() {
            basePackagesToScan = new ArrayList<>();
        }

        public Builder withBasePackage(String basePackage) {
            basePackagesToScan.add(basePackage);
            return this;
        }

        public Builder withBasePackages(String[] basePackages) {
            basePackagesToScan.addAll(Arrays.asList(basePackages));
            return this;
        }

        public Builder withBasePackages(Collection< ? extends String> basePackages) {
            basePackagesToScan.addAll(basePackages);
            return this;
        }

        public AnnotationTypeInformationMapper build() {
            return new AnnotationTypeInformationMapper(basePackagesToScan);
        }
    }
}
