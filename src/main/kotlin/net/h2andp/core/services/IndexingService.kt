package net.h2andp.core.services

import java.net.URI

/**
 * Index images in path.
 */
interface IndexingService {

    /**
     * Index images found in the given path by {@link URI}.
     * @param path path to search images in.
     * @param recursive recursive going through folders.
     */
    fun index( path: URI, recursive: Boolean )
}