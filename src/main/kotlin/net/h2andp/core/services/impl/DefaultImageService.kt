package net.h2andp.core.services.impl

import net.h2andp.core.domain.Image
import net.h2andp.core.domain.ImageClassification
import net.h2andp.core.repositories.ImageClassificationRepository
import net.h2andp.core.repositories.ImageRepository
import net.h2andp.core.services.ImageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

/**
 * Default implementation of {@link ImageService}.
 */
@Service
class DefaultImageService(
        @Autowired
        val imageRepository: ImageRepository,

        @Autowired
        val imageClassificationRepository: ImageClassificationRepository ) : ImageService {

    override fun getAllClassified(): Collection<Image> = imageRepository.findClassified()

    override fun getClassifiedPageable(
            from: Int, to: Int
    ): Collection<Image> = imageRepository.findClassifiedPageable( PageRequest( from, to ) )

    override fun getAllByClass( cls: String ): Collection<Image> = imageRepository.findClassifiedByClass( cls )

    override fun getByClassPageable( cls: String,
                                    from: Int,
                                    to: Int ): Collection<Image> = imageRepository.findClassifiedPageableByClass(
            cls, PageRequest( from, to )
    )

    override fun getDetectedClasses(): Collection<String> = imageClassificationRepository
            .findAll()
            .map(ImageClassification::classes)
            .toSet()
}