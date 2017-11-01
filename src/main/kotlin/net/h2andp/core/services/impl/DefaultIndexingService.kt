package net.h2andp.core.services.impl

import com.google.common.hash.Hashing
import com.google.common.io.Files
import net.h2andp.core.domain.Image
import net.h2andp.core.domain.ImageAttribute
import net.h2andp.core.domain.ImageDuplicate
import net.h2andp.core.repositories.ImageAttributeRepository
import net.h2andp.core.repositories.ImageDuplicateRepository
import net.h2andp.core.repositories.ImageRepository
import net.h2andp.core.services.IndexingService
import org.apache.commons.io.FileUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.File
import java.net.URI
import java.nio.file.Files.probeContentType
import java.util.*

/**
 * Default implementation of the {@link IndexingService}.
 */
@Service
class DefaultIndexingService(

        @Autowired
        val imageRepository: ImageRepository,

        @Autowired
        val imageAttributeRepository: ImageAttributeRepository,

        @Autowired
        val imageDuplicateRepository: ImageDuplicateRepository

): IndexingService {

    override fun index( path: URI, recursive: Boolean ) {
       for ( file in FileUtils.iterateFiles( File(path), null, recursive ) ) {

           detectMimeType( file ).ifPresent({ mime ->
               if ( mime.startsWith( "image" ) ) {
                    calculateHash( file ).ifPresent( { hash ->
                        var maybeIndexedImage = imageRepository.findByHash( hash ).orElse( null )
                        val duplicates = mutableSetOf<ImageDuplicate>()
                        var attributes = mutableSetOf<ImageAttribute>()
                        if ( maybeIndexedImage != null ) {
                            duplicates.add( ImageDuplicate( null, file.absolutePath, null  ) )
                        } else {
                            maybeIndexedImage = Image(
                                    null,
                                    file.absolutePath,
                                    false,
                                    hash,
                                    mime,
                                    null,
                                    emptySet(),
                                    emptySet(),
                                    emptySet()
                            )
                            attributes = extractMetadata( file )
                        }
                        saveImage( maybeIndexedImage, duplicates, attributes )
                    } )
               }
           })
       }
    }

    /**
     * Save given image to database.
     * @param image image to save.
     * @param attributes image attributes to save.
     * @param duplicates image duplicates to save.
     */
    private fun saveImage( image: Image, duplicates: Set<ImageDuplicate>, attributes: Set<ImageAttribute> ){
        val img = imageRepository.save( image )

        duplicates.forEach( { it.image = img } )
        imageDuplicateRepository.save( duplicates )

        attributes.forEach( { it.image = img } )
        imageAttributeRepository.save( attributes )

    }

    /**
     * Trying to detect file's mime type.
     * @param file file for which we are trying to detect mime code.
     * @return {@link Optional} which maybe contains mime.
     */
    private fun detectMimeType( file: File ): Optional<String> {
        try {
            return Optional.of( probeContentType( file.toPath() ) )
        } catch ( e: Exception ) {
            //TODO add logging
        }

        return Optional.empty()
    }

    /**
     * Trying to calculate MD5 hash of the given file.
     * @param file file to which we are trying to calculate hash code.
     * @return {@link Optional} which maybe contains calculated hash.
     */
    private fun calculateHash( file: File ): Optional<String> {
        try {
            return Optional.of( Files.hash( file, Hashing.md5() ).toString() )
        } catch ( e: Exception ) {
            //TODO add logging
        }

        return Optional.empty()
    }

    /**
     * Extracts image metadata by given path.
     * @param file file to extract metadata.
     * @return mutable set of {}
     */
    private fun extractMetadata( file: File ): MutableSet<ImageAttribute> {
        val attributes = mutableSetOf<ImageAttribute>()
        try {


        } catch ( e: Throwable ){
            //TODO add logging
        }
        return attributes
    }
}