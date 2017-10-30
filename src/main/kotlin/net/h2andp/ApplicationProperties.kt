package net.h2andp

import org.springframework.boot.context.properties.ConfigurationProperties
import java.net.URI

/**
 * Spring boot application.yml mapping.
 */
@ConfigurationProperties( value = "vesta", ignoreUnknownFields = false )
class ApplicationProperties {

    /**
     * Instance of the indexing settings container.
     */
    var indexing = Indexing()

    /**
     * Instance of the message broker settings container.
     */
    var mq = Mq()


    /**
     * Indexing settings.
     */
    class Indexing(
            /**
             * {@link URI} to the resource which stores file for indexing.
             */
            var path: URI = URI( "" ),

            /**
             * Recursive files search.
             */
            var recursive: Boolean = true
    )

    /**
     * Message broker settings.
     */
    class Mq(
            /**
             * Name of the queue in messaging queue broker for indexing requests.
             */
            var idxReqQName: String = "idxRequests",

            /**
             * Name of the queue in messaging queue broker for indexing responses.
             */
            var idxRespQName: String = "idxResponses"
    )
}