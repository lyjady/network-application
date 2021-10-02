package org.augusutus.handler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * @author: linyongjin
 * @create: 2021-08-02 16:07:30
 */
public class HandleColumn {

    public static void main(String[] args) throws Exception {
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = new BufferedReader(new FileReader("/Users/linyongjin/IdeaProjects/network-application/src/main/java/org/augusutus/handler/column.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] split = line.split("\\s+");
            buffer.append("ALTER TABLE AST_USE_DTL_TMP MODIFY ").append(split[0]).append(" ").append(split[1]).append(" NULL DEFAULT NULL;").append("\n");
        }
        System.out.println(buffer);
    }
}
