package net.h2andp.core.repositories

import net.h2andp.core.domain.ImageClassification
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Talks to the collection of {@link ImageClassification} in database.
 */
interface ImageClassificationRepository : JpaRepository<ImageClassification, Long>