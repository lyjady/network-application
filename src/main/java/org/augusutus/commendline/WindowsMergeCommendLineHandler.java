package org.augusutus.commendline;

/**
 * @author: linyongjin
 * @create: 2021-10-02 14:34:58
 */
public class WindowsMergeCommendLineHandler implements MergeCommendLineHandler {

    @Override
    public void handle(String index, String filename) throws Exception {

    }

    @Override
    public String getSystemName() {
        return OperateSystem.WINDOWS.name();
    }
}
