package io.product.finder.dao

import com.google.common.base.Optional
import io.product.finder.domain.Product
import org.hibernate.Query
import org.hibernate.Session
import org.hibernate.SessionFactory
import spock.lang.Specification

class ProductDAOSpec extends Specification {

    ProductDao productDAO
    SessionFactory sessionFactory
    Session session
    Product givenProduct
    Query query

    def setup() {
        sessionFactory = Mock()
        session = Mock()
        query = Mock()
        givenProduct = new Product()
        productDAO = new ProductDao(sessionFactory)
    }

    def 'query the db by id returns an Optional'() {
        given: 'our mock hibernate session calls'
        1 * sessionFactory.currentSession >> session
        1 * session.get(Product, 1) >> givenProduct

        and: 'no more mock calls'
        0 * _

        when: 'we query for a product'
        Optional<Product> foundProduct = productDAO.findById(1)

        then: 'our found product matches our given product'
        foundProduct.get() == givenProduct
    }

    def 'query the db for all products'() {
        given:
        1 * sessionFactory.currentSession >> session
        1 * session.getNamedQuery('io.product.finder.domain.Product.findAll') >> query
        1 * query.list() >> [givenProduct]

        and: 'no more mock calls'
        0 * _

        when: 'we query for all products'
        List<Product> foundProducts = productDAO.findAll()

        then: 'our found product list matches our given list'
        [givenProduct] == foundProducts
    }
}
