package io.product.finder

import groovy.util.logging.Slf4j
import io.dropwizard.Application
import io.dropwizard.db.DataSourceFactory
import io.dropwizard.hibernate.HibernateBundle
import io.dropwizard.migrations.MigrationsBundle
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import io.product.finder.dao.ProductDao
import io.product.finder.domain.Price
import io.product.finder.domain.Product
import io.product.finder.resources.ProductResource

@Slf4j
class ProductFinderApplication extends Application<ProductFinderConfiguration> {

    static void main(String[] args) throws Exception {
        new ProductFinderApplication().run(args)
    }

    @Override
    void initialize(Bootstrap bootstrap) {
        bootstrap.with {
            addBundle(migrationsBundle)
            addBundle(hibernateBundle)
        }
    }

    @Override
    void run(ProductFinderConfiguration configuration, Environment environment) throws Exception {
        log.info('Running.....')

        final ProductDao productDao = new ProductDao(hibernateBundle.sessionFactory)
        environment.jersey().register(new ProductResource(productDao))
    }

    private final HibernateBundle<ProductFinderConfiguration> hibernateBundle =
            new HibernateBundle<ProductFinderConfiguration>(Product, Price) {

                @Override
                DataSourceFactory getDataSourceFactory(ProductFinderConfiguration configuration) {
                    return configuration.database
                }
            }

    private final MigrationsBundle<ProductFinderConfiguration> migrationsBundle =
            new MigrationsBundle<ProductFinderConfiguration>() {

                @Override
                DataSourceFactory getDataSourceFactory(ProductFinderConfiguration configuration) {
                    return configuration.database
                }
            }
}
