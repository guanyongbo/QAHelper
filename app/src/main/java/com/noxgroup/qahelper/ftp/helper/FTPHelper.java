package com.noxgroup.qahelper.ftp.helper;

import android.text.TextUtils;
import android.util.Log;

import com.noxgroup.qahelper.ftp.bean.DocumentInfo;
import com.noxgroup.qahelper.ftp.bean.FTPInfo;
import com.noxgroup.qahelper.util.QALogUtil;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.parser.DefaultFTPFileEntryParserFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: SongRan
 * @Date: 2021/1/19
 * @Desc: FTP连接管理
 */
public class FTPHelper {
    private static FTPHelper INSTANCE = null;
    private static FTPClient ftpClient;
    private static FTPInfo ftpInfo;

    private FTPHelper() {
    }

    public static FTPHelper getInstance() {
        if (INSTANCE == null) {
            synchronized (FTPHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FTPHelper();
                }
            }
        }
        return INSTANCE;
    }

    public boolean isConnected() {
        return ftpClient != null && ftpClient.isConnected();
    }

    public void disConnect() {
        try {
            if (ftpClient != null) {
                ftpClient.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean login(FTPInfo info) {
        ftpInfo = info;
        ftpClient = new FTPClient();
        ftpClient.setParserFactory(new DefaultFTPFileEntryParserFactory());
        ftpClient.setControlEncoding(ftpInfo.charset);
        int reply;
        try {
            ftpClient.connect(ftpInfo.serverName, Integer.parseInt(ftpInfo.port));
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return false;
            }
            boolean loginSuccess = false;
            if (!TextUtils.isEmpty(ftpInfo.username)) {
                loginSuccess = ftpClient.login(ftpInfo.username, ftpInfo.password);
            } else {
                loginSuccess = ftpClient.login("anonymous", "anonymous");
            }
            if (!loginSuccess) {
                Log.e(QALogUtil.TAG_QA, "FTPHelper login: loginFail");
                return false;
            } else {
                Log.e("SRLog", "FTPHelper login: success");
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                if (!TextUtils.isEmpty(ftpInfo.ftpMode) && "PASSIVE".equals(ftpInfo.ftpMode)) {
                    ftpClient.enterLocalPassiveMode();
                }
                if (!TextUtils.isEmpty(ftpInfo.remotePath)) {
                    return ftpClient.changeWorkingDirectory(ftpInfo.remotePath);
                }
                return true;
            }

        } catch (Exception ex) {
            return false;
        }
    }

    public List<DocumentInfo> login1(FTPInfo info) {
        ftpInfo = info;
        List<DocumentInfo> documentInfoList = new ArrayList<DocumentInfo>();
        ftpClient = new FTPClient();
        ftpClient.setParserFactory(new DefaultFTPFileEntryParserFactory());
        ftpClient.setControlEncoding(ftpInfo.charset);
        int reply;
        try {
            ftpClient.connect(ftpInfo.serverName, Integer.parseInt(ftpInfo.port));
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return null;
            }
            boolean loginSuccess = false;
            if (!TextUtils.isEmpty(ftpInfo.username)) {
                loginSuccess = ftpClient.login(ftpInfo.username, ftpInfo.password);
            } else {
                loginSuccess = ftpClient.login("anonymous", "anonymous");
            }
            if (!loginSuccess) {
                Log.e(QALogUtil.TAG_QA, "FTPHelper login: loginFail");
                return null;
            }
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            if (!TextUtils.isEmpty(ftpInfo.ftpMode) && "PASSIVE".equals(ftpInfo.ftpMode)) {
                ftpClient.enterLocalPassiveMode();
            }
            if (!TextUtils.isEmpty(ftpInfo.remotePath)) {
                if (!ftpClient.changeWorkingDirectory(ftpInfo.remotePath)) {
                    return null;
                }
            }
            FTPFile[] listFiles = ftpClient.listFiles();
            for (FTPFile tempFile : listFiles) {
                DocumentInfo documentInfo = new DocumentInfo();
                documentInfo.displayName = tempFile.getName();
                if (tempFile.isDirectory()) {
                    documentInfo.size = ftpClient.listFiles(tempFile.getName()).length;
                } else {
                    documentInfo.size = tempFile.getSize();
                }
                documentInfo.lastModified = tempFile.getTimestamp().getTimeInMillis() + Math.abs(tempFile.getTimestamp().getTimeZone().getRawOffset());
                documentInfo.fileType = tempFile.isFile() ? DocumentInfo.FILE_TYPE : DocumentInfo.DIRECTORY_TYPE;
                documentInfo.documentId = ftpInfo.serverName + ":" + ftpInfo.port;
                documentInfo.path = ftpClient.printWorkingDirectory();
                documentInfoList.add(documentInfo);
            }
            return documentInfoList;
        } catch (Exception ex) {
            return null;
        }
    }


    public List<DocumentInfo> listSubFile(String dir) {
        List<DocumentInfo> subFileList = new ArrayList<DocumentInfo>();
        if (ftpClient == null || !ftpClient.isConnected()) {
            Log.e("SRLog", "FTPHelper listSubFile: 未登录");
            login(ftpInfo);
        }
        try {
            ftpClient.changeWorkingDirectory(dir);
            FTPFile[] listFiles = ftpClient.listFiles(dir);
            for (FTPFile tempFile : listFiles) {
                DocumentInfo documentInfo = new DocumentInfo();
                documentInfo.displayName = tempFile.getName();
                if (tempFile.isDirectory()) {
                    documentInfo.size = ftpClient.listFiles(tempFile.getName()).length;
                } else {
                    documentInfo.size = tempFile.getSize();
                }
                documentInfo.lastModified = tempFile.getTimestamp().getTimeInMillis() + Math.abs(tempFile.getTimestamp().getTimeZone().getRawOffset());
                documentInfo.fileType = tempFile.isFile() ? DocumentInfo.FILE_TYPE : DocumentInfo.DIRECTORY_TYPE;
                documentInfo.documentId = ftpInfo.serverName + ":" + ftpInfo.port;
                documentInfo.path = ftpClient.printWorkingDirectory();
                subFileList.add(documentInfo);
            }
        } catch (Exception e) {
            Log.e("SRLog", "FTPHelper listSubFile: fail");
            e.printStackTrace();
        }
        return subFileList;
    }

    public List<DocumentInfo> searchApk(String dir) {
        List<DocumentInfo> searchFileList = new ArrayList<DocumentInfo>();
        if (ftpClient == null || !ftpClient.isConnected()) {
            Log.e("SRLog", "FTPHelper listSubFile: 未登录");
            login(ftpInfo);
        }
        try {
            ftpClient.changeWorkingDirectory(dir);
            FTPFile[] listFiles = ftpClient.listFiles(dir);
            for (FTPFile tempFile : listFiles) {
                if (tempFile.isDirectory()) {
                    listAPKFile(dir + "/" + tempFile.getName(), searchFileList);
                }
            }
        } catch (Exception e) {
            Log.e("SRLog", "FTPHelper listAppFile: 查询文件报错");
            e.printStackTrace();
        }
        return searchFileList;
    }

    private void listAPKFile(String dir, List<DocumentInfo> searchFileList) {
        try {
            ftpClient.changeWorkingDirectory(dir);
            FTPFile[] listFiles = ftpClient.listFiles(dir);
            for (FTPFile tempFile : listFiles) {
                if (tempFile.isDirectory()) {
                    listAPKFile(ftpClient.printWorkingDirectory() + "/" + tempFile.getName(), searchFileList);
                } else {
                    if (tempFile.getName().endsWith(".apk")) {
                        DocumentInfo documentInfo = new DocumentInfo();
                        documentInfo.displayName = tempFile.getName();
                        documentInfo.size = tempFile.getSize();
                        documentInfo.lastModified = tempFile.getTimestamp().getTimeInMillis();
                        documentInfo.fileType = tempFile.isFile() ? DocumentInfo.FILE_TYPE : DocumentInfo.DIRECTORY_TYPE;
                        documentInfo.documentId = ftpInfo.serverName + ":" + ftpInfo.port;
                        documentInfo.path = ftpClient.printWorkingDirectory();
                        searchFileList.add(documentInfo);
                    }
                }
            }
        } catch (Exception e) {
            Log.e("SRLog", "FTPHelper listAPKFile: 查询文件报错");
            e.printStackTrace();
        }
    }

    public boolean download(String remoteFilePath, OutputStream fileOutputStream) {
        if (ftpClient == null || !ftpClient.isConnected()) {
            login(ftpInfo);
        }
        try {
            return ftpClient.retrieveFile(remoteFilePath, fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
