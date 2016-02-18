package examples.com.bitmovin.bitcodin.examples;

import com.bitmovin.bitcodin.api.BitcodinApi;
import com.bitmovin.bitcodin.api.exception.BitcodinApiException;
import com.bitmovin.bitcodin.api.input.AzureInputConfig;
import com.bitmovin.bitcodin.api.input.Input;
import com.bitmovin.bitcodin.api.job.*;
import com.bitmovin.bitcodin.api.media.*;
import com.bitmovin.bitcodin.api.output.AzureOutputConfig;
import com.bitmovin.bitcodin.api.output.Output;
import com.bitmovin.bitcodin.api.transfer.Transfer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
        inputConfig.container = "container";

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
                System.out.println("Status: " + jobDetails.status.toString() + " - Enqueued Duration: " + jobDetails.enqueueDuration + "s" +
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

        /* Wait till transfer is finished */

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = null;

        Transfer[] transfers = null;
        int finishedTransfers = 0;
        do {
            cal = Calendar.getInstance();
            try
            {
                transfers = bitApi.listTransfers(job.jobId);
                finishedTransfers = 0;
                for (Transfer t : transfers)
                {
                    System.out.println(dateFormat.format(cal.getTime()) + " - Transfer: JobID " + t.jobId + " Progress[" + t.progress + "] Status[" + t.status + "]");
                    if (t.progress == 100)
                        finishedTransfers++;
                }
                Thread.sleep(2000);
            } catch (Exception e)
            {
                System.out.println("Unexpected error!");
            }
        } while (transfers == null || transfers.length == 0 || finishedTransfers < transfers.length );

        System.out.println("Transfer Finished");
    }
}
