package com.cch.cz;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

public class Test {
    public static void main(String[] args) {
        File file1 = new File("D:\\tool\\apache-tomcat-8.0.50-windows-x64\\apache-tomcat-8.0.50\\webapps\\archcase\\佰仟-360+sdafsdf太原.xlsx");
        try {
            String md5=DigestUtils.md5Hex(IOUtils.toByteArray(new FileInputStream(file1)));
            System.out.println(md5);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

}
