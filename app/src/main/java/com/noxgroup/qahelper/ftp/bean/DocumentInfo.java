package com.noxgroup.qahelper.ftp.bean;

/**
 * @Author: SongRan
 * @Date: 2021/1/19
 * @Desc:
 */
public class DocumentInfo {
    public static final int FILE_TYPE = 0;
    public static final int DIRECTORY_TYPE = 1;
    public String documentId;
    public String displayName;
    public long size;
    public String path;
    public long lastModified;
    public int fileType;
}
