package io.product.finder.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = 'price')
@ToString
@EqualsAndHashCode
class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id

    @Column(nullable = false)
    Double price

    @JsonIgnore
    @OneToOne(mappedBy = 'price')
    Product product
}
