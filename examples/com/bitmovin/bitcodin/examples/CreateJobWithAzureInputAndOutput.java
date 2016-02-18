package com.bitmovin.bitcodin.examples;

import com.bitmovin.bitcodin.api.BitcodinApi;
import com.bitmovin.bitcodin.api.exception.BitcodinApiException;
import com.bitmovin.bitcodin.api.input.AzureInputConfig;
import com.bitmovin.bitcodin.api.input.Input;
import com.bitmovin.bitcodin.api.job.*;
import com.bitmovin.bitcodin.api.media.*;
import com.bitmovin.bitcodin.api.output.AzureOutputConfig;
import com.bitmovin.bitcodin.api.output.Output;
import com.bitmovin.bitcodin.api.transfer.TransferConfig;

public class CreateJobWithAzureInputAndOutput {

    public static void main(String[] args) throws InterruptedException {

        /* Create BitcodinApi */
        String apiKey = "YOUR_API_KEY";
        BitcodinApi bitApi = new BitcodinApi(apiKey);

        /* Create URL Input */
        AzureInputConfig inputConfig = new AzureInputConfig();
        inputConfig.accountKey = "accountKey";
        inputConfig.accountName = "accountName";
        inputConfig.url = "http://accountName.blob.core.windows.net/container/video.mkv";
        inputConfig.container = "containerr";

        Input azureInput = null;
        try {
            azureInput =  bitApi.createAzureInput(inputConfig);
        } catch (BitcodinApiException e) {
            e.printStackTrace();
        }

        System.out.println("Created Azure Input: " + azureInput.filename);

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
        jobConfig.inputId = azureInput.inputId;
        jobConfig.manifestTypes.addElement(ManifestType.MPEG_DASH_MPD);
        jobConfig.manifestTypes.addElement(ManifestType.HLS_M3U8);
        jobConfig.speed = Speed.STANDARD;

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

        AzureOutputConfig azureOutput = new AzureOutputConfig();
        azureOutput.accountKey = "accountKey";
        azureOutput.accountName = "accountName";
        azureOutput.name = "name";
        azureOutput.container = "container";
        azureOutput.prefix = "prefix";
        azureOutput.createSubDirectory = false;
        Output output = null;
        try {
            output = bitApi.createAzureOutput(azureOutput);
        } catch (BitcodinApiException e) {
            e.printStackTrace();
        }

        TransferConfig transferConfig = new TransferConfig();
        transferConfig.outputId = output.outputId;
        transferConfig.jobId = job.jobId;

        try {
            bitApi.transfer(transferConfig);
        } catch (BitcodinApiException e) {
            e.printStackTrace();
        }

        System.out.println("Job with ID " + job.jobId + " finished successfully!");
    }
}
