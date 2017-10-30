package net.h2andp.core.domain

import javax.persistence.*
import javax.persistence.GenerationType.AUTO

/**
 * Contains all variables tied to the image classifications collection in database.
 */
@Entity
@Table( name = "image_classifications" )
data class ImageClassification (
        /**
         * Id of the record in database.
         */
        @Id
        @GeneratedValue( strategy = AUTO )
        var id: Long,

        /**
         * Class detected on image divided by ','.
         */
        var classes: String,

        /**
         * Score of the detected class.
         */
        var score: Float,

        /**
         * Instance of {@link Image} for which class attached.
         */
        @ManyToOne
        @JoinColumn( name = "image_id" )
        var image: Image
)