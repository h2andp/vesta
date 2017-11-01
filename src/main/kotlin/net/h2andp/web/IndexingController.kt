package net.h2andp.web

import net.h2andp.ApplicationProperties
import net.h2andp.core.services.IndexingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Accepts indexing requests.
 */
@RestController
class IndexingController(
        @Autowired
        val indexingService: IndexingService,

        @Autowired
        val config: ApplicationProperties
) {

    /**
     * Accepts index request and immediately start it.
     */
    @GetMapping( value = "/indexing/index" )
    fun index(): ResponseEntity<Void> {
        indexingService.index( config.indexing.path, config.indexing.recursive )
        return ResponseEntity.ok().build<Void>()
    }
}