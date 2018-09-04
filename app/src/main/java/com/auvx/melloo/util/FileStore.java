package com.auvx.melloo.util;

import java.io.InputStream;

/**
 * 因为上传或者下载文件也不一定就一定是用Http请求做，因此
 * 就觉得弄一个文件的工具类会舒服些吧
 */
public class FileStore {

    private static final String MELLOO_LOCAL_FILE_PATH_BASE = "";

    private enum ModuleFolder {
        ACCOUNT("account"), CHAT("chat"), MOMENT("moment");

        public String value;

        ModuleFolder(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
    //-------------------------------------远程-----------------------------------------------
    //用fetch吧，总感觉download的话，就是下载到本地文件系统里了，而fetch没有那么强的语义
    public static InputStream fetchFile() {

        return null;
    }

    public static void downloadFile(String path, InputStream is){

        return;
    }
    public static void uploadFile(String url, InputStream is) {

        return;
    }

    //-------------------------------------本地------------------------------------------------
    public static void copyFile() {

    }

    public static void moveFile() {

    }

    public static void deleteFile() {

    }
}
