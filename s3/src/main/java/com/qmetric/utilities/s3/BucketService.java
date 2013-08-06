package com.qmetric.utilities.s3;

import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.io.CharStreams;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.jets3t.service.S3Service;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.ServiceException;
import org.jets3t.service.model.S3Bucket;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.utils.ServiceUtils;
import org.joda.time.DateTime;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static com.google.common.collect.FluentIterable.from;
import static java.util.Arrays.asList;

/**
 * Encapsulates storage and retrieval of strings to a single S3 bucket.
 */
public class BucketService {
    private final Logger logger = Logger.getLogger(BucketService.class);

    static final String NO_SUCH_KEY = "NoSuchKey";

    private final S3Service s3Service;

    private final S3Bucket bucket;

    @SuppressWarnings("unused")
    BucketService() {
        this.s3Service = null;
        this.bucket = null;
    }

    public BucketService(final S3Service s3Service, final String bucketName) throws S3ServiceException {
        this.s3Service = s3Service;

        final S3Bucket bucket = s3Service.getBucket(bucketName);

        if (bucket == null) {
            logger.warn(String.format("Bucket called %s cannot be found on Amazon S3. Creating this bucket now...", bucketName));
            try {
                this.bucket = s3Service.createBucket(bucketName);
                logger.warn(String.format("Bucket called %s now created.", bucketName));
            } catch (S3ServiceException e) {
                throw new RuntimeException(String.format("S3Service failed to create missing bucket %s", bucketName), e);
            }
        } else {
            this.bucket = bucket;
        }
    }

    public void store(String key, String data, String contentType) throws IOException, NoSuchAlgorithmException, S3ServiceException {
        if(key == null || key.length() == 0) throw new RuntimeException("key must have a value");
        if(data == null || data.length() ==0) throw new RuntimeException("data must have a value");
        if(contentType == null || contentType.length() ==0) throw new RuntimeException("contentType must have a value");

        S3Object s3Object = new S3Object(key, data);
        s3Object.setContentType(contentType);
        s3Object.setContentLength(data.getBytes().length);

        s3Service.putObject(bucket.getName(), s3Object);
    }

    /**
     * @deprecated use BucketService.store(String key, String data, String contentType)
     */
    @Deprecated
    public void store(String key, String data) throws IOException, NoSuchAlgorithmException, S3ServiceException {
        S3Object stringData = new S3Object(key, data);
        s3Service.putObject(bucket, stringData);
    }

    public Optional<InputStream> retrieveAsInputStream(String key) {
        final Optional<S3Object> s3ObjectSearch = retrieveS3Object(key);

        if (!s3ObjectSearch.isPresent()) {
            return Optional.absent();
        }

        try {
            final S3Object s3Object = s3ObjectSearch.get();
            final InputStream dataInputStream = s3Object.getDataInputStream();
            final byte[] content = ServiceUtils.readInputStreamToBytes(dataInputStream);

            if (verifyData(s3Object, content)) {
                return Optional.of((InputStream) new ByteArrayInputStream(content));
            }
        } catch (IOException e) {
            logError(e);
        } catch (ServiceException e) {
            logError(e);
        }

        return Optional.absent();
    }

    public Optional<String> retrieveString(String key) {
        final Optional<InputStream> inputStreamSearch = retrieveAsInputStream(key);

        if (inputStreamSearch.isPresent()) {
            try {
                return Optional.of(getStringFromStream(inputStreamSearch.get()));
            } catch (IOException e) {
                logError(e);
            }
        }

        return Optional.absent();
    }

    public BucketItems listItems(final String prefix, final String delimiter) {
        try {
            return new BucketItems(from(asList(s3Service.listObjects(bucket.getName(), prefix, delimiter))).transform(new Function<S3Object, BucketItem>() {
                @Override public BucketItem apply(final S3Object input) {
                    return new BucketItem(input.getKey(), new DateTime(input.getLastModifiedDate()));
                }
            }).toImmutableList());
        } catch (S3ServiceException e) {
            logError(e);
            throw new RuntimeException(e);
        }
    }

    private Optional<S3Object> retrieveS3Object(String key) {
        try {
            return Optional.of(s3Service.getObject(bucket.getName(), key));
        } catch (S3ServiceException e) {
            if (!NO_SUCH_KEY.equals(e.getS3ErrorCode())) {
                logError(e);
            }
            return Optional.absent();
        }
    }

    public String getBucketName() {
        return bucket.getName();
    }

    private boolean verifyData(S3Object s3Object, byte[] content) {
        try {
            return s3Object.verifyData(content);
        } catch (NoSuchAlgorithmException e) {
            logError(e);
        } catch (IOException e) {
            logError(e);
        }

        return false;
    }

    private String getStringFromStream(InputStream stream) throws IOException {
        return CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8));
    }

    private void logError(Throwable t) {
        logger.error("Error storing/retrieving data with BucketService", t);
    }

    public static class BucketItems {
        private final List<BucketItem> items;

        public BucketItems(final List<BucketItem> items) {
            this.items = items;
        }

        public List<BucketItem> all() {
            return items;
        }

        @Override public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }

        @Override public boolean equals(final Object obj) {
            return EqualsBuilder.reflectionEquals(this, obj);
        }

        @Override public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }
    }

    public static class BucketItem {
        public final String path;

        public final DateTime lastModified;

        public BucketItem(final String path, final DateTime lastModified) {
            this.path = path;
            this.lastModified = lastModified;
        }

        @Override public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }

        @Override public boolean equals(final Object obj) {
            return EqualsBuilder.reflectionEquals(this, obj);
        }

        @Override public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }
    }
}
