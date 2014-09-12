package io.product.finder.domain

import com.fasterxml.jackson.databind.ObjectMapper
import io.dropwizard.jackson.Jackson
import spock.lang.Specification

import static io.dropwizard.testing.FixtureHelpers.fixture

class ProductSpec extends Specification {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper()
    private static final String PRODUCT_JSON = 'product.json'

    Product product

    def setup() {
        product = new Product(
                sku: 'AEX143',
                name: 'Stroller',
                category: 'baby',
                lastUpdated: new Date(1400821200000),
                price: new Price(price: 29.99)
        )
    }

    def 'serialization of product is as expected'() {
        expect: 'entity as json is equal to sample json'
        fixture(PRODUCT_JSON) == MAPPER.writeValueAsString(product)
    }

    def 'deserialization of product is as expected'() {
        given: 'json can be turned into an entity'

        Product jsonProduct = MAPPER.readValue(fixture(PRODUCT_JSON), Product)

        expect: 'properties of each are equal'
        jsonProduct.sku == product.sku
        jsonProduct.name == product.name
        jsonProduct.category == product.category
        jsonProduct.lastUpdated == product.lastUpdated
    }
}
