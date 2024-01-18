package com.sophon.schedule.api.hls;


import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/12/14 17:45
 */
public class HLSHandle {

    //生成iv文件的名称
    public static final String IV_NAME = "iv.txt";
    //keyinfo的文件名称
    public static final String ENC_KEY_INFO = "enc.keyinfo";
    //生成的加密文件名称
    public static final String ENC_KEY = "enc.key";
    //加密之后的m3u8上传oss目录
    private static final String uploadOssPath = "/";
    private static final String downloadTempPath = "/";
    private static final String generateIvPath = "";
    private static final String generateTsPath = "";

    /**
     * 将oss中的视频下载到本地，然后进行加密切分成m3u8文件
     * 生成key文件参考资料：https://www.cnblogs.com/codeAB/p/9184266.html
     *
     * @param bucketName 文件所处在的oss的bucket名称
     * @param path       文件所在的路径,例如：upload/
     * @param name       文件名称，例如：8KAnimal.mp4
     * @return 返回所有上传的文件路径集合
     */
    public static List<String> videoTom3u8AndEncrypt(String bucketName, String path, String name) {
        List<String> ossPathList = new ArrayList<>();

        //将oss文件下载到本地服务器,返回值路径+文件名称
        String storagePath = downloadOssResourceToLocal(bucketName, path, name, downloadTempPath);
        //截取文件暂存路径
        String storageFilePath = storagePath.substring(0, storagePath.indexOf(name));

        //生成16位enc.key
        String encKey = generateRandom(16);

        //创建enc.key并将上面生成的encKey写入到文件中
        createFileWriterContent(storageFilePath, ENC_KEY, encKey);
        //生成vi文件
        generateIVFile(storageFilePath, generateIvPath);
        //生成enc.keyinfo文件,enc.key的ng路径地址，ng本地地址，iv文件内容
        String content = ENC_KEY + "\n" +
                storageFilePath + File.separator + ENC_KEY + "\n" +
                readFileContent(storageFilePath + File.separator + IV_NAME);
        System.out.println("***keyinfo文件的内容为:{}***" + content);
        createFileWriterContent(storageFilePath, ENC_KEY_INFO,
                content);
        String videoType = getFileType(name);
        //使用ffmpeg指令调用服务器/data/shell目录下的脚本实现视频切分和加密
        dealVideoToM3u8AndEnc(videoType, storageFilePath + File.separator + ENC_KEY_INFO,
                storageFilePath + File.separator + name, generateTsPath);

        //由于ffmpeg视频切分是调用服务器脚本，代码执行与脚本执行是异步操作，
        // 所以需要等待一段时间待视频切分完成后将文件上传到oss中
        int duration = calculateVideoDuration(storageFilePath + File.separator + name);
        System.out.println("***视频时长为：【{}】秒***" + duration);

        //计算ts的数量，向上取整。20是每个ts文件的视频长度，每个是20秒
        int fragmentNum = (int) Math.ceil((float) duration / 20);
        System.out.println("***ts数量为：{}***" + fragmentNum);

        //等待视频切割完成，通过检测文件夹下总ts文件数量与计算的ts数量是否相同判断切割是否完成
        // （切割后端ts总大小与原视频大小不一致）
        waitVideoSegmentFinish(storageFilePath, fragmentNum);

        //获取文件路径下所有ts与m3u8、enc.key文件列表
        List<String> uploadPathList = getUploadPathList(storageFilePath);

        //获取文件名称
        String filePrefix = name.substring(0, name.indexOf("."));

        //将文件上传到oss上
        for (String uploadFile : uploadPathList) {
            File file = new File(uploadFile);
            String uploadFileName = file.getName();
            //创建oss上传的路径，加密后的文件传到security下，未加密的传到upload目录下
            //String fileName = "security/" + filePrefix + "/" + uploadFileName;
            try {
                String s = uploadFileWithInputSteam(bucketName, uploadOssPath + filePrefix, uploadFileName,
                        new FileInputStream(file), file.length());
                //上传视频文件为中文名时，oss会返回URL编码后的路径，所以要将返回回来的路径进行URL解码后再存入数据库
                s = URLDecoder.decode(s, "utf-8");
                System.out.println("***文件上传后的绝对路径为：【{}】***" + s);
                ossPathList.add(s);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        return ossPathList;
    }


    /**
     * 下载oos上资源到本地临时文件夹
     *
     * @param bucketName oss桶名
     * @param path       文件夹路径，例如：abc/efg/
     * @param name       文件名称，例如：123.jpg
     * @param tempPath   临时目录 /Users/XXX/tmp/
     * @return filename 下载后的文件全路径名
     */
    public static String downloadOssResourceToLocal(String bucketName, String path, String name, String tempPath) {
        String objectName = path + name;
        String fileName = name.substring(0, name.indexOf("."));

        tempPath = tempPath + fileName;
        File uploadFile = new File(tempPath);
        if (!uploadFile.exists()) {
            System.out.println("创建的文件夹目录:" + uploadFile.getPath());
            uploadFile.mkdirs();
        }
        //oss下载
        //minioUtils.downloadFile(objectName,tempPath + File.separator + name);
        return tempPath + name;
    }


    /**
     * 读取文件内容并返回
     *
     * @param fileName 文件完整路径
     * @return 文件内容
     */
    public static String readFileContent(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuilder sbf = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }

    /**
     * 生成一定长度的字母与数字随机数
     *
     * @param length 长度
     * @return 随机数
     */
    public static String generateRandom(int length) {
        StringBuilder val = new StringBuilder();
        Random random = new Random();
        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val.append((char) (random.nextInt(26) + temp));
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val.append(random.nextInt(10));
            }
        }
        return val.toString();
    }


    /**
     * 创建文件并写入内容
     *
     * @param filePath 文件路径
     * @param fileName 文件名称
     * @param content  需要写入的文件内容
     * @throws IOException
     */
    public static void createFileWriterContent(String filePath, String fileName, String content) {
        File dir = new File(filePath);
        // 一、检查放置文件的文件夹路径是否存在，不存在则创建
        if (!dir.exists()) {
            dir.mkdirs();// mkdirs创建多级目录
        }
        File checkFile = new File(filePath + File.separator + fileName);
        FileWriter writer = null;
        try {
            // 二、检查目标文件是否存在，不存在则创建
            if (!checkFile.exists()) {
                checkFile.createNewFile();// 创建目标文件
            }
            // 三、向目标文件中写入内容
            // FileWriter(File file, boolean append)，append为true时为追加模式，false或缺省则为覆盖模式
            writer = new FileWriter(checkFile);
            writer.append(content);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != writer) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 生成视频播放所需要的的iv文件
     * generate-iv.sh脚本指令：openssl rand -hex 16
     *
     * @param path           生成iv文件的工作路径，不带文件名
     * @param generateIvPath generate-iv.sh脚本path
     */
    public static void generateIVFile(String path, String generateIvPath) {
        List<String> command = new ArrayList<>();
        command.add("sh");
        command.add(generateIvPath);
        //command.add("/Users/jinmu/Downloads/self/sophon-schedule/sophon-schedule-api/src/main/resources/generate-iv.sh");
        //传入生成iv路径的参数，指定iv文件生成路径
        command.add(path + File.separator);
        System.out.println("command命令：{}" + command);
        ProcessBuilder builder = new ProcessBuilder(command);
        try {
            Process start = builder.start();
            BufferedReader br2 = new BufferedReader(new InputStreamReader(start.getErrorStream()));
            StringBuilder buf = new StringBuilder(); // 保存输出结果流
            String line;
            while ((line = br2.readLine()) != null) buf.append(line);
            System.out.println("执行生成iv文件脚本返回输出结果：{}" + buf);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("***生成iv文件完成***");
    }


    /**
     * 将mp4文件切分成ts文件，并根据keyinfo文件加密
     * generate-ts-video.sh脚本中ffmpeg切分指令：
     * ffmpeg -i a.mp4 -profile:v baseline -level 3.0  -start_number 0
     * -hls_time 20 -hls_list_size 0 -f hls -hls_key_info_file enc.keyinfo index.m3u8
     *
     * @param videoType      需要转换的视频格式 例如mp4、flv、等等
     * @param sourcePath     需要加密的视频路径
     * @param keyInfoPath    keyinfo文件的路径
     * @param generateTsPath generate-ts-video.sh脚本path
     * @throws IOException
     */
    public static void dealVideoToM3u8AndEnc(String videoType,
                                             String keyInfoPath,
                                             String sourcePath,
                                             String generateTsPath) {
        List<String> command = new ArrayList<>();
        command.add("sh");
        command.add(generateTsPath);
        //command.add("/Users/jinmu/Downloads/self/sophon-schedule/sophon-schedule-api/src/main/resources/generate-ts-video.sh");
        //需要切分的视频路径
        command.add(sourcePath);
        //keyinfo文件位置
        command.add(keyInfoPath);
        //自定义切分名称，同时包含m3u8文件的输出路径
        command.add(sourcePath.replace(videoType, "m3u8"));
        long start = System.currentTimeMillis();
        ProcessBuilder builder = new ProcessBuilder(command);
        try {
            builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println(Arrays.toString(builder.command().toArray()));
        System.out.println("总共耗时=" + (end - start) + "毫秒");
    }

    /**
     * 计算视频的时长
     *
     * @param storageFilePath 视频路径
     * @return 视频时长，单位为秒
     */
    public static int calculateVideoDuration(String storageFilePath) {
        //生成获取视频时长的指令
        String dos = "ffprobe " + storageFilePath;
        //开始时间
        long time = System.currentTimeMillis();
        String dosText = execDos(dos);
        System.out.println("耗时：" + (System.currentTimeMillis() - time) + "毫秒");
        System.out.println("dos文本：" + dosText);
        int startIndex = dosText.indexOf("Duration: ") + "Duration: ".length();
        System.out.println("startIndex:" + startIndex);
        int endIndex = dosText.indexOf(", start", startIndex);
        System.out.println("endIndex:" + endIndex);
        String durationText = dosText.substring(startIndex, endIndex).trim();
        System.out.println("视频时长文本为：{}" + durationText);
        String[] arr = durationText.split(":");
        //计算视频总时长（单位：秒），每20秒切一次
        int duration = 0;

        //计算小时单位的数量
        if (!"00".equals(arr[0])) {
            duration = (Integer.parseInt(arr[0]) * 3600);
        }
        if (!"00".equals(arr[1])) {
            duration += (Integer.parseInt(arr[1]) * 60);
        }
        if (!"00".equals(arr[2]) && !"00.00".equals(arr[2])) {
            String secondStr = arr[2];
            String[] split = secondStr.split("\\.");
            duration += Integer.parseInt(split[0]);
            if (!"00".equals(split[1])) {
                //带有毫秒的单位不为0，则默认将视频长度加1s
                duration++;
            }
        }
        System.out.println("视频时长为：" + duration + " 秒");
        return duration;
    }

    /**
     * 通过终端执行获取时长指令
     * 指令格式：ffprobe 视频路径
     *
     * @param dos 指令
     * @return 返回的文本
     */
    private static String execDos(String dos) {
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(dos);
            process.waitFor();
            InputStream in = process.getErrorStream();
            return IOUtils.toString(in, StandardCharsets.UTF_8);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 等待视频切片完成
     *
     * @param storageFilePath 发生切片的路径
     * @param fragmentNum     计算出来的视频切片数量
     */
    public static void waitVideoSegmentFinish(String storageFilePath, int fragmentNum) {
        //为了防止死循环可以循环进入的次数，通过切片数量调整次数
        long start = System.currentTimeMillis();
        int entryTime = 0;
        do {
            if (entryTime > fragmentNum * 4) {
                //正常情况下每切一次耗时10秒左右，进入循环4次，总共数量为切片耗时：fragmentNum * 3 +10
                break;
            }
            try {
                Thread.sleep(3000);
                //增加进入的次数
                entryTime++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (getAllFileNameCountTsNumber(storageFilePath) < fragmentNum);

        //ts数量与计算的切片总数一样时再等10秒，等待切片数据写入完成
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("***本次切片耗时：" + (end - start) / 1000);
    }

    /**
     * 统计当前路径下的ts后缀文件数量，通过检测文件夹下总ts文件数量
     * 与计算的ts数量是否相同判断切割是否完成（切割后端ts总大小与原视频大小不一致）
     *
     * @param directoryPath 需要遍历的文件夹路径
     * @return 文件名称
     */
    private static int getAllFileNameCountTsNumber(String directoryPath) {
        //路径下的所有文件，不包括文件夹
        List<String> list = new ArrayList<>();
        File baseFile = new File(directoryPath);
        if (baseFile.isFile() || !baseFile.exists()) {
            return 0;
        }
        File[] files = baseFile.listFiles();
        for (File file : files) {
            if (!file.isDirectory()) {
                list.add(file.getName());
            }
        }

        //计算ts文件后缀数量
        int count = 0;
        for (String s : list) {
            int start = s.lastIndexOf(".") + 1;
            String suffix = s.substring(start);
            if ("ts".equals(suffix)) {
                count++;
            }
        }
        return count;
    }

    /**
     * 获取指定目录下的ts、m3u8、enc.key文件的绝对路径列表
     *
     * @param basePath 基础路径
     * @return 路径集合
     */
    public static List<String> getUploadPathList(String basePath) {
        List<String> list = new ArrayList<>();
        File baseFile = new File(basePath);
        if (baseFile.isFile() || !baseFile.exists()) {
            return null;
        }
        File[] files = baseFile.listFiles();
        for (File file : files) {
            if (!file.isDirectory()) {
                String name = file.getName();
                int start = name.lastIndexOf(".") + 1;
                //文件后缀
                String suffix = name.substring(start);
                if ("ts".equals(suffix) || "m3u8".equals(suffix) || "key".equals(suffix)) {
                    //将需要上传的ts、m3u8、enc.key文件加入到list中
                    list.add(file.getAbsolutePath());
                }
            }
        }
        return list;
    }


    /**
     * 上传文件流
     *
     * @param path        abc/efg/
     * @param fileName    上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如123.jpg
     * @param inputStream 来自本地的文件流
     */
    public static String uploadFileWithInputSteam(String bucketName, String path, String fileName, InputStream inputStream, Long size) {
        //return minioUtils.uploadFile(oranFileName,inputStream,size);
        return null;
    }


    /**
     * 根据文件名判断类型
     *
     * @param fileName
     * @return
     */
    public static String getFileType(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            throw new RuntimeException("根据文件名判断类型报错");
        }
    }
}
