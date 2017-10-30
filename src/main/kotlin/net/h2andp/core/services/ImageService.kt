package net.h2andp.core.services

import net.h2andp.core.domain.Image

/**
 * Service that works with {@link Image}.
 */
interface ImageService {

    /**
     * Returns collection of classified images.
     * @return collection of {@link Image}.
     */
    fun getAllClassified(): Collection<Image>

    /**
     * Returns collection of classified images by given pages range.
     * @param from start from page
     * @param to till page.
     * @return collection of {@link Image}.
     */
    fun getClassifiedPageable( from: Int, to: Int ): Collection<Image>

    /**
     * Returns collection of images by given class.
     * @param cls class to search by.
     * @return collection of {@link Image}.
     */
    fun getAllByClass( cls: String ): Collection<Image>

    /**
     * Returns collection of images by given class by given pages range.
     * @param cls class to search by.
     * @param from start from page
     * @param to till page.
     * @return collection of {@link Image}.
     */
    fun getByClassPageable( cls: String, from: Int, to: Int ): Collection<Image>

    /**
     * Returns collection of detected classes.
     * @return collection of classes.
     */
    fun getDetectedClasses(): Collection<String>
}