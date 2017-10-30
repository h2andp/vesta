package net.h2andp.core.repositories

import net.h2andp.core.domain.Image
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

/**
 * Talks to the collection of {@link Image} in database.
 */
interface ImageRepository: JpaRepository<Image, Long> {

    /**
     * Find {@link Image} by it's hash.
     * @param hash hash of image to search by.
     * @return nullable instance of {@link Image}.
     */
    fun findByHash( hash: String ): Image?

    /**
     * Find already classified instances of {@link Image}.
     * @return collection of {@link Image}.
     */
    @Query("from Image img where img.indexed = true")
    fun findClassified(): List<Image>

    /**
     * Find already classified instances of {@link Image} pageable.
     * @param pageRequest {@link Pageable} pageable.
     * @return collection of {@link Image}.
     */
    @Query("from Image img where img.indexed = true")
    fun findClassifiedPageable( pageRequest: Pageable): List<Image>

    /**
     * Find already classified instances of {@link Image} pageable filtered by given class.
     * @param pageRequest {@link Pageable} pageable.
     * @param cls class to filter.
     * @return collection of {@link Image}.
     */
    @Query("select img from Image as img right join img.imageClassifications as imgc where imgc.classes like %:cls% and imgc.score >= 0.1")
    fun findClassifiedPageableByClass( @Param("cls") cls: String, pageRequest: Pageable ): List<Image>

    /**
     * Find already classified instances of {@link Image} filtered by given class.
     * @param cls class to filter.
     * @return collection of {@link Image}.
     */
    @Query("select img.id, img.path,img.indexed,img.hash,img.mime,img.error from ((select * from image) img right join (select distinct on(1) image_id, classes, score, id from image_classifications order by image_id, score desc) cls on img.id=cls.image_id) where cls.classes like %:cls%", nativeQuery = true)
    fun findClassifiedByClass( @Param("cls") cls: String ): List<Image>
}