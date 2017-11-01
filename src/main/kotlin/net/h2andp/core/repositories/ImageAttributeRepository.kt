package net.h2andp.core.repositories

import net.h2andp.core.domain.ImageAttribute
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Talks to the collection of {@link ImageAttribute} in database.
 */
interface ImageAttributeRepository : JpaRepository<ImageAttribute, Long>