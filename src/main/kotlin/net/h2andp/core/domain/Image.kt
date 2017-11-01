package net.h2andp.core.domain

import javax.persistence.*
import javax.persistence.CascadeType.ALL
import javax.persistence.FetchType.EAGER
import javax.persistence.GenerationType.AUTO

/**
 * Contains all variables tied to the image collection in database.
 */
@Entity
@Table( name = "image" )
data class Image (
        /**
         * Id of the record in database.
         */
        @Id
        @GeneratedValue( strategy = AUTO )
        var id: Long?,

        /**
         * Path the image is located.
         */
        var path: String,

        /**
         * Determines whether image indexed or not.
         */
        var indexed: Boolean,

        /**
         * MD5 has of the image.
         */
        var hash: String,

        /**
         * Mime type of the image.
         */
        var mime: String,

        /**
         * Any indexing errors.
         */
        var error: String?,

        /**
         * Set of attached image attributes
         * extracted from this image during the indexing step.
         */
        @OneToMany( mappedBy = "image", cascade = arrayOf( ALL ), fetch = EAGER )
        var attributes: Set<ImageAttribute>,

        /**
         * Set of attached image attributes
         * extracted from this image during the indexing step.
         */
        @OneToMany( mappedBy = "image", cascade = arrayOf( ALL ), fetch = EAGER )
        var imageClassifications: Set<ImageClassification>,

        /**
         * Set of attached image attributes
         * extracted from this image during the indexing step.
         */
        @OneToMany( mappedBy = "image", cascade = arrayOf( ALL ), fetch = EAGER )
        var duplicates: Set<ImageDuplicate>

)