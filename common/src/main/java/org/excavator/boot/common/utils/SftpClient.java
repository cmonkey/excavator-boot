/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.excavator.boot.common.utils;

import com.jcraft.jsch.*;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Vector;

public class SftpClient {

    private final static Logger logger = LoggerFactory.getLogger(SftpClient.class);

    private SftpItem            sftpItem;

    private ChannelSftp         sftp;

    private Session             session;
    /**
     * SFTP 登录用户名
     */
    private String              username;
    /**
     * SFTP 登录密码
     */
    private String              password;
    /**
     * 私钥
     */
    private String              privateKey;
    /**
     * SFTP 服务器地址IP地址
     */
    private String              host;
    /**
     * SFTP 端口
     */
    private int                 port;

    private int                 mode   = 0;

    public SftpClient() {
    }

    public SftpClient(SftpItem sftpItem) {
        if (sftpItem == null) {
            return;
        }
        this.sftpItem = sftpItem;
        this.username = sftpItem.user();
        this.password = sftpItem.password();
        this.privateKey = sftpItem.privateKey();
        this.host = sftpItem.ip();
        this.port = sftpItem.port();
        this.mode = sftpItem.model();
    }

    public SftpClient(String username, String password, String privateKey, String host, int port) {
        this.username = username;
        this.password = password;
        this.privateKey = privateKey;
        this.host = host;
        this.port = port;
    }

    public SftpClient(String username, String password, String privateKey, String host, int port,
                      int mode) {
        this.username = username;
        this.password = password;
        this.privateKey = privateKey;
        this.host = host;
        this.port = port;
        this.mode = mode;
    }

    /**
     * 连接sftp服务器
     */
    public boolean login() {
        try {
            JSch jsch = new JSch();
            if (privateKey != null) {
                jsch.addIdentity(privateKey);// 设置私钥
            }

            session = jsch.getSession(username, host, port);

            if (password != null) {
                session.setPassword(password);
            }
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");

            session.setConfig(config);
            session.connect();
            logger.info("sftp ip:{} port{} connect succss", host, port);
            Channel channel = session.openChannel("sftp");
            channel.connect();

            sftp = (ChannelSftp) channel;
            return true;
        } catch (JSchException e) {
            logger.error("系统异常", e);
            return false;
        }
    }

    /**
     * 关闭连接 server
     */
    public void logout() {
        if (sftp != null) {
            if (sftp.isConnected()) {
                sftp.disconnect();
            }
        }
        if (session != null) {
            if (session.isConnected()) {
                session.disconnect();
            }
        }
        logger.info("close success");
    }

    /**
     * 将输入流的数据上传到sftp作为文件。文件完整路径
     *
     * @param sftpFileName 服务器的目录路径
     * @param sftpFileName sftp端文件名
     * @param input        输入流
     */
    public void upload(String sftpFileName, InputStream input) throws SftpException, IOException {
        String remotePath = sftpItem.path();
        upload(remotePath, sftpFileName, input);
    }

    /**
     * 将输入流的数据上传到sftp作为文件。文件完整路径
     *
     * @param remotePath   服务器的目录路径
     * @param sftpFileName sftp端文件名
     * @param input        输入流
     */
    public boolean upload(String remotePath, String sftpFileName, InputStream input)
                                                                                    throws SftpException,
                                                                                    IOException {
        try {
            sftp.cd(remotePath);
        } catch (SftpException e) {
            //目录不存在，则创建文件夹
            String[] dirs = remotePath.split("/");
            String tempPath = "";
            for (String dir : dirs) {
                if (null == dir || "".equals(dir)) {
                    continue;
                }
                tempPath += "/" + dir;
                try {
                    sftp.cd(tempPath);
                } catch (SftpException ex) {
                    try {
                        sftp.mkdir(tempPath);
                        logger.info("创建文件夹成功:{}", tempPath);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        logger.info("创建文件夹失败:{}", tempPath);
                        return false;
                    }
                    sftp.cd(tempPath);
                }
            }
            logger.info("create dir {} success", remotePath);
        }
        sftp.put(input, sftpFileName, mode); //上传文件
        logger.info("upload file {} success", sftpFileName);
        return true;
    }

    /**
     * 将输入流的数据上传到sftp作为文件。文件完整路径=basePath+directory
     *
     * @param basePath     服务器的基础路径
     * @param directory    上传到该目录
     * @param sftpFileName sftp端文件名
     * @param input        输入流
     */
    public void upload(String basePath, String directory, String sftpFileName, InputStream input)
                                                                                                 throws SftpException,
                                                                                                 IOException {
        try {
            sftp.cd(basePath);
            sftp.cd(directory);
            sftp.put(input, sftpFileName, mode); //上传文件
            logger.info("upload file {} success", sftpFileName);
        } catch (SftpException e) {
            //目录不存在，则创建文件夹
            String[] dirs = directory.split("/");
            String tempPath = basePath;
            for (String dir : dirs) {
                if (null == dir || "".equals(dir)) {
                    continue;
                }
                tempPath += "/" + dir;
                try {
                    sftp.cd(tempPath);
                } catch (SftpException ex) {
                    sftp.mkdir(tempPath);
                    sftp.cd(tempPath);
                }
            }
            logger.info("create dir {} success", directory);
        } finally {
            if (input != null) {
                input.close();
            }
        }
    }

    /**
     * 下载文件。
     *
     * @param directory    下载目录
     * @param downloadFile 下载的文件
     * @param saveFile     存在本地的路径
     */
    public void download(String directory, String downloadFile, String saveFile)
                                                                                throws SftpException,
                                                                                IOException {
        if (directory != null && !"".equals(directory)) {
            sftp.cd(directory);
        }
        FileOutputStream fos = null;
        try {
            File file = new File(saveFile);
            fos = new FileOutputStream(file);
            sftp.get(downloadFile, fos);
        } catch (Exception e) {
            logger.error("系统异常", e);
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }

    /**
     * 下载文件
     *
     * @param directory    下载目录
     * @param downloadFile 下载的文件名
     * @return 字节数组
     */
    public byte[] download(String directory, String downloadFile) throws SftpException, IOException {
        if (directory != null && !"".equals(directory)) {
            sftp.cd(directory);
        }
        InputStream is = sftp.get(downloadFile);

        byte[] fileData = IOUtils.toByteArray(is);

        return fileData;
    }

    /**
     * 删除文件
     *
     * @param directory  要删除文件所在目录
     * @param deleteFile 要删除的文件
     */
    public void delete(String directory, String deleteFile) throws SftpException {
        sftp.cd(directory);
        sftp.rm(deleteFile);
    }

    /**
     * 列出目录下的文件
     *
     * @param directory 要列出的目录
     */
    public Vector<?> listFiles(String directory) throws SftpException {
        return sftp.ls(directory);
    }

    public SftpItem getSftpItem() {
        return sftpItem;
    }

    public void setSftpItem(SftpItem sftpItem) {
        this.sftpItem = sftpItem;
    }
}
