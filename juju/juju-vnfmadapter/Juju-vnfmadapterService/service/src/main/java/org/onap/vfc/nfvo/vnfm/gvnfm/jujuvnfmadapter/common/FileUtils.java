/*
 * Copyright (c) 2016, Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FileUtils
 * <br/>
 * <p>
 * </p>
 * 
 * @author quanzhong@huawei.com
 * @version NFVO 0.5 Sep 23, 2016
 */
public class FileUtils {

    public static final String UTF8 = "UTF-8";

    public static final String GBK = "GBK";

    private static Logger log = LoggerFactory.getLogger(FileUtils.class);

    private FileUtils(){
        
    }
    /**
     * get current classPath as str. and the trailing slash will be remove
     * <br/>
     * 
     * @return
     * @since  NFVO 0.5
     */
    public static String getClassPath(){
        String path = ClassLoader.getSystemClassLoader().getResource("./").getPath();
        if(path.endsWith("/")){
            path = path.substring(0, path.length()-1);
        }
        return path;
    }
    /**
     * read data from the file
     * <br/>
     * 
     * @param file
     * @param charsetName
     * @return
     * @throws IOException
     * @since  NFVO 0.5
     */
    public static byte[] readFile(File file, String charsetName) throws IOException {
        if(file != null) {
            FileReader reader = new FileReader(file);
            StringBuilder buffer = new StringBuilder();
            char[] cbuf = new char[1024];
            int legth;
            while((legth = reader.read(cbuf)) != -1) {
                buffer.append(new String(cbuf, 0, legth));
            }
            reader.close();
            return buffer.toString().getBytes(charsetName);
        }
        return new byte[]{};
    }

    /**
     * write data as file to the filePath
     * <br/>
     * 
     * @param data
     * @param filePath
     * @return
     * @since  NFVO 0.5
     */
    public static int writeFile(byte[] data, String filePath) {
        try {
            OutputStream out = new FileOutputStream(filePath);
            out.write(data, 0, data.length);
            out.close();
            return 0;
        } catch (Exception e) {
            log.error("write file fail,filePath:"+filePath, e);
        }
        return -1;
    }
    
    /**
     * List all files in directory
     * @param file
     * @return
     * @throws FileNotFoundException 
     */
    public static  List<File> listFiles(File file) throws FileNotFoundException{
        List<File> list = new ArrayList<>();
        list(file, list);
        return list;
    }
    /**
     * List all files in directory
     * @param file
     * @param list
     * @throws FileNotFoundException 
     */
    private static void list(File file, List<File> list) throws FileNotFoundException{
        if(!file.exists()){
            throw new FileNotFoundException(file.getAbsolutePath());
        }
        File[] files = file.listFiles();
        if(files != null && files.length >  0 ){
            for(File f : files){
                if(f.isFile()){
                    list.add(f);
                }else{
                    list(f, list);
                }
            }
        }
    }
    
    
    /**
     * createDirs (cycle create)
     * <br/>
     * 
     * @param file
     * @since  NFVO 0.5
     */
    public static void createDirs(File file){
        if(file.getParentFile().exists()){
            file.mkdir();
        }else{
            createDirs(file.getParentFile());
            file.mkdir();
        }
    }
    
    /**
     *mkDirs 
     * <br/>
     * 
     * @param path
     * @since  NFVO 0.5
     */
    public static void mkDirs(String path){
        File file = new File(path);
        createDirs(file);
    }
    
    
    
    public static String getSuperUrl(String file) {
        return new File(file).getParentFile().getAbsolutePath();

    }

    /**
     * absolute url
     * 
     * @author sunny.sun
     * @return file this application absolute url
     */
    public static String getAppAbsoluteUrl() {

        // ��ȡ��class���ڵľ���·��
        String file = UnCompressUtil.class.getClassLoader().getResource("/") == null ? null
                : UnCompressUtil.class.getClassLoader().getResource("/").toString();
        if (file == null) {
            file = UnCompressUtil.class.getProtectionDomain().getCodeSource()
                    .getLocation().getFile().substring(1);
        }
        // ��class�ļ���war��ʱ������"zip:D:/ ..."����·��
        if (file.startsWith("zip")) {
            file = file.substring(4);

            // ��class�ļ���class�ļ���ʱ������"file:/F:/ ..."����·��
        } else if (file.startsWith("file")) {
            file = file.substring(6);

            // ��class�ļ���jar�ļ���ʱ������"jar:file:/F:/ ..."����·��
        } else if (file.startsWith("jar")) {
            file = file.substring(10);
        }

        if (!isWindows())
            return ("/" + file).replace("%20", " ");

        return file.replace("%20", " ");
    }

    public static String getWEBClassAbsoluteUrl() {

        return getAppAbsoluteUrl();
    }

    /**
     * @param floder
     * @return
     */
    public static File newFloder(String floder) {
        File file = new File(floder);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }



    public static void copyFile(String oldPath, String newPath, boolean flag)
            throws Exception {
        int byteread = 0;
        File oldfile = new File(oldPath);
        if (oldfile.exists()) { // �ļ�����ʱ
            if (flag == false) {
                delFiles(newPath);
            }
            if (new File(newPath).exists() && flag == true) {
                return;
            }
            newFile(newPath);
            FileInputStream fis = new FileInputStream(oldPath); // ����ԭ�ļ�
            FileOutputStream fos = new FileOutputStream(newPath);
            byte[] buffer = new byte[1024];
            while ((byteread = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, byteread);
            }
            fos.close();
            fis.close();
        } else {
            throw new FileNotFoundException("the " + oldfile + " is not exits ");
        }

    }

    /**
     * @param filePathAndName
     *            String exp c:/fqf.txt
     * @param fileContent
     *            String
     * @return boolean
     */
    public static boolean delFiles(String filePathAndName) {

        boolean flag = false;
        File myDelFile = new File(filePathAndName);
        if (!myDelFile.exists())
            return true;
        if (myDelFile.isDirectory()) {
            File[] fs = myDelFile.listFiles();
            for (int i = 0; i < fs.length; i++) {
                if (fs[i].isFile())
                    flag = fs[i].delete();
                if (fs[i].isDirectory()) {
                    flag = delFiles(fs[i].getAbsolutePath());
                    flag = fs[i].delete();
                }
            }
        }
        flag = myDelFile.delete();
        return flag;
    }

    /**
     * create new file
     * 
     * @param filePathAndName
     *            String
     * @return
     * @throws IOException
     */
    public static File newFile(String fileName) throws IOException {
        File file = new File(fileName);

        newFloder(file.getParentFile().toString());

        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    /**
     * the file down all the hidden files
     * 
     * @author sunny.sun
     * */
    public static List<File> getFiles(String path) {

        List<File> list = new ArrayList<File>();
        File file = new File(path);
        if (!file.exists()) {

            file.mkdirs();
        }

        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile() && !files[i].isHidden()) {
                list.add(files[i]);
            }
            if (files[i].isDirectory() && !files[i].isHidden()) {
                List<File> list2 = getFiles(files[i].getPath());// use myself
                list.addAll(list2);
            }
        }
        return list;
    }

    /**
     * causes the os of this computer is Windows
     * 
     * @author sunny.sun
     * */
    public static boolean isWindows() {

        String os = System.getProperty("os.name").toLowerCase();
        // windows
        return (os.indexOf("win") >= 0);

    }

    public static void copy(String oldfile, String newfile, boolean flag)
            throws Exception {
        File oldf = new File(oldfile);
        File newf = new File(newfile);
        boolean oisd = (oldfile.endsWith("/") || oldfile.endsWith("\\"));
        boolean nisd = (newfile.endsWith("/") || newfile.endsWith("\\"));

        // Դ�ļ�������
        if (!oldf.exists()) {
            throw new Exception("the  from data is not exists ");
        }
        // ����ļ����У�������
        if (oldf.exists() && !newf.exists()) {
            if (newfile.endsWith("/") || newfile.endsWith("\\")) {
                newFloder(newfile);
            } else {
                newFile(newfile);
            }
        }
        // Ŀ�����ļ�����Դ���ļ���
        if (oldf.exists() && oisd && !nisd) {
            throw new Exception(
                    "the  from data is directory,but the to data is a file");
        }
        // Դ�Ǹ��ļ�,Ŀ�����ļ�
        if (!oisd && !nisd) {
            copyFile(oldf.getAbsolutePath(), newf.getAbsolutePath(), flag);
        }
        // Դ�Ǹ��ļ��У�Ŀ�����ļ���
        if (oisd && nisd) {
            newFloder(newf.getAbsolutePath());
            List<File> list = getFiles(oldf.getAbsolutePath());
            for (int i = 0; i < list.size(); i++) {
                copyFile(list.get(i).getAbsolutePath(), newf.getAbsolutePath()
                        + "/" + list.get(i).getName(), flag);
            }
        }
        // Դ�Ǹ��ļ���Ŀ���Ǹ��ļ���
        if (!oisd && nisd) {
            newFloder(newf.getAbsolutePath());
            copyFile(oldf.getAbsolutePath(), newf.getAbsolutePath() + "/"
                    + oldf.getName(), flag);
        }

    }

    /**
     * causes the os of this computer is mac
     * 
     * @author sunny.sun
     * */
    public static boolean isMac() {

        String os = System.getProperty("os.name").toLowerCase();
        // Mac
        return (os.indexOf("mac") >= 0);

    }

    /**
     * causes the os of this computer is unix
     * 
     * @author sunny.sun
     * */
    public static boolean isUnix() {

        String os = System.getProperty("os.name").toLowerCase();
        // linux or unix
        return (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0);

    }

    /**
     * @param file
     * @return
     * @throws Exception
     */
    public static boolean isUsed(String file) throws Exception {
        File f = new File(file);
        if (!f.exists()) {
            throw new Exception("the file is not exists ..");
        }
        File f1 = new File(file + ".temp");
        f.renameTo(f1);
        if (f.exists()) {
            return true;
        } else {
            f1.renameTo(f);
            return false;
        }

    }
    /**
     * Remove the file name's extension (only remove the last) 
     * <br/>
     * 
     * @param file
     * @return
     * @since  NFVO 0.5
     */
    public static String getBaseFileName(File file){
        if(file.getName().lastIndexOf(".") > 0){
            return file.getName().substring(0,file.getName().lastIndexOf("."));
        }else{
            return file.getName();
        }
       
    }
    
    /**
     * fix file path to linux seperate,and remove the head and end slash
     * <br/>
     * 
     * @param path
     * @return
     * @since  NFVO 0.5
     */
    public static String fixPath(String path){
        String newPath = path;
        if(path == null){
            return newPath;
        }
        newPath = newPath.replaceAll("\\\\", "/");
        if(newPath.startsWith("/")){
            newPath = newPath.substring(1, newPath.length()); 
        }
        if(newPath.endsWith("/")){
            newPath = newPath.substring(0, newPath.length()-1); 
        }
        return newPath;
    }
    
    /**
     * fix file path to linux seperate,and add the head and end slash
     * <br/>
     * 
     * @param path
     * @return
     * @since  NFVO 0.5
     */
    public static String getFriendlyPath(String path){
        String newPath = path;
        if(path == null){
            return newPath;
        }
        newPath = newPath.replaceAll("\\\\", "/");
        if(!newPath.startsWith("/")){
            newPath = "/"+newPath;
        }
        if(!newPath.endsWith("/")){
            newPath = newPath + "/";
        }
        return newPath;
    }
}
