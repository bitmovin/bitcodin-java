package examples.com.bitmovin.bitcodin.examples;

import com.bitmovin.bitcodin.api.BitcodinApi;
import com.bitmovin.bitcodin.api.exception.BitcodinApiException;
import com.bitmovin.bitcodin.api.input.AzureInputConfig;
import com.bitmovin.bitcodin.api.input.Input;
import com.bitmovin.bitcodin.api.job.*;
import com.bitmovin.bitcodin.api.media.*;
import com.bitmovin.bitcodin.api.output.AzureOutputConfig;
import com.bitmovin.bitcodin.api.output.Output;

public class CreateAzureInputOutputJobWithPlayreadyWidevineCombinedDRM {

    public static void main(String[] args) throws InterruptedException {

        /* Create BitcodinApi */
        String apiKey = "YOURAPIKEY";
        BitcodinApi bitApi = new BitcodinApi(apiKey);

        /* Create Azure Input */
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

        /* Create Azure Output */
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

        /* Create CombinedDrmConfig */
        CombinedDrmConfig drmConfig = new CombinedDrmConfig();
        drmConfig.kid = "eb676abbcb345e96bbcf616630f1a3da";
        drmConfig.key = "100b6c20940f779a4589152b57d2dacb";
        drmConfig.laUrl = "http://playready.directtaps.net/pr/svc/rightsmanager.asmx?PlayRight=1&ContentKey=EAtsIJQPd5pFiRUrV9Layw==";
        drmConfig.pssh = "CAESEOtnarvLNF6Wu89hZjDxo9oaDXdpZGV2aW5lX3Rlc3QiEGZrajNsamFTZGZhbGtyM2oqAkhEMgA=";

        /* Create Job */
        JobConfig jobConfig = new JobConfig();
        jobConfig.encodingProfileId = encodingProfile.encodingProfileId;
        jobConfig.inputId = azureInput.inputId;
        jobConfig.outputId = output.outputId;
        jobConfig.manifestTypes.addElement(ManifestType.MPEG_DASH_MPD);
        jobConfig.manifestTypes.addElement(ManifestType.HLS_M3U8);
        jobConfig.speed = Speed.PREMIUM;
        jobConfig.drmConfig = drmConfig;

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
    }
}
