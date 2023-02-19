package ru.verkhoturkin.ecommerce.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import ru.verkhoturkin.ecommerce.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;

@Configuration
public class DataRestConfig implements RepositoryRestConfigurer {

    @Value("${spring.data.rest.base-path}")
    private String basePath;
    @Value("${allowed.origins}")
    private String[] allowedOrigins;
    private final EntityManager entityManager;

    @Autowired
    public DataRestConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

        HttpMethod[] unsupportedMethods = {HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.PATCH};

        disableHttpMethod(Product.class, config, unsupportedMethods);
        disableHttpMethod(ProductCategory.class, config, unsupportedMethods);
        disableHttpMethod(Country.class, config, unsupportedMethods);
        disableHttpMethod(State.class, config, unsupportedMethods);
        disableHttpMethod(Order.class, config, unsupportedMethods);

        exposeIds(config);

        cors.addMapping(basePath + "/**").allowedOrigins(allowedOrigins);
    }

    private static void disableHttpMethod(Class<?> clazz,
                                          RepositoryRestConfiguration config,
                                          HttpMethod[] unsupportedMethods) {
        config.getExposureConfiguration()
                .forDomainType(clazz)
                .withItemExposure((metdata, httpMethod) -> httpMethod.disable(unsupportedMethods))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(unsupportedMethods));
    }

    private void exposeIds(RepositoryRestConfiguration config) {
//        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
//
//        List<Class<?>> entityClasses = new ArrayList<>();
//
//        for (EntityType<?> entity : entities) {
//            entityClasses.add(entity.getJavaType());
//        }
//        Class[] domainTypes = entityClasses.toArray(new Class[0]);

        Class<?>[] domainTypes = entityManager.getMetamodel().getEntities()
                .stream()
                .map(EntityType::getJavaType)
                .toArray(Class[]::new);

        config.exposeIdsFor(domainTypes);
    }
}
