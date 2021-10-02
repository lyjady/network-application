package org.augusutus.app;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.augusutus.utils.CommandLineUtils;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author: linyongjin
 * @create: 2021-05-17 14:06:48
 */
public class NineOneDownload {

    static Logger log = LoggerFactory.getLogger(NineOneDownload.class);

    static String fileName = "";

    static String urlPrefix = "https://cdn.91p07.com//m3u8/";

    static String location = "/Users/linyongjin/video/";

    public static void main(String[] args) throws IOException, InterruptedException {
        while (true) {
            String index = requestIndexUrl(inputIndexUrl());
            String header = downloadReplication(index.split(","));
            CommandLineUtils.mergeReplication(header, fileName);
        }
    }

    /**
     * 输入获取索引的URL地址
     *
     * @return 索引列表URL
     */
    public static String inputIndexUrl() {
        Scanner scanner = new Scanner(System.in);
        log.info("请输入索引URL");
        return scanner.nextLine();
    }

    /**
     * 获取索引列表
     *
     * @return 索引列表
     */
    public static String requestIndexUrl(String url) throws IOException {
        log.info("请输入文件名");
        Scanner scanner = new Scanner(System.in);
        fileName = scanner.nextLine();
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(url);

        log.info("开始请求索引列表");
        long startTime = System.currentTimeMillis();
        CloseableHttpResponse response = client.execute(get);
        StatusLine statusLine = response.getStatusLine();
        log.info("请求结束, Http状态码为: {}, 共用时: {}", statusLine.getStatusCode(), System.currentTimeMillis() - startTime);

        InputStream content = response.getEntity().getContent();
        BufferedReader bis = new BufferedReader(new InputStreamReader(content));
        StringBuilder index = new StringBuilder();
        String line;
        while ((line = bis.readLine()) != null) {
            if (line.startsWith("#")) {
                continue;
            }
            index.append(line).append(",");
        }
        index.substring(0, index.length() - 1);
        return index.toString();
    }

    /**
     * 下载
     */
    public static String downloadReplication(String[] index) throws IOException {
        String header = index[0].substring(0, index[0].indexOf(".ts") - 1);
        String target = urlPrefix + "/" + header + "/";
        CloseableHttpClient client = HttpClientBuilder.create().build();
        log.info("开始下载视频片段");
        int count = 1;
        StringBuilder concatContent = new StringBuilder();
        StringBuilder replications = new StringBuilder();
        for (String replication : index) {
            concatContent.append("file '").append(location).append(replication).append("'").append("\n");
            replications.append(replication).append("\n");
            HttpGet get = new HttpGet(target + replication);
            log.info("开始下载第{}部分", count++);
            long startTime = System.currentTimeMillis();
            CloseableHttpResponse response = client.execute(get);
            log.info("请求结束, Http状态码为: {}, 共用时: {}", response.getStatusLine().getStatusCode(), System.currentTimeMillis() - startTime);
            InputStream content = response.getEntity().getContent();
            FileUtils.writeByteArrayToFile(new File(location + replication), IOUtils.toByteArray(content));
        }
        FileUtils.writeByteArrayToFile(new File(location + header + ".txt"), concatContent.toString().getBytes(StandardCharsets.UTF_8));
        FileUtils.writeByteArrayToFile(new File(location + "replications.txt"), replications.toString().getBytes(StandardCharsets.UTF_8));
        return header;
    }

}
