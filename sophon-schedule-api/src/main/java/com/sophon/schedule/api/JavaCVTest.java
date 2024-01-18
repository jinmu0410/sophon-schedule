package com.sophon.schedule.api;

import org.bytedeco.ffmpeg.avcodec.AVPacket;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.*;
import sun.font.FontDesignMetrics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/11/29 14:10
 */
public class JavaCVTest {


    public static void main(String[] args) throws IOException, InterruptedException {
        Long time = System.currentTimeMillis();
        String sourcePath = "/Users/jinmu/Downloads/ffmpeg/test.mp4";
        String outputFlvPath = "/Users/jinmu/Downloads/ffmpeg/test-bb.flv";
        String outPath = "/Users/jinmu/Downloads/ffmpeg/test3/test.m3u8";
        String seg = "/Users/jinmu/Downloads/ffmpeg/test3/test-%3d.ts";

        //segmentVideoToHls(sourcePath, outPath, seg);
        //test();
        //segmentVideoToFlv(sourcePath,outputFlvPath);
        //test(sourcePath,"ffmpeg -i /Users/jinmu/Downloads/ffmpeg/test.mp4 -f flv /Users/jinmu/Downloads/ffmpeg/test-111.flv");

        test1(sourcePath,outputFlvPath);
        System.out.println("time = " + (System.currentTimeMillis() - time));
    }

    public static void segmentVideoToHls(String originalFilePath, String m3u8FilePath, String segmentFilePath) {
        try {
            segmentVideoToHls(new FileInputStream(originalFilePath), m3u8FilePath, segmentFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 保持m3u8FilePath 和 segmentFilePath在一个目录下
     *
     * @param inputStream
     * @param m3u8FilePath
     * @param segmentFilePath
     * @throws IOException
     */
    public static void segmentVideoToHls(InputStream inputStream, String m3u8FilePath, String segmentFilePath) throws IOException {
        avutil.av_log_set_level(avutil.AV_LOG_INFO);
        FFmpegLogCallback.set();

        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputStream);
        grabber.start();

        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(m3u8FilePath, grabber.getImageWidth(), grabber.getImageHeight(), grabber.getAudioChannels());

        recorder.setFormat("hls");
        recorder.setOption("hls_time", "20");
        recorder.setOption("hls_list_size", "0");
        recorder.setOption("hls_flags", "delete_segments");
        recorder.setOption("hls_delete_threshold", "1");
        recorder.setOption("hls_segment_type", "mpegts");
        recorder.setOption("hls_segment_filename", segmentFilePath);
        //加密文件
        //recorder.setOption("hls_key_info_file", infoUrl);

        // http属性
        //recorder.setOption("method", "POST");

        //控制m3u8文件的质量
        recorder.setFrameRate(25); // 设置帧率为25帧/秒
        recorder.setGopSize(2 * 25); // 设置关键帧间隔为2秒，即每两帧为一个关键帧
        recorder.setVideoQuality(1.0); // 设置视频质量为1.0（最高质量）
        //1080P视频对应码率是4Mbytes每秒，也就是recorder.setVideoBitrate(512*1024);
        recorder.setVideoBitrate(10 * 1024); // 设置视频比特率为10kbps
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); // 设置视频编码器为H.264
        recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC); // 设置音频编码器为AAC

        recorder.start();

        Frame frame;
        while ((frame = grabber.grabImage()) != null)
            try {
                recorder.record(frame);
            } catch (Exception e) {
                e.printStackTrace();
            }
        recorder.setTimestamp(grabber.getTimestamp());
        recorder.close();
        grabber.close();
    }

    /**
     * 切割视频指定的位置
     *
     * @param videoPath      视频路径
     * @param start          视频开始时间 ，单位秒
     * @param end            视频结束时间
     * @param recodeAudio    是否录制音频
     * @param targetFilePath 生成的文件路径
     * @return 生成文件路径
     * @throws FFmpegFrameGrabber.Exception
     * @throws FFmpegFrameRecorder.Exception
     */
    public static void cutVideo(String videoPath, Integer start, Integer end, boolean recodeAudio, String targetFilePath) throws FFmpegFrameGrabber.Exception, FFmpegFrameRecorder.Exception {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoPath);
        grabber.start();
        FFmpegFrameRecorder fFmpegFrameRecorder = new FFmpegFrameRecorder(targetFilePath, grabber.grabImage().imageWidth, grabber.grabImage().imageHeight, recodeAudio ? 1 : 0);
        fFmpegFrameRecorder.start();
        Frame frame = null;
        while ((frame = grabber.grabFrame(recodeAudio, true, true, false)) != null) {
            if ((start == null ? 0 : (start * 1000000)) < frame.timestamp && (end == null || ((end * 1000000) > frame.timestamp))) {
                fFmpegFrameRecorder.record(frame);
            }
        }

        fFmpegFrameRecorder.stop();
        fFmpegFrameRecorder.release();
    }


    /**
     * 视频添加水印
     *
     * @param videoPath      视频地址
     * @param text           水印文本
     * @param x              水印位置x
     * @param y              水印位置y
     * @param color          水印颜色
     * @param fontSize       水印文字大小
     * @param targetFilePath 生成视频路径
     * @throws Exception
     */
    public static void waterMark(String videoPath, String text, Integer x, Integer y, Color color, Integer fontSize, String targetFilePath) throws Exception {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoPath);
        grabber.start();
        Frame frame = grabber.grabImage();

        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(targetFilePath, frame.imageWidth, frame.imageHeight, 0);
        recorder.start();
        int j = 0;
        while ((frame = grabber.grabImage()) != null) {
            Java2DFrameConverter converter = new Java2DFrameConverter();
            BufferedImage bufImg = converter.getBufferedImage(frame);

            Font font = new Font("微软雅黑", Font.BOLD, 64);
//            String content = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//            String content1 = "字符滚动效果";
            FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);
            int width = bufImg.getWidth();//计算图片的宽
            int height = bufImg.getHeight();//计算高
            Graphics2D graphics = bufImg.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            graphics.drawImage(bufImg, 0, 0, bufImg.getWidth(), bufImg.getHeight(), null);

            graphics.setColor(color);
            graphics.setFont(new Font("微软雅黑", Font.BOLD, fontSize));
            graphics.drawString(text, x, y);
            graphics.dispose();

            frame = converter.getFrame(bufImg);

            recorder.record(frame);
            j++;
        }
        grabber.stop();
        recorder.stop();
        recorder.release();
    }


    /**
     * 合并音视频
     *
     * @param audioPath        音频文件
     * @param videoPath        视频文件
     * @param targetFileFormat 合并之后的视频格式
     * @param targetFilePath   合并之后的视频文件路径
     */
    public static void mergeAV(String audioPath, String videoPath, String targetFileFormat, String targetFilePath) {
        try (FFmpegFrameGrabber imageGrabber = new FFmpegFrameGrabber(videoPath);
             FFmpegFrameGrabber audioGrabber = new FFmpegFrameGrabber(audioPath)) {
            imageGrabber.start();
            audioGrabber.start();
            try (FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(targetFilePath, imageGrabber.getImageWidth(), imageGrabber.getImageHeight(),
                    1)) {
                recorder.setInterleaved(true);
                recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
                recorder.setFormat(targetFileFormat);
                recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P); // yuv420p
                int frameRate = 25;
                recorder.setFrameRate(frameRate);
                recorder.setGopSize(frameRate * 2);
                recorder.setAudioOption("crf", "0");
                recorder.setAudioQuality(0);// 最高质量
                recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);
                recorder.setAudioChannels(2);
                recorder.setSampleRate(44100);
                recorder.start();
                long videoTime = imageGrabber.getLengthInTime();
                Frame imageFrame = null;
                while ((imageFrame = imageGrabber.grabImage()) != null) {
                    recorder.record(imageFrame);
                }
                long audioPlayTime = 0L;
                Frame sampleFrame = null;
                while ((sampleFrame = audioGrabber.grabSamples()) != null || audioPlayTime < videoTime) {
                    if (sampleFrame == null) {
                        audioGrabber.restart();//重新开始
                        sampleFrame = audioGrabber.grabSamples();
                        videoTime -= audioPlayTime;
                    }
                    recorder.record(sampleFrame);
                    audioPlayTime = audioGrabber.getTimestamp();
                    if (audioPlayTime >= videoTime) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void segmentVideoToFlv(String inputFile,String outputFile) throws FFmpegFrameGrabber.Exception, FFmpegFrameRecorder.Exception {
        avutil.av_log_set_level(avutil.AV_LOG_INFO);
        FFmpegLogCallback.set();

        int audioChannels = 2;

        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputFile);
        grabber.start();

        // 获取视频流信息
        int width = grabber.getImageWidth();
        int height = grabber.getImageHeight();
        int frameRate = (int) grabber.getFrameRate();
        int videoCodecId = grabber.getVideoCodec();

        // 创建FFmpegFrameRecorder对象
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFile, width, height);
        recorder.setAudioChannels(audioChannels);
        recorder.setVideoCodec(videoCodecId);
        recorder.setFormat("flv");
        recorder.setFrameRate(frameRate);
        recorder.start();

        // 逐帧读取并写入
        Frame frame;
        while ((frame = grabber.grab()) != null) {
            recorder.record(frame);
        }
        // 停止抓取和录制
        grabber.stop();
        recorder.stop();
    }

    public static void test(String sourcePath,String command) throws FFmpegFrameGrabber.Exception {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(sourcePath);
        grabber.setOption("ffmpeg_command",command);

        grabber.start();

        // 停止抓取和录制
        grabber.stop();
    }

    public static void test1(String inputFile,String outputFile) throws FrameGrabber.Exception, FrameRecorder.Exception {
        FFmpegLogCallback.set();

        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputFile);
        grabber.start();

        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFile, grabber.getImageWidth(), grabber.getImageHeight(), grabber.getAudioChannels());
        recorder.setFormat("flv");
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);

        recorder.start();

        Frame frame;
        while ((frame = grabber.grabImage()) != null)
            try {
                recorder.record(frame);
            } catch (Exception e) {
                e.printStackTrace();
            }
        recorder.setTimestamp(grabber.getTimestamp());

        recorder.close();
        grabber.close();
    }
}
