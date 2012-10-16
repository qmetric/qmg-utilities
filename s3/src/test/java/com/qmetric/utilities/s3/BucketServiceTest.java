package com.qmetric.utilities.s3;

import com.google.common.base.Optional;
import org.jets3t.service.S3Service;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.ServiceException;
import org.jets3t.service.model.S3Bucket;
import org.jets3t.service.model.S3Object;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BucketServiceTest
{

    private final String key = "key";

    private final String data = "data";

    private final S3Service s3Service = mock(S3Service.class);

    private final String bucketName = "bucket";

    private final S3Bucket bucket = mock(S3Bucket.class);

    private BucketService bucketService;

    @Before
    public void setUp() throws S3ServiceException
    {
        when(bucket.getName()).thenReturn(bucketName);
        when(s3Service.getBucket(bucketName)).thenReturn(bucket);
        bucketService = new BucketService(s3Service, bucketName);
    }

    @After
    public void tearDown()
    {
        bucketService = null;
    }

    @Test
    public void zeroArgsConstructorExists()
    {
        new BucketService();
    }

    @Test(expected = IllegalArgumentException.class)
    public void ShouldNotCreateServiceWithInvalidBucket() throws S3ServiceException
    {
        when(s3Service.getBucket(bucketName)).thenReturn(null);
        bucketService = new BucketService(s3Service, bucketName);
    }

    @Test
    public void shouldStoreDataInConfiguredBucket() throws IOException, NoSuchAlgorithmException, S3ServiceException
    {
        bucketService.store(key, data);

        verify(s3Service).putObject(eq(bucket), any(S3Object.class));
    }

    @Test
    public void shouldRetrieveVerifiedContent() throws ServiceException, IOException, NoSuchAlgorithmException
    {
        final S3Object s3Object = mock(S3Object.class);
        when(s3Object.getDataInputStream()).thenReturn(new ByteArrayInputStream(data.getBytes()));
        when(s3Object.verifyData(data.getBytes())).thenReturn(true);

        when(s3Service.getObject(bucketName, key)).thenReturn(s3Object);

        Optional<String> retrieved = bucketService.retrieve(key);
        assertTrue(retrieved.isPresent());
        assertEquals(data, retrieved.get());
    }

    @Test
    public void shouldReturnAbsentIfContentUnverified() throws ServiceException, IOException, NoSuchAlgorithmException
    {
        final S3Object s3Object = mock(S3Object.class);
        when(s3Object.getDataInputStream()).thenReturn(new ByteArrayInputStream(data.getBytes()));
        when(s3Object.verifyData(data.getBytes())).thenReturn(false);

        when(s3Service.getObject(bucketName, key)).thenReturn(s3Object);

        assertFalse(bucketService.retrieve(key).isPresent());
    }

    @Test
    public void shouldReturnAbsentIfVerifyThrowsNoSuchAlgorithm() throws ServiceException, IOException, NoSuchAlgorithmException
    {
        final S3Object s3Object = mock(S3Object.class);
        when(s3Object.getDataInputStream()).thenReturn(new ByteArrayInputStream(data.getBytes()));
        when(s3Object.verifyData(data.getBytes())).thenThrow(new NoSuchAlgorithmException());

        when(s3Service.getObject(bucketName, key)).thenReturn(s3Object);

        assertFalse(bucketService.retrieve(key).isPresent());
    }

    @Test
    public void shouldReturnAbsentIfVerifyThrowsIOException() throws ServiceException, IOException, NoSuchAlgorithmException
    {
        final S3Object s3Object = mock(S3Object.class);
        when(s3Object.getDataInputStream()).thenReturn(new ByteArrayInputStream(data.getBytes()));
        when(s3Object.verifyData(data.getBytes())).thenThrow(new IOException());

        when(s3Service.getObject(bucketName, key)).thenReturn(s3Object);

        assertFalse(bucketService.retrieve(key).isPresent());
    }

    @Test
    public void shouldReturnAbsentIfNoKey() throws S3ServiceException
    {
        final S3ServiceException s3ServiceException = mock(S3ServiceException.class);
        when(s3ServiceException.getS3ErrorCode()).thenReturn(BucketService.NO_SUCH_KEY);
        when(s3Service.getObject(bucketName, key)).thenThrow(s3ServiceException);

        assertFalse(bucketService.retrieve(key).isPresent());
    }

    @Test
    public void shouldReturnAbsentIfS3ServiceExceptionThrown() throws S3ServiceException
    {
        when(s3Service.getObject(bucketName, key)).thenThrow(new S3ServiceException());

        assertFalse(bucketService.retrieve(key).isPresent());
    }

    @Test
    public void shouldReturnAbsentIfServiceExceptionThrown() throws ServiceException
    {
        final S3Object s3Object = mock(S3Object.class);
        when(s3Object.getDataInputStream()).thenThrow(new ServiceException());
        when(s3Service.getObject(bucketName, key)).thenReturn(s3Object);

        assertFalse(bucketService.retrieve(key).isPresent());
    }

    @Test
    public void shouldReturnAbsentIfIOExceptionThrown() throws ServiceException
    {
        final InputStream inputStream = mock(InputStream.class);

        final S3Object s3Object = mock(S3Object.class);
        when(s3Object.getDataInputStream()).thenReturn(inputStream);

        when(s3Service.getObject(bucketName, key)).thenReturn(s3Object);

        assertFalse(bucketService.retrieve(key).isPresent());
    }
}
