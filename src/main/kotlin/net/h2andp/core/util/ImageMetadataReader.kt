package net.h2andp.core.util

import com.drew.imaging.FileType
import com.drew.imaging.FileTypeDetector
import com.drew.imaging.ImageProcessingException
import com.drew.imaging.jpeg.JpegMetadataReader
import java.io.InputStream
import com.drew.lang.annotations.NotNull
import com.drew.metadata.Metadata
import com.drew.metadata.adobe.AdobeJpegReader
import com.drew.metadata.exif.ExifReader
import com.drew.metadata.file.FileMetadataReader
import com.drew.metadata.icc.IccReader
import com.drew.metadata.iptc.IptcReader
import com.drew.metadata.jfif.JfifReader
import com.drew.metadata.jfxx.JfxxReader
import com.drew.metadata.jpeg.JpegCommentReader
import com.drew.metadata.jpeg.JpegDhtReader
import com.drew.metadata.jpeg.JpegDnlReader
import com.drew.metadata.jpeg.JpegReader
import com.drew.metadata.photoshop.DuckyReader
import com.drew.metadata.photoshop.PhotoshopReader
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream

/**
 * Image metadata reader extracted from {@link com.drew.imaging.ImageMetadataReader} to avoid using XMP reader due to xmp core error.
 */
object ImageMetadataReader {

    /**
     * Reads metadata from an {@link InputStream}.
     *
     * @param inputStream a stream from which the file data may be read.  The stream must be positioned at the
     *                    beginning of the file's data.
     * @return a populated {@link Metadata} object containing directories of tags with values and any processing errors.
     * @throws ImageProcessingException if the file type is unknown, or for general processing errors.
     */
    @NotNull
    fun readMetadata( @NotNull inputStream: InputStream ): Metadata {
        return readMetadata(inputStream, -1)
    }

    /**
     * Reads metadata from an {@link InputStream} of known length.
     *
     * @param inputStream a stream from which the file data may be read.  The stream must be positioned at the
     *                    beginning of the file's data.
     * @param streamLength the length of the stream, if known, otherwise -1.
     * @return a populated {@link Metadata} object containing directories of tags with values and any processing errors.
     * @throws ImageProcessingException if the file type is unknown, or for general processing errors.
     */
    @NotNull
    fun readMetadata( @NotNull inputStream: InputStream, streamLength: Long ): Metadata {
        val bufferedInputStream: BufferedInputStream = inputStream as? BufferedInputStream ?: BufferedInputStream( inputStream )
        val fileType: FileType = FileTypeDetector.detectFileType( bufferedInputStream )
        if( fileType == FileType.Jpeg ) {
            return JpegMetadataReader.readMetadata( bufferedInputStream, listOf(
                    JpegReader(),
                    JpegCommentReader(),
                    JfifReader(),
                    JfxxReader(),
                    ExifReader(),
                    IccReader(),
                    PhotoshopReader(),
                    DuckyReader(),
                    IptcReader(),
                    AdobeJpegReader(),
                    JpegDhtReader(),
                    JpegDnlReader()
            ) )
        }

        throw ImageProcessingException("File format is not supported")
    }

    /**
     * Reads {@link Metadata} from a {@link File} object.
     *
     * @param file a file from which the image data may be read.
     * @return a populated {@link Metadata} object containing directories of tags with values and any processing errors.
     * @throws ImageProcessingException for general processing errors.
     */
    @NotNull
    fun readMetadata( @NotNull file: File): Metadata? {
        val inputStream = FileInputStream( file )
        var metadata: Metadata? = null
        inputStream.use {
            metadata = readMetadata( inputStream, file.length() )
            FileMetadataReader().read( file, metadata )
        }

        return metadata

    }
}