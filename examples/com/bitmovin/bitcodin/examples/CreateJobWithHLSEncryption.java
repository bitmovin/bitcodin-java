package com.bitmovin.bitcodin.examples;

import com.bitmovin.bitcodin.api.BitcodinApi;
import com.bitmovin.bitcodin.api.exception.BitcodinApiException;
import com.bitmovin.bitcodin.api.input.HTTPInputConfig;
import com.bitmovin.bitcodin.api.input.Input;
import com.bitmovin.bitcodin.api.job.*;
import com.bitmovin.bitcodin.api.media.*;

/**
 * Created by Dominic Miglar <dominic.miglar@bitmovin.net> on 8/26/15.
 */
public class CreateJobWithHLSEncryption {
    public static void main(String[] args) throws InterruptedException {

        /* Create BitcodinApi */
        String apiKey = "YOUR_API_KEY";
        BitcodinApi bitApi = new BitcodinApi(apiKey);

        /* Create URL Input */
        HTTPInputConfig httpInputConfig = new HTTPInputConfig();
        httpInputConfig.url = "http://bitbucketireland.s3.amazonaws.com/h264_720p_mp_3.1_3mbps_aac_shrinkage.mp4";

        Input input;
        try {
            input = bitApi.createInput(httpInputConfig);
        } catch (BitcodinApiException e) {
            System.out.println("Could not create input: " + e.getMessage());
            return;
        }

        System.out.println("Created Input: " + input.filename);

        /* Create EncodingProfile */
        VideoStreamConfig videoConfig1 = new VideoStreamConfig();
        videoConfig1.bitrate = 4800000;
        videoConfig1.width = 1920;
        videoConfig1.height = 1080;
        videoConfig1.profile = Profile.MAIN;
        videoConfig1.preset = Preset.PREMIUM;

        VideoStreamConfig videoConfig2 = new VideoStreamConfig();
        videoConfig2.bitrate = 2400000;
        videoConfig2.width = 1280;
        videoConfig2.height = 720;
        videoConfig2.profile = Profile.MAIN;
        videoConfig2.preset = Preset.PREMIUM;

        VideoStreamConfig videoConfig3 = new VideoStreamConfig();
        videoConfig3.bitrate = 1200000;
        videoConfig3.width = 854;
        videoConfig3.height = 480;
        videoConfig3.profile = Profile.MAIN;
        videoConfig3.preset = Preset.PREMIUM;

        EncodingProfileConfig encodingProfileConfig = new EncodingProfileConfig();
        encodingProfileConfig.name = "JavaTestProfile";
        encodingProfileConfig.videoStreamConfigs.add(videoConfig1);
        encodingProfileConfig.videoStreamConfigs.add(videoConfig2);
        encodingProfileConfig.videoStreamConfigs.add(videoConfig3);

        AudioStreamConfig audioStreamConfig = new AudioStreamConfig();
        audioStreamConfig.defaultStreamId = 0;
        audioStreamConfig.bitrate = 192000;
        encodingProfileConfig.audioStreamConfigs.add(audioStreamConfig);

        EncodingProfile encodingProfile;
        try {
            encodingProfile = bitApi.createEncodingProfile(encodingProfileConfig);
        } catch (BitcodinApiException e) {
            System.out.println("Could not create encoding profile: " + e.getMessage());
            return;
        }

        /* Create HlsEncryptionConfig */
        HlsEncryptionConfig hlsEncryptionConfig = new HlsEncryptionConfig();
        hlsEncryptionConfig.method = HlsMethod.SAMPLE_AES;
        hlsEncryptionConfig.key = "cab5b529ae28d5cc5e3e7bc3fd4a544d";

        /* Create Job */
        JobConfig jobConfig = new JobConfig();
        jobConfig.encodingProfileId = encodingProfile.encodingProfileId;
        jobConfig.inputId = input.inputId;
        jobConfig.manifestTypes.addElement(ManifestType.MPEG_DASH_MPD);
        jobConfig.manifestTypes.addElement(ManifestType.HLS_M3U8);
        jobConfig.speed = Speed.STANDARD;
        jobConfig.hlsEncryptionConfig = hlsEncryptionConfig;

        Job job;
        try {
            job = bitApi.createJob(jobConfig);
        } catch (BitcodinApiException e) {
            System.out.println("Could not create job: " + e.getMessage());
            return;
        }

        JobDetails jobDetails;

        do {
            try {
                jobDetails = bitApi.getJobDetails(job.jobId);
                System.out.println("Status: " + jobDetails.status.toString() +
                        " - Enqueued Duration: " + jobDetails.enqueueDuration + "s" +
                        " - Realtime Factor: " + jobDetails.realtimeFactor +
                        " - Encoded Duration: " + jobDetails.encodedDuration + "s" +
                        " - Output: " + jobDetails.bytesWritten/1024/1024 + "MB");
            } catch (BitcodinApiException e) {
                System.out.println("Could not get any job details");
                return;
            }

            if (jobDetails.status == JobStatus.ERROR) {
                System.out.println("Error during transcoding");
                return;
            }

            Thread.sleep(2000);

        } while (jobDetails.status != JobStatus.FINISHED);

        System.out.println("Job with ID " + job.jobId + " finished successfully!");
    }
}
