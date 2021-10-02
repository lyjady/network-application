package org.augusutus.factory;

import org.augusutus.commendline.MacMergeCommendLineHandler;
import org.augusutus.commendline.MergeCommendLineHandler;
import org.augusutus.commendline.WindowsMergeCommendLineHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: linyongjin
 * @create: 2021-10-02 14:29:21
 */
public class CommendLineFactory {

    private final static List<MergeCommendLineHandler> mergeCommendLineHandlers = new ArrayList<>();

    static {
        mergeCommendLineHandlers.add(new MacMergeCommendLineHandler());
        mergeCommendLineHandlers.add(new WindowsMergeCommendLineHandler());
    }

    public static MergeCommendLineHandler getHandler() {
        String osName = System.getProperty("os.name");
        for (MergeCommendLineHandler mergeCommendLineHandler : mergeCommendLineHandlers) {
            if (osName.toUpperCase().contains(mergeCommendLineHandler.getSystemName())) {
                return mergeCommendLineHandler;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        getHandler();
    }
}
