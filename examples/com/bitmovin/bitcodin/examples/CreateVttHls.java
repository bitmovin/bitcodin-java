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
import com.bitmovin.bitcodin.api.manifest.*;
import com.bitmovin.bitcodin.api.media.*;

public class CreateVttHls
{
    public static void main(String[] args) throws InterruptedException
    {
        /* Create BitcodinApi */
        String apiKey = "YOUR API KEY";
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

        /* Create VTT Manifest */
        VttSubtitle engSub = new VttSubtitle();
        engSub.langLong = "English";
        engSub.langShort = "en";
        engSub.url = "http://url.to/your/eng.vtt";

        VttSubtitle deSub = new VttSubtitle();
        deSub.langLong = "Deutsch";
        deSub.langShort = "de";
        deSub.url = "http://url.to/your/de.vtt";

        VttSubtitle[] subtitles = {engSub, deSub};

        VttHlsConfig vttHlsConfig = new VttHlsConfig();
        vttHlsConfig.jobId = job.jobId;
        vttHlsConfig.subtitles = subtitles;
        vttHlsConfig.outputFileName = "vttTestHls.m3u8";

        try {
            VttHls vttHls = bitApi.createVttHls(vttHlsConfig);
            System.out.println("VTT HLS URL: " + vttHls.hlsUrl);
        } catch (BitcodinApiException e) {
            System.out.println("Could not create Vtt HLS! " + e.getMessage());
        }
    }
}
