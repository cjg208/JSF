package com.jsf.utils.sdk.fdfs.proto.storage;

import com.jsf.utils.sdk.fdfs.proto.AbstractFdfsCommand;
import com.jsf.utils.sdk.fdfs.proto.FdfsResponse;
import com.jsf.utils.sdk.fdfs.proto.storage.internal.StorageAppendFileRequest;

import java.io.InputStream;

/**
 * 添加文件命令
 * 
 * @author tobato
 *
 */
public class StorageAppendFileCommand extends AbstractFdfsCommand<Void> {

    public StorageAppendFileCommand(InputStream inputStream, long fileSize, String path) {
        this.request = new StorageAppendFileRequest(inputStream, fileSize, path);
        // 输出响应
        this.response = new FdfsResponse<Void>() {
            // default response
        };
    }

}
