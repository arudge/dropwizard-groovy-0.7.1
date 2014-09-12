package io.product.finder.dao

import com.google.common.base.Optional
import io.dropwizard.hibernate.AbstractDAO
import io.product.finder.domain.Product
import org.hibernate.SessionFactory

class ProductDao extends AbstractDAO<Product> {

    ProductDao(SessionFactory sessionFactory) {
        super(sessionFactory)
    }

    Optional<Product> findById(Long id) {
        return Optional.fromNullable(get(id))
    }

    List<Product> findAll() {
        return list(namedQuery('io.product.finder.domain.Product.findAll'))
    }
}
