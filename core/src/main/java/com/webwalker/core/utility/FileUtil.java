package com.webwalker.core.utility;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Created by xujian on 2018/7/5.
 */
public class FileUtil {
    /**
     * 创建文件夹
     */
    public static boolean createDirs(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists() || !file.isDirectory()) {
            return file.mkdirs();
        }
        return true;
    }

    /**
     * 复制文件，可以选择是否删除源文件
     */
    public static boolean copyFile(String srcPath, String destPath,
                                   boolean deleteSrc) {
        File srcFile = new File(srcPath);
        File destFile = new File(destPath);
        return copyFile(srcFile, destFile, deleteSrc);
    }

    /**
     * 复制文件，可以选择是否删除源文件
     */
    public static boolean copyFile(File srcFile, File destFile,
                                   boolean deleteSrc) {
        if (!srcFile.exists() || !srcFile.isFile()) {
            return false;
        }
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];
            int i = -1;
            while ((i = in.read(buffer)) > 0) {
                out.write(buffer, 0, i);
                out.flush();
            }
            if (deleteSrc) {
                srcFile.delete();
            }
        } catch (Exception e) {
            Logger.dn(e.getMessage());
            return false;
        } finally {
            FileUtil.close(out);
            FileUtil.close(in);
        }
        return true;
    }

    /**
     * 判断文件夹是否为空
     */
    public static boolean isEmptyFolder(String path) {
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            if (file.list().length > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 修改文件的权限,例如"777"等
     */
    public static void chmod(String path, String mode) {
        try {
            String command = "chmod " + mode + " " + path;
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(command);
        } catch (Exception e) {
            Logger.dn(e.getMessage());
        }
    }

    /**
     * 把键值对写入文件
     *
     * @param filePath 文件路径
     * @param key      键
     * @param value    值
     * @param comment  该键值对的注释
     */
    public static void writeProperties(String filePath, String key,
                                       String value, String comment) {
        if (StringUtil.isEmpty(key) || StringUtil.isEmpty(filePath)) {
            return;
        }
        FileInputStream fis = null;
        FileOutputStream fos = null;
        File f = new File(filePath);
        try {
            if (!f.exists() || !f.isFile()) {
                f.createNewFile();
            }
            fis = new FileInputStream(f);
            Properties p = new Properties();
            p.load(fis);// 先读取文件，再把键值对追加到后面
            p.setProperty(key, value);
            fos = new FileOutputStream(f);
            p.store(fos, comment);
        } catch (Exception e) {
            Logger.dn(e.getMessage());
        } finally {
            FileUtil.close(fis);
            FileUtil.close(fos);
        }
    }

    /**
     * 根据值读取
     */
    public static String readProperties(String filePath, String key,
                                        String defaultValue) {
        if (StringUtil.isEmpty(key) || StringUtil.isEmpty(filePath)) {
            return null;
        }
        String value = null;
        FileInputStream fis = null;
        File f = new File(filePath);
        try {
            if (!f.exists() || !f.isFile()) {
                f.createNewFile();
            }
            fis = new FileInputStream(f);
            Properties p = new Properties();
            p.load(fis);
            value = p.getProperty(key, defaultValue);
        } catch (IOException e) {
            Logger.dn(e.getMessage());
        } finally {
            FileUtil.close(fis);
        }
        return value;
    }

    public static Map<String, String> readAllProperties(String filePath) {
        Map<String, String> value = new HashMap<>();
        File f = new File(filePath);
        if (StringUtil.isEmpty(filePath) || !f.exists()) {
            Logger.en("配置文件不存在.");
            return value;
        }
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
            Properties p = new Properties();
            p.load(fis);
            Iterator<String> it = p.stringPropertyNames().iterator();
            while (it.hasNext()) {
                String key = it.next();
                value.put(key, p.getProperty(key));
            }
        } catch (IOException e) {
            Logger.dn(e.getMessage());
        } finally {
            FileUtil.close(fis);
        }
        return value;
    }

    /**
     * 改名
     */
    public static boolean copy(String src, String des, boolean delete) {
        File file = new File(src);
        if (!file.exists()) {
            return false;
        }
        File desFile = new File(des);
        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            in = new FileInputStream(file);
            out = new FileOutputStream(desFile);
            byte[] buffer = new byte[1024];
            int count = -1;
            while ((count = in.read(buffer)) != -1) {
                out.write(buffer, 0, count);
                out.flush();
            }
        } catch (Exception e) {
            return false;
        } finally {
            FileUtil.close(in);
            FileUtil.close(out);
        }
        if (delete) {
            file.delete();
        }
        return true;
    }

    /**
     * 关闭流
     */
    public static boolean close(Closeable io) {
        if (io != null) {
            try {
                io.close();
            } catch (IOException e) {
                Logger.dn(e.getMessage());
            }
        }
        return true;
    }

    public static String readFile(String srcFile) {
        String ret = "";
        try {
            File file = new File(srcFile);
            if (file.exists()) {
                ret = readByInputStream(new FileInputStream(file));
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static String readByInputStream(InputStream is) {
        StringBuffer sb = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            sb = new StringBuffer();
            String line = "";
            while (null != (line = br.readLine())) {
                sb.append(line);
            }
        } catch (IOException e) {
            sb = null;
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != isr) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (null != sb) {
            return sb.toString();
        }

        return null;
    }

    public static void writeFile(String str, String descFile, boolean append) {
        if ((null == str) || (null == descFile)) {
            return;
        }

        createDirIfNotExist(descFile);

        BufferedOutputStream out = null;

        try {
            byte[] src = str.getBytes("UTF-8");
            out = new BufferedOutputStream(new FileOutputStream(descFile,
                    append), 1024 * 64);
            out.write(src);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(out);
        }
    }

    public static boolean createDirIfNotExist(File file) {
        boolean ret = true;
        if (null == file) {
            ret = false;
            return ret;
        }
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                ret = parentFile.mkdirs();
                // chmod777(parentFile, null);
            }
        }
        return ret;
    }

    public static boolean createDirIfNotExist(String filePath) {
        File file = new File(filePath);
        return createDirIfNotExist(file);
    }

}