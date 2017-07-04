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

package org.openo.nfvo.jujuvnfmadapter.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.compress.compressors.xz.XZCompressorInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <br/>
 * <p>
 * </p>
 * 
 * @author quanzhong@huawei.com
 * @version NFVO 0.5 Oct 28, 2016
 */
public class UnCompressUtil {

    private static Logger log = LoggerFactory.getLogger(UnCompressUtil.class);

    public static Map<String, String> archiveMap = new HashMap<String, String>();

    public static Map<String, String> compressorMap = new HashMap<String, String>();

    static {
        // archive type
        archiveMap.put(ArchiveStreamFactory.AR, ArchiveStreamFactory.AR);
        archiveMap.put(ArchiveStreamFactory.ARJ, ArchiveStreamFactory.ARJ);
        archiveMap.put(ArchiveStreamFactory.CPIO, ArchiveStreamFactory.CPIO);
        archiveMap.put(ArchiveStreamFactory.DUMP, ArchiveStreamFactory.DUMP);
        archiveMap.put(ArchiveStreamFactory.JAR, ArchiveStreamFactory.JAR);
        archiveMap.put(ArchiveStreamFactory.SEVEN_Z, ArchiveStreamFactory.SEVEN_Z);
        archiveMap.put(ArchiveStreamFactory.TAR, ArchiveStreamFactory.TAR);
        archiveMap.put(ArchiveStreamFactory.ZIP, ArchiveStreamFactory.ZIP);

        // compressor type
        compressorMap.put(CompressorStreamFactory.BZIP2, CompressorStreamFactory.BZIP2);
        compressorMap.put(CompressorStreamFactory.DEFLATE, CompressorStreamFactory.DEFLATE);
        compressorMap.put(CompressorStreamFactory.GZIP, CompressorStreamFactory.GZIP);
        compressorMap.put(CompressorStreamFactory.LZMA, CompressorStreamFactory.LZMA);
        compressorMap.put(CompressorStreamFactory.PACK200, CompressorStreamFactory.PACK200);
        compressorMap.put(CompressorStreamFactory.SNAPPY_FRAMED, CompressorStreamFactory.SNAPPY_FRAMED);
        compressorMap.put(CompressorStreamFactory.SNAPPY_RAW, CompressorStreamFactory.SNAPPY_RAW);
        compressorMap.put(CompressorStreamFactory.XZ, CompressorStreamFactory.XZ);
        compressorMap.put(CompressorStreamFactory.Z, CompressorStreamFactory.Z);
    }

    /**
     * tar.gz
     * <br/>
     * 
     * @param zipfileName
     * @param outputDirectory
     * @param fileNames
     * @return
     * @since NFVO 0.5
     */
    public static boolean unCompressGzip(String zipfileName, String outputDirectory, List<String> fileNames) {
        FileInputStream fis = null;
        ArchiveInputStream in = null;
        BufferedInputStream bis = null;
        try {
            fis = new FileInputStream(zipfileName);
            GZIPInputStream gis = new GZIPInputStream(new BufferedInputStream(fis));
            in = new ArchiveStreamFactory().createArchiveInputStream("tar", gis);
            bis = new BufferedInputStream(in);
            TarArchiveEntry entry = (TarArchiveEntry)in.getNextEntry();
            while(entry != null) {
                String name = entry.getName();
                String[] names = name.split("/");
                String fileName = outputDirectory;
                for(int i = 0; i < names.length; i++) {
                    String str = names[i];
                    fileName = fileName + File.separator + str;
                }
                if(name.endsWith("/")) {
                    FileUtils.mkDirs(fileName);
                } else {
                    File file = getRealFileName(outputDirectory, name);
                    if(null != fileNames) {
                        fileNames.add(file.getName());
                    }
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                    int b = -1;
                    while((b = bis.read()) != -1) {
                        bos.write(b);
                    }
                    log.debug("ungzip to:" + file.getCanonicalPath());
                    bos.flush();
                    bos.close();
                }
                entry = (TarArchiveEntry)in.getNextEntry();
            }
            return true;
        } catch(Exception e) {
            log.error("UnCompressGZip faield:", e);
            return false;
        } finally {
            try {
                if(null != bis) {
                    bis.close();
                }
            } catch(IOException e) {
                log.error("UnCompressGZip faield:", e);
            }
        }
    }

    public static void unCompressAllZip(String sourcePath, String targetPath) throws FileNotFoundException {
        for(File file : FileUtils.listFiles(new File(sourcePath))) {
            unCompressZip(file.getAbsolutePath(),
                    targetPath + File.separator + file.getName().substring(0, file.getName().lastIndexOf(".")),new ArrayList<String>());
        }
    }

    /**
     * zip
     * <br/>
     * 
     * @param sourceFile unzipPath
     * @param targetPath zippath
     * @param fileNames
     * @since NFVO 0.5
     */
    public static boolean unCompressZip(String sourceFile, String targetPath,List<String> fileNames) {
        try {
            BufferedOutputStream dest = null;
            FileInputStream fis = new FileInputStream(sourceFile);
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
            ZipEntry entry = null;
            while((entry = zis.getNextEntry()) != null) {
                String name = entry.getName();
                String[] names = name.split("/");
                String fileName = targetPath;
                for(int i = 0; i < names.length; i++) {
                    String str = names[i];
                    fileName = fileName + File.separator + str;
                }
                if(name.endsWith("/")) {
                    FileUtils.mkDirs(fileName);
                } else {
                    int count;
                    byte data[] = new byte[2048];
                    File file = getRealFileName(targetPath, name);
                    fileNames.add(file.getName());
                    dest = new BufferedOutputStream(new FileOutputStream(file));

                    while((count = zis.read(data, 0, 2048)) != -1) {
                        dest.write(data, 0, count);
                    }
                    log.debug("unzip to:" + file.getCanonicalPath());
                    dest.flush();
                    dest.close();
                }
            }
            zis.close();
            return true;
        } catch(Exception e) {
            log.error("UnCompressZip faield:", e);
        }
        return false;
    }

    private static File getRealFileName(String zippath, String absFileName) {
        String[] dirs = absFileName.split("/", absFileName.length());

        File ret = new File(zippath);

        if(dirs.length > 1) {
            for(int i = 0; i < dirs.length - 1; i++) {
                ret = new File(ret, dirs[i]);
            }
        }

        if(!ret.exists()) {
            ret.mkdirs();
        }

        ret = new File(ret, dirs[dirs.length - 1]);

        return ret;
    }

    /**
     * tar.xz
     * <br/>
     * 
     * @param zipfileName
     * @param outputDirectory
     * @param fileNames
     * @return
     * @since NFVO 0.5
     */
    public static boolean unCompressTarXZ(String zipfileName, String outputDirectory, List<String> fileNames) {
        ArchiveInputStream in = null;
        BufferedInputStream bis = null;
        try {
            XZCompressorInputStream xzis =
                    new XZCompressorInputStream(new BufferedInputStream(new FileInputStream(zipfileName)));
            in = new ArchiveStreamFactory().createArchiveInputStream("tar", xzis);
            bis = new BufferedInputStream(in);
            TarArchiveEntry entry = (TarArchiveEntry)in.getNextEntry();
            while(entry != null) {
                String name = entry.getName();
                String[] names = name.split("/");
                String fileName = outputDirectory;
                for(int i = 0; i < names.length; i++) {
                    String str = names[i];
                    fileName = fileName + File.separator + str;
                }
                if(name.endsWith("/")) {
                    FileUtils.mkDirs(fileName);
                } else {
                    File file = getRealFileName(outputDirectory, name);
                    if(null != fileNames) {
                        fileNames.add(file.getName());
                    }
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                    int b = -1;
                    while((b = bis.read()) != -1) {
                        bos.write(b);
                    }
                    log.debug("ungzip to:" + file.getCanonicalPath());
                    bos.flush();
                    bos.close();
                }
                entry = (TarArchiveEntry)in.getNextEntry();
            }
            return true;
        } catch(Exception e) {
            log.error("unCompressTarXZ faield:", e);
        } finally {
            try {
                if(null != bis) {
                    bis.close();
                }
            } catch(IOException e) {
                log.error("unCompressTarXZ faield:", e);
            }
        }
        return false;
    }
    
    /**
     * only support .zip/.tar.gz/.tar.xz
     * <br/>
     * 
     * @param zipfileName
     * @param outputDirectory
     * @param fileNames
     * @return
     * @since  NFVO 0.5
     */
    public static boolean unCompress(String zipfileName, String outputDirectory, List<String> fileNames) {
        if(zipfileName.endsWith(".zip")){
            return unCompressZip(zipfileName,outputDirectory,fileNames);
        }else if(zipfileName.endsWith(".tar.gz")){
            return unCompressGzip(zipfileName,outputDirectory,fileNames);
        }else if(zipfileName.endsWith(".tar.xz")){
            return unCompressTarXZ(zipfileName,outputDirectory,fileNames);
        }else{
            log.error("not supprot file type:->"+zipfileName);
            return false;
        }
    }

        
}
