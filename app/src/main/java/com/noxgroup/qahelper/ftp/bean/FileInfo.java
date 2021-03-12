package com.noxgroup.qahelper.ftp.bean;

/**
 * @Author: SongRan
 * @Date: 2021/1/21
 * @Desc:
 */
public class FileInfo {
    String fileName;
    String filePath;
    boolean isCheck;
    boolean isApk;

    public FileInfo(String fileName, String filePath, boolean isCheck, boolean isApk) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.isCheck = isCheck;
        this.isApk = isApk;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public boolean isApk() {
        return isApk;
    }

    public void setApk(boolean apk) {
        isApk = apk;
    }
}
