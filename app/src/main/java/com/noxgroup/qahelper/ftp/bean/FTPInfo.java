package com.noxgroup.qahelper.ftp.bean;

/**
 * @Author: SongRan
 * @Date: 2021/1/19
 * @Desc: FTP配置信息
 */
public class FTPInfo {
    //10.8.1.201 : 21 /aa/bb noxuser duodian@123456
    public String serverName;//服务器名称(10.8.1.201)
    public String port;//端口(21)
    public String remotePath;//远程路径(/aa/bb，默认为/)
    public String username;//用户名(noxuser)
    public String password;//密码(duodian@123456)
    public String charset;//编码
    public String ftpMode;//主动被动
    public String anonymous;//匿名登录
    public String connectMode;//类型ftp/ftps
    public String ssl;//ftps协议
    public String ftpsPort;//默认端口
    public String trustMgr;//ftps证书类型
    public String displayName;//显示为：
    public long lastModified;//最后修改时间

    public FTPInfo(String serverName, String port, String remotePath, String username, String password, String charset, String ftpMode) {
        this.serverName = serverName;
        this.port = port;
        this.remotePath = remotePath;
        this.username = username;
        this.password = password;
        this.charset = charset;
        this.ftpMode = ftpMode;
    }

    @Override
    public String toString() {
        return "FTPInfo{" +
                "serverName='" + serverName + '\'' +
                ", port='" + port + '\'' +
                ", remotePath='" + remotePath + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", charset='" + charset + '\'' +
                ", ftpMode='" + ftpMode + '\'' +
                ", anonymous='" + anonymous + '\'' +
                ", connectMode='" + connectMode + '\'' +
                ", ssl='" + ssl + '\'' +
                ", ftpsport='" + ftpsPort + '\'' +
                ", trustmgr='" + trustMgr + '\'' +
                ", displayName='" + displayName + '\'' +
                ", lastModified=" + lastModified +
                '}';
    }
}
