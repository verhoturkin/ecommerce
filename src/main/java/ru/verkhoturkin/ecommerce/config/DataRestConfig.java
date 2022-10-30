package ru.verkhoturkin.ecommerce.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import ru.verkhoturkin.ecommerce.entity.Product;
import ru.verkhoturkin.ecommerce.entity.ProductCategory;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;

@Configuration
public class DataRestConfig implements RepositoryRestConfigurer {

    private final EntityManager entityManager;

    @Autowired
    public DataRestConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

        HttpMethod[] unsupportedMethods = {HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE};

        config.getExposureConfiguration()
                .forDomainType(Product.class)
                .withItemExposure((metdata, httpMethod) -> httpMethod.disable(unsupportedMethods))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(unsupportedMethods));

        config.getExposureConfiguration()
                .forDomainType(ProductCategory.class)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(unsupportedMethods))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(unsupportedMethods));

        exposeIds(config);
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
