package net.h2andp.core.domain

import javax.persistence.*

/**
 * Contains all variables tied to the image duplicate collection in database.
 */
@Entity
@Table( name = "image_duplicate" )
data class ImageDuplicate(

        /**
         * Id of the record in database.
         */
        @Id
        @GeneratedValue( strategy = GenerationType.AUTO)
        var id: Long,

        /**
         * Path the image duplicate is located.
         */
        var path: String,

        /**
         * Instance of {@link Image} for which duplicate attached.
         */
        @ManyToOne
        @JoinColumn( name = "image_id" )
        var image: Image
)