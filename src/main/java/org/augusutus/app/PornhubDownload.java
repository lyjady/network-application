package org.augusutus.app;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.augusutus.utils.CommandLineUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author: linyongjin
 * @create: 2021-10-01 14:52:13
 */
public class PornhubDownload {

    static Logger log = LoggerFactory.getLogger(PornhubDownload.class);

    private static String RESOURCE_PREFIX = null;

    private static String RESOURCE_SUFFIX = null;

    private final static String DIR = "/Users/linyongjin/video/";

    private static String filename;

    public static void main(String[] args) throws IOException, InterruptedException {
        initResource();
        String index = downloadVideo();
        CommandLineUtils.mergeReplication(index, filename);
    }

    private static void initResource() {
        log.info("请输入地址");
        Scanner scanner = new Scanner(System.in);
        String resourceAddress = scanner.nextLine();
        log.info("请文件名");
        filename = scanner.nextLine();
        RESOURCE_PREFIX = resourceAddress.substring(0, resourceAddress.indexOf("seg") + 4);
        RESOURCE_SUFFIX = resourceAddress.substring(resourceAddress.indexOf(".ts") - 9);
    }

    public static String downloadVideo() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;
        int part = 1;
        StringBuilder concatContent = new StringBuilder();
        StringBuilder replications = new StringBuilder();
        try {
            log.info("开始请求{}资源", filename);
            long startTime = System.currentTimeMillis();
            while (true) {
                String resource = RESOURCE_PREFIX + part + RESOURCE_SUFFIX;
                HttpGet get = new HttpGet(resource);
                log.info("开始请求第{}部分资源", part);
                long beginTime = System.currentTimeMillis();
                response = httpClient.execute(get);
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200 || statusCode == 474) {
                    log.info("请求第{}部分资源成功一共花费{}ms, 开始写入磁盘", part, System.currentTimeMillis() - beginTime);
                    InputStream content = response.getEntity().getContent();
                    String replicationName = filename + "-" + part;
                    File file = new File(DIR + replicationName + ".mp4");
                    replications.append(file.getName()).append(System.lineSeparator());
                    concatContent.append("file '").append(DIR).append(file.getName()).append("'").append("\n");
                    FileUtils.writeByteArrayToFile(file, IOUtils.toByteArray(content));
                    log.info("第{}部分写入磁盘成功", part++);
                } else if (statusCode == 404) {
                    log.error("请求第{}部分资源时发生404, 资源请求结束", part);
                    break;
                } else if (statusCode == 500) {
                    log.error("请求第{}部分资源时发生500, 资源请求结束", part);
                    break;
                } else {
                    log.info("响应码为{}", statusCode);
                    break;
                }
            }
            log.info("资源{}请求结束一共花费{}s", filename, ((System.currentTimeMillis() - startTime) * 1.0) / 1000);
            String index = "index";
            FileUtils.writeByteArrayToFile(new File(DIR + index + ".txt"), concatContent.toString().getBytes(StandardCharsets.UTF_8));
            FileUtils.writeByteArrayToFile(new File(DIR + "replications.txt"), replications.toString().getBytes(StandardCharsets.UTF_8));
            return index;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
