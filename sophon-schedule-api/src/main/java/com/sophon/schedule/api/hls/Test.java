package com.sophon.schedule.api.hls;


import java.io.FileNotFoundException;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/12/13 16:03
 */
public class Test {

    public static void main(String[] args) throws FileNotFoundException {
        MinioUtils minioUtils = new MinioUtils();


//        File file = new File("/Users/Jinmu/Downloads/1.png");
//        String uploadFile = minioUtils.uploadFile("2.png", new FileInputStream(file), file.length());
//        System.out.println(uploadFile);

//        minioUtils.downloadFile("1.png","/Users/Jinmu/Downloads/1111111.png");

        //HlsUtil.mp4Tom3u8AndEncrypt(null, "/2023/12/07/","3e836611147f42b18c89eeebd48a66dc.mp4");

        String url = minioUtils.getPrivateUrl("2024/01/02/4843dfa839aa430e9bb1bc3e171a104e.mp4",60);
        System.out.println(url);
    }
}
