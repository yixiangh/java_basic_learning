package com.example.file;

import com.jcraft.jsch.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 使用File对象读取远程服务器文件
 * @Author: HYX
 * @Date: 2020/8/29 18:25
 */
public class ReadFile {

    public static void readRemoteFile() throws IOException {
        File file = new File("E:\\idea_workspace\\20200831.txt");
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        String msg = null;
        br.readLine();//跳过第一行
        while ((msg = br.readLine()) != null)
        {
            String[] split = msg.split("\\|");
            Map<String,Object> map = new HashMap<>();
            map.put("acct_id",split[0]);
            map.put("etc_no",split[1]);
            map.put("into_station_time",split[2]);
            map.put("pay_amount",split[3]);
            String[] split1 = split[4].split("\\,");
            map.put("into_station",split1[0]);
            map.put("exit_station",split1[1]);
            System.out.println(map.toString());
        }


    }

    public static ChannelSftp connect() {
        ChannelSftp sftp = null;
        try {
            JSch jsch = new JSch();
            Session sshSession = jsch.getSession("demo1", "192.168.10.129", 22);
            sshSession.setPassword("demo1");
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            System.out.println("登录成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sftp;
    }

    public static void main(String[] args) throws IOException {
//        readRemoteFile();
        ChannelSftp sftp = connect();
        OutputStream output = null;
        try {
            File file = new File("E:\\testtest4444.txt");
            output = new FileOutputStream(file);
            sftp.cd("upload/");
            sftp.get("20200905.txt", output);
            sftp.disconnect();
            sftp.getSession().disconnect();
        } catch (SftpException | JSchException e) {
            e.printStackTrace();
        }

    }
}
