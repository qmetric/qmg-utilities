package com.qmetric.utilities.s3;

import com.google.common.base.Charsets;
import com.google.common.base.Optional;
import com.google.common.io.CharStreams;
import org.apache.log4j.Logger;
import org.jets3t.service.S3Service;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.ServiceException;
import org.jets3t.service.model.S3Bucket;
import org.jets3t.service.model.S3Object;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;

/**
 * Encapsulates storage and retrieval of strings to a single S3 bucket.
 */
public class BucketService
{
    private final Logger logger = Logger.getLogger(BucketService.class);

    static final String NO_SUCH_KEY = "NoSuchKey";

    private final S3Service s3Service;

    private final S3Bucket bucket;

    @SuppressWarnings("unused")
    BucketService()
    {
        this.s3Service = null;
        this.bucket = null;
    }

    public BucketService(final S3Service s3Service, final String bucketName) throws S3ServiceException
    {
        this.s3Service = s3Service;
        this.bucket = s3Service.getBucket(bucketName);
        if (bucket == null)
        {
            throw new IllegalArgumentException(String.format("Bucket called %s cannot be found on Amazon S3", bucketName));
        }
    }

    public void store(String key, String data) throws IOException, NoSuchAlgorithmException, S3ServiceException
    {
        S3Object stringData = new S3Object(key, data);
        s3Service.putObject(bucket, stringData);
    }

    public Optional<String> retrieve(String key)
    {
        S3Object s3Object;
        try
        {
            s3Object = s3Service.getObject(bucket.getName(), key);
        }
        catch (S3ServiceException e)
        {
            if (!NO_SUCH_KEY.equals(e.getS3ErrorCode()))
            {
                logError(e);
            }
            return Optional.absent();
        }

        try
        {
            String content = getStringFromStream(s3Object.getDataInputStream());

            if (verifyData(s3Object, content.getBytes(Charsets.UTF_8)))
            {
                return Optional.of(content);
            }
        }
        catch (IOException e)
        {
            logError(e);
        }
        catch (ServiceException e)
        {
            logError(e);
        }

        return Optional.absent();
    }

    private boolean verifyData(S3Object s3Object, byte[] content)
    {
        try
        {
            return s3Object.verifyData(content);
        }
        catch (NoSuchAlgorithmException e)
        {
            logError(e);
        }
        catch (IOException e)
        {
            logError(e);
        }

        return false;
    }

    private String getStringFromStream(InputStream stream) throws IOException
    {
        return CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8));
    }

    private void logError(Throwable t)
    {
        logger.error("Error storing/retrieving data with BucketService", t);
    }
}
