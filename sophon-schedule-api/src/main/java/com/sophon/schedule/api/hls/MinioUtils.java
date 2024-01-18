package com.sophon.schedule.api.hls;

import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import io.netty.handler.codec.http.HttpMethod;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class MinioUtils {
    private String bucketName = "shortv";

    private static String url = "http://47.113.150.189:9000";
    private static String accessKey = "DehdaXh4syoFbJBaJKQJ";
    private static String secretKey = "hpN0XuY1PgShuSNNBd4HqVW2bKObjWBDZ6xBUC2u";

    private MinioClient minioClient;

    public MinioUtils(){
        minioClient = minioClient();
    }
    public MinioClient minioClient() {
        System.out.println(url + "," + accessKey + "," + secretKey);
        MinioClient minioClient = MinioClient.builder()
                .endpoint(url)
                .credentials(accessKey, secretKey)
                .build();
        try{
            List<Bucket> list = minioClient.listBuckets();
            System.out.println("Bucket List:" + list.size());
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return minioClient;
    }

    /**
     * 创建桶
     */
    public boolean createBucket(){
        try{
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除桶
     */
    public void removeBucket(String bucketName) throws Exception{
        minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
    }

    /**
     * 上传文件
     */
    public String uploadFile(String fileName, InputStream stream, Long fileSize){
        try{
            ObjectWriteResponse putObject = minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(stream, fileSize, -1)
                    .build());

            return putObject.object();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String getPrivateUrl(String objectKey, Integer second) {
        String url = null;
        try {
            url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .bucket(bucketName)
                            .object(objectKey)
                            .method(Method.GET)
                            .expiry(second)
                            .build());
        } catch (ErrorResponseException e) {
            throw new RuntimeException(e);
        } catch (InsufficientDataException e) {
            throw new RuntimeException(e);
        } catch (InternalException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidResponseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (XmlParserException e) {
            throw new RuntimeException(e);
        } catch (ServerException e) {
            throw new RuntimeException(e);
        }

        return url;
    }

    public GetObjectResponse getStream(String path ,String fileName){
        try{
            return minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucketName).object(path + fileName).build());
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void downloadFile(String fileName,String sinkFileName){
        try{
            GetObjectResponse response = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build());
            FileUtils.copyInputStreamToFile(response, new File(sinkFileName));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 判断文件夹是否存在
     * @return
     */
    public Boolean folderExists(String bucketName, String prefix) throws Exception{
        Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).prefix(
                prefix).recursive(false).build());
        for (Result<Item> result : results) {
            Item item = result.get();
            if (item.isDir()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 创建文件夹
     *
     * @param bucketName 桶名称
     * @param path 路径
     */
    public void createFolder(String bucketName, String path) throws Exception{
        minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(path)
                .stream(new ByteArrayInputStream(new byte[] {}), 0, -1).build());
    }

    /**
     * 获取文件在minio在服务器上的外链
     *
     */
    public String getUrl(String objectName) throws Exception{
        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(
                bucketName).object(objectName).expiry(1, TimeUnit.DAYS).build());
    }

    /**
     * 获取minio中,某个bucket中所有的文件名
     */
    public Map<String, String> getFileList() {
        Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName)
                .recursive(true).build());
        Map<String, String> fileUrlData = new HashMap<>();
        for (Result<Item> result : results) {
            Item item;
            String fileName = null;
            try {
                item = result.get();
                fileName = item.objectName();
                String url = getUrl(fileName);
                fileUrlData.put(fileName, url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fileUrlData;
    }
}