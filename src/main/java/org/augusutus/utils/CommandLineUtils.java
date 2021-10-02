package org.augusutus.utils;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author: linyongjin
 * @create: 2021-05-16 15:08:58
 * 测试Java代码执行操作系统的命令行代码
 */
public class CommandLineUtils {

    static Logger log = LoggerFactory.getLogger(CommandLineUtils.class);

    /**
     *
     * @param header
     * @throws IOException
     * @throws InterruptedException
     */
    public static void mergeReplication(String header, String filename) throws IOException, InterruptedException {
        log.info("开始合并文件");
        String[] cmd = {"sh", "merge.sh", header, filename.trim()};
        Process exec = Runtime.getRuntime().exec(cmd);
        exec.waitFor();
        exec.destroy();
        log.info("合并完成");
    }
}

