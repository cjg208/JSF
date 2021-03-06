package com.jsf.utils.sdk.fdfs.proto.tracker.internal;

import com.jsf.utils.sdk.fdfs.proto.CmdConstants;
import com.jsf.utils.sdk.fdfs.proto.FdfsRequest;
import com.jsf.utils.sdk.fdfs.proto.ProtoHead;

/**
 * 获取存储节点请求
 * 
 * @author tobato
 *
 */
public class TrackerGetStoreStorageRequest extends FdfsRequest {

    private static final byte withoutGroupCmd = CmdConstants.TRACKER_PROTO_CMD_SERVICE_QUERY_STORE_WITHOUT_GROUP_ONE;

    /**
     * 获取存储节点
     * 
     * @param groupName
     */
    public TrackerGetStoreStorageRequest() {
        super();
        this.head = new ProtoHead(withoutGroupCmd);
    }

}
