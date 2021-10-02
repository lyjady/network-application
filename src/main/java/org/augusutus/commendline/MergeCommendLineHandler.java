package org.augusutus.commendline;

/**
 * @author: linyongjin
 * @create: 2021-10-02 14:29:44
 */
public interface MergeCommendLineHandler {

    /**
     * 执行命令行语句
     *
     * @param index    分片文件列表
     * @param filename 合并之后的文件名
     */
    void handle(String index, String filename) throws Exception;

    /**
     * 获取操作系统的名称
     *
     * @return
     */
    String getSystemName();
}
