package com.bitmovin.bitcodin.examples;

import com.bitmovin.bitcodin.api.BitcodinApi;
import com.bitmovin.bitcodin.api.exception.BitcodinApiException;
import com.bitmovin.bitcodin.api.input.HTTPInputConfig;
import com.bitmovin.bitcodin.api.input.Input;
import com.bitmovin.bitcodin.api.job.Job;
import com.bitmovin.bitcodin.api.job.JobConfig;
import com.bitmovin.bitcodin.api.job.ManifestType;
import com.bitmovin.bitcodin.api.media.*;
import com.bitmovin.bitcodin.api.thumbnail.Thumbnail;
import com.bitmovin.bitcodin.api.thumbnail.ThumbnailConfig;

/**
 * Created by
 * dmoser [david.moser@bitmovin.net]
 * on 13.11.15
 */
public class CreateThumbnail {
    public static void main(String[] args) throws InterruptedException
    {
        /* Create BitcodinApi */
        String apiKey = "INSERT YOUR API KEY";
        BitcodinApi bitApi = new BitcodinApi(apiKey);

        /* Create URL Input */
        HTTPInputConfig httpInputConfig = new HTTPInputConfig();
        httpInputConfig.url = "http://bitbucketireland.s3.amazonaws.com/Sintel-original-short.mkv";

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
        videoConfig.bitrate = 1024 * 1024;
        videoConfig.width = 640;
        videoConfig.height = 480;
        videoConfig.profile = Profile.MAIN;
        videoConfig.preset = Preset.STANDARD;

        AudioStreamConfig audioConfig = new AudioStreamConfig();
        audioConfig.bitrate = 256000;
        audioConfig.samplerate = 320000;

        EncodingProfileConfig encodingProfileConfig = new EncodingProfileConfig();
        encodingProfileConfig.name = "CreateVttHls Test Profile";
        encodingProfileConfig.videoStreamConfigs.add(videoConfig);
        encodingProfileConfig.audioStreamConfigs.add(audioConfig);

        EncodingProfile encodingProfile;
        try {
            encodingProfile = bitApi.createEncodingProfile(encodingProfileConfig);
        } catch (BitcodinApiException e) {
            System.out.println("Could not create encoding profile: " + e.getMessage());
            return;
        }

        /* Create Job */
        JobConfig jobConfig = new JobConfig();
        jobConfig.encodingProfileId = encodingProfile.encodingProfileId;
        jobConfig.inputId = input.inputId;
        jobConfig.manifestTypes.addElement(ManifestType.MPEG_DASH_MPD);
        jobConfig.manifestTypes.addElement(ManifestType.HLS_M3U8);

        Job job;
        try {
            job = bitApi.createJob(jobConfig);
        } catch (BitcodinApiException e) {
            System.out.println("Could not create job: " + e.getMessage());
            return;
        }
        System.out.println("Created Job with id: " + job.jobId);

        /* Create thumbnail */
        ThumbnailConfig thumbnailConfig = new ThumbnailConfig();
        thumbnailConfig.jobId = job.jobId;
        thumbnailConfig.height = 320;
        thumbnailConfig.position = 45;

        try {
            Thumbnail thumbnail = bitApi.createThumbnail(thumbnailConfig);
            System.out.println("Successfully created thumbnail! Thumbnail URL: " + thumbnail.thumbnailUrl);
        }
        catch (BitcodinApiException e) {
            System.out.println("Could not create thumbnail: " + e.getMessage());
        }
    }
}

