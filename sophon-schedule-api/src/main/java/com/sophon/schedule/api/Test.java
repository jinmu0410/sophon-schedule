package com.sophon.schedule.api;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avformat;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.FFmpegLogCallback;

public class Test {

    public static void main(String[] args) {
        Long time = System.currentTimeMillis();
        // 输入视频文件路径
        String inputVideoPath = "/Users/jinmu/Downloads/ffmpeg/test.mp4";

        // 输出 FLV 文件路径
        String outputFlvPath = "/Users/jinmu/Downloads/ffmpeg/test-cc.flv";

        convertToFlv(inputVideoPath, outputFlvPath);

        System.out.println("time = " + (System.currentTimeMillis() - time));
    }

    public static void convertToFlv(String inputPath, String outputPath) {
        avutil.av_log_set_level(avutil.AV_LOG_INFO); // 设置日志级别
        FFmpegLogCallback.set();
        try {
            // 创建视频帧抓取器
            FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputPath);
            grabber.start();

            // 创建视频帧录制器
            FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputPath, grabber.getImageWidth(), grabber.getImageHeight());
            recorder.setFormat("flv"); // 设置输出格式为 FLV
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); // 设置视频编解码器为 H.264
            recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC); // 设置音频编解码器为 AAC

            recorder.setFrameRate(grabber.getFrameRate()); // 设置帧率
            recorder.setVideoBitrate(grabber.getVideoBitrate()); // 设置视频比特率
            recorder.setSampleRate(grabber.getSampleRate()); // 设置音频采样率
            recorder.setAudioBitrate(grabber.getAudioBitrate()); // 设置音频比特率
            recorder.setAudioChannels(grabber.getAudioChannels());

            recorder.start();

            // 逐帧读取并写入
            for (int i = 0; i < grabber.getLengthInFrames(); i++) {
                recorder.record(grabber.grabFrame());
            }

            recorder.stop();
            recorder.release();
            grabber.stop();
            grabber.release();

            System.out.println("Conversion to FLV completed successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
