package org.augusutus.commendline;

import org.augusutus.utils.CommandLineUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: linyongjin
 * @create: 2021-10-02 14:33:39
 */
public class MacMergeCommendLineHandler implements MergeCommendLineHandler {

    static Logger log = LoggerFactory.getLogger(CommandLineUtils.class);

    /**
     * 执行命令行语句
     *
     * @param index    分片文件列表
     * @param filename 合并之后的文件名
     */
    @Override
    public void handle(String index, String filename) throws Exception {
        log.info("开始合并文件");
        String[] cmd = {"sh", "merge.sh", index, filename.trim()};
        Process exec = Runtime.getRuntime().exec(cmd);
        exec.waitFor();
        exec.destroy();
        log.info("合并完成");
    }

    /**
     * 获取操作系统的名称
     *
     * @return
     */
    @Override
    public String getSystemName() {
        return OperateSystem.MAC.name();
    }
}
