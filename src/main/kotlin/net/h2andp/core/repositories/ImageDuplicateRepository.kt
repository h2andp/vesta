package net.h2andp.core.repositories

import net.h2andp.core.domain.ImageDuplicate
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Talks to the collection of {@link ImageDuplicate} in database.
 */
interface ImageDuplicateRepository : JpaRepository<ImageDuplicate, Long>