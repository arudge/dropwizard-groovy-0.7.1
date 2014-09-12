package io.product.finder.resource

import com.google.common.base.Optional
import com.sun.jersey.api.NotFoundException
import io.dropwizard.jersey.params.LongParam
import io.product.finder.dao.ProductDao
import io.product.finder.domain.Product
import io.product.finder.resources.ProductResource
import spock.lang.Specification

class ProductResourceSpec extends Specification {

    Product product
    ProductDao mockDAO
    ProductResource resource

    def setup() {
        product = new Product(
                sku: 'AEX143',
                name: 'Stroller',
                category: 'baby',
                lastUpdated: new Date(1400821200000)
        )

        mockDAO = Mock()
        resource = new ProductResource(mockDAO)
    }

    def 'listing products returns a list of products'() {
        given: 'a mocked call to our db'
        1 * mockDAO.findAll() >> [product]

        and: 'no more mocks are called'
        0 * _

        expect: 'our mock return value as a list of products'
        [product] == resource.list()
    }

    def 'getting a product by id returns a single product'() {
        given: 'a mocked call to our db'
        1 * mockDAO.findById(product.id) >> Optional.fromNullable(product)

        and: 'no more mocks are called'
        0 * _

        expect: 'our mock return value as a single product'
        product == resource.getProduct(new LongParam(product.id.toString()))
    }

    def 'getting a product that doesn\'t exist throw exception'() {
        given: 'a mocked call to our db'
        1 * mockDAO.findById(product.id) >> Optional.fromNullable(null)

        and: 'no more mocks are called'
        0 * _

        when: 'our mock return value as a single product'
        resource.getProduct(new LongParam(product.id.toString()))

        then: 'exception was thrown'
        thrown(NotFoundException)
    }

}
