package com.qmetric.utilities.file;

import com.intridea.io.vfs.provider.s3.S3FileProvider;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemOptions;
import org.apache.commons.vfs.auth.StaticUserAuthenticator;
import org.apache.commons.vfs.impl.DefaultFileSystemConfigBuilder;

/**
 * Created: Jul 18, 2011, Author: Dom Farr
 */

public class S3VirtualFileSystem
{
    public S3VirtualFileSystem(final String id, final String password) throws FileSystemException
    {
        StaticUserAuthenticator userAuthenticator = new StaticUserAuthenticator(null, id, password);

        FileSystemOptions opts = S3FileProvider.getDefaultFileSystemOptions();

        DefaultFileSystemConfigBuilder.getInstance().setUserAuthenticator(opts, userAuthenticator);
    }
}
