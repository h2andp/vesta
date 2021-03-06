package net.h2andp.core.domain

import javax.persistence.*
import javax.persistence.GenerationType.AUTO

/**
 * Contains all variables tied to the image attributes collection in database.
 */
@Entity
@Table( name = "image_attribute" )
data class ImageAttribute (

        /**
         * Id of the record in database.
         */
        @Id
        @GeneratedValue( strategy = AUTO )
        var id: Long? = null,

        /**
         * Name of the attribute.
         */
        var name: String? = null,

        /**
         * Value of the attribute.
         */
        var value: String? = null,

        /**
         * Instance of {@link Image} for which attribute attached.
         */
        @ManyToOne
        @JoinColumn( name = "image_id" )
        var image: Image? = null

)