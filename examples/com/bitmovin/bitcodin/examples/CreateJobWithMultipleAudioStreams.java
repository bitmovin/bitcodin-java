package com.bitmovin.bitcodin.examples;

import com.bitmovin.bitcodin.api.BitcodinApi;
import com.bitmovin.bitcodin.api.exception.BitcodinApiException;
import com.bitmovin.bitcodin.api.input.HTTPInputConfig;
import com.bitmovin.bitcodin.api.input.Input;
import com.bitmovin.bitcodin.api.job.*;
import com.bitmovin.bitcodin.api.media.*;
import sun.audio.AudioStream;

/**
 * Created by Dominic Miglar <dominic.miglar@bitmovin.net> on 8/26/15.
 */
public class CreateJobWithMultipleAudioStreams {
    public static void main(String[] args) throws InterruptedException {

        /* Create BitcodinApi */
        String apiKey = "YOUR_API_KEY";
        BitcodinApi bitApi = new BitcodinApi(apiKey);

        /* Create URL Input */
        HTTPInputConfig httpInputConfig = new HTTPInputConfig();
        httpInputConfig.url = "http://bitbucketireland.s3.amazonaws.com/Sintel-two-audio-streams-short.mkv";

        Input input;
        try {
            input = bitApi.createInput(httpInputConfig);
        } catch (BitcodinApiException e) {
            System.out.println("Could not create input: " + e.getMessage());
            return;
        }

        System.out.println("Created Input: " + input.filename);

        /* Create EncodingProfile */
        VideoStreamConfig videoConfig = new VideoStreamConfig();
        videoConfig.bitrate = 1 * 1024 * 1024;
        videoConfig.width = 640;
        videoConfig.height = 480;
        videoConfig.profile = Profile.MAIN;
        videoConfig.preset = Preset.STANDARD;

        AudioStreamConfig audioStreamConfigOne = new AudioStreamConfig();
        AudioStreamConfig audioStreamConfigTwo = new AudioStreamConfig();
        audioStreamConfigOne.defaultStreamId = 0;
        audioStreamConfigOne.bitrate = 192000;
        audioStreamConfigTwo.defaultStreamId = 1;
        audioStreamConfigTwo.bitrate = 192000;

        EncodingProfileConfig encodingProfileConfig = new EncodingProfileConfig();
        encodingProfileConfig.name = "JUnitTestProfile";
        encodingProfileConfig.videoStreamConfigs.add(videoConfig);
        encodingProfileConfig.audioStreamConfigs.add(audioStreamConfigOne);
        encodingProfileConfig.audioStreamConfigs.add(audioStreamConfigTwo);

        EncodingProfile encodingProfile;
        try {
            encodingProfile = bitApi.createEncodingProfile(encodingProfileConfig);
        } catch (BitcodinApiException e) {
            System.out.println("Could not create encoding profile: " + e.getMessage());
            return;
        }

        /* Create AudioMetaData */
        AudioMetaData audioMetaDataOne = new AudioMetaData();
        AudioMetaData audioMetaDataTwo = new AudioMetaData();
        audioMetaDataOne.defaultStreamId = 0;
        audioMetaDataOne.language = "de";
        audioMetaDataOne.label = "Just Sound";
        audioMetaDataTwo.defaultStreamId = 1;
        audioMetaDataTwo.language = "en";
        audioMetaDataTwo.label = "Sound and Voice";

        /* Create Job */
        JobConfig jobConfig = new JobConfig();
        jobConfig.encodingProfileId = encodingProfile.encodingProfileId;
        jobConfig.inputId = input.inputId;
        jobConfig.manifestTypes.addElement(ManifestType.MPEG_DASH_MPD);
        jobConfig.manifestTypes.addElement(ManifestType.HLS_M3U8);
        jobConfig.speed = Speed.STANDARD;
        jobConfig.audioMetaData = new AudioMetaData[]{audioMetaDataOne, audioMetaDataTwo};

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
