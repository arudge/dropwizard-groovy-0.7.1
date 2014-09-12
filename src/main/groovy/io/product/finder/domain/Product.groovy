package io.product.finder.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@Table(name = 'product')
@ToString
@EqualsAndHashCode
@NamedQuery(
        name = 'io.product.finder.domain.Product.findAll',
        query = 'SELECT p from Product p'
)
class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id

    @Column(nullable = false)
    String sku

    @Column(nullable = false)
    String name

    @Column(nullable = false)
    String category

    @Column(nullable = false)
    Date lastUpdated

    @OneToOne
    @PrimaryKeyJoinColumn
    Price price

}
