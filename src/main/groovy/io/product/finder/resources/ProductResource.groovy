package io.product.finder.resources

import com.codahale.metrics.annotation.Timed
import com.google.common.base.Optional
import com.sun.jersey.api.NotFoundException
import io.dropwizard.hibernate.UnitOfWork
import io.dropwizard.jersey.caching.CacheControl
import io.dropwizard.jersey.params.LongParam
import io.product.finder.dao.ProductDao
import io.product.finder.domain.Product

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import java.util.concurrent.TimeUnit

@Path('/product')
@Produces(MediaType.APPLICATION_JSON)
class ProductResource {

    private final ProductDao productDao

    ProductResource(ProductDao productDao) {
        this.productDao = productDao
    }

    @GET
    @Path('/list')
    @Timed(name = 'get-requests')
    @CacheControl(maxAge = 60, maxAgeUnit = TimeUnit.SECONDS)
    @UnitOfWork
    List<Product> list() {
        return productDao.findAll()
    }

    @GET
    @Path('/{id}')
    @Timed(name = 'get-requests')
    @CacheControl(maxAge = 60, maxAgeUnit = TimeUnit.SECONDS)
    @UnitOfWork
    Product getProduct(@PathParam('id') LongParam id) {
        return findSafely(id.get())
    }

    @GET
    @Path('/{id}/price')
    @Timed(name = 'get-requests')
    @CacheControl(maxAge = 60, maxAgeUnit = TimeUnit.SECONDS)
    @UnitOfWork
    String getProductPrice(@PathParam('id') LongParam id) {
        return findSafely(id.get())
    }

    private Product findSafely(long id) {
        final Optional<Product> product = productDao.findById(id)
        if (!product.isPresent()) {
            throw new NotFoundException('No such product for id: ' + id)
        }
        return product.get()
    }
}
