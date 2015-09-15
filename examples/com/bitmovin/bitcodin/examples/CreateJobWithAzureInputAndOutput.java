package com.bitmovin.bitcodin.examples;

import com.bitmovin.bitcodin.api.BitcodinApi;
import com.bitmovin.bitcodin.api.exception.BitcodinApiException;
import com.bitmovin.bitcodin.api.input.HTTPInputConfig;
import com.bitmovin.bitcodin.api.input.Input;
import com.bitmovin.bitcodin.api.job.Job;
import com.bitmovin.bitcodin.api.job.JobConfig;
import com.bitmovin.bitcodin.api.job.JobDetails;
import com.bitmovin.bitcodin.api.job.JobStatus;
import com.bitmovin.bitcodin.api.job.ManifestType;
import com.bitmovin.bitcodin.api.media.EncodingProfile;
import com.bitmovin.bitcodin.api.media.EncodingProfileConfig;
import com.bitmovin.bitcodin.api.media.Preset;
import com.bitmovin.bitcodin.api.media.Profile;
import com.bitmovin.bitcodin.api.media.VideoStreamConfig;
import com.bitmovin.bitcodin.api.output.AzureOutputConfig;

public class TranscodeSintelToDASHAndHLS {

    public static void main(String[] args) throws InterruptedException {

        /* Create BitcodinApi */
        String apiKey = "YOUR_API_KEY";
        BitcodinApi bitApi = new BitcodinApi(apiKey);

        /* Create URL Input */
        AzureInputConfig inputConfig = new AzureInputConfig();
        inputConfig.accountKey = "YOUR_AZURE_ACCOUNT_KEY";
        inputConfig.accountName = "YOUR_AZURE_ACCOUNT_NAME";
        inputConfig.url = "URL_TO_YOUR_FILE";

        try {
            Input azureInput = bitApi.createAzureInput(inputConfig);
        } catch (BitcodinApiException e) {
            System.out.println("Could not create input: " + e.getMessage());
            return;
        }

        System.out.println("Created Azure Input: " + azureInput.filename);

        /* Create EncodingProfile */
        VideoStreamConfig videoConfig = new VideoStreamConfig();
        videoConfig.bitrate = 1 * 1024 * 1024;
        videoConfig.width = 640;
        videoConfig.height = 480;
        videoConfig.profile = Profile.MAIN;
        videoConfig.preset = Preset.STANDARD;

        EncodingProfileConfig encodingProfileConfig = new EncodingProfileConfig();
        encodingProfileConfig.name = "JavaTestProfile";
        encodingProfileConfig.videoStreamConfigs.add(videoConfig);

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
        azureOutput.accountKey = "YOUR_AZURE_ACCOUNT_KEY";
        azureOutput.accountName = "YOUR_AZURE_ACCOUNT_NAME";
        azureOutput.name = "AzureOutput";
        azureOutput.container = "YOUR_AZURE_CONTAINER";
        azureOutput.prefix = "YOUR_PREFIX";
        azureOutput.makePublic = true;

        output = bitApi.createAzureOutput(azureOutput);

        TransferConfig transferConfig = new TransferConfig();
        transferConfig.outputId = output.outputId;
        transferConfig.jobId = job.jobId;

        bitApi.transfer(transferConfig);

        System.out.println("Job with ID " + job.jobId + " finished successfully!");
    }
}
