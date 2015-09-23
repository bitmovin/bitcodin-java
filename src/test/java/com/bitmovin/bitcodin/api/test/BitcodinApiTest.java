package com.bitmovin.bitcodin.api.test;

import com.bitmovin.bitcodin.api.BitcodinApi;
import com.bitmovin.bitcodin.api.exception.BitcodinApiException;
import com.bitmovin.bitcodin.api.input.*;
import com.bitmovin.bitcodin.api.job.*;
import com.bitmovin.bitcodin.api.media.*;
import com.bitmovin.bitcodin.api.output.*;
import com.bitmovin.bitcodin.api.statistics.MonthlyStatistic;
import com.bitmovin.bitcodin.api.statistics.Statistic;
import com.bitmovin.bitcodin.api.transfer.TransferConfig;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.ParallelComputer;
import org.junit.rules.ExpectedException;
import org.junit.runner.JUnitCore;

import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class BitcodinApiTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static Settings settings;

    public BitcodinApiTest() throws FileNotFoundException {
        BitcodinApiTest.settings = Settings.getInstance();
    }


    @Test
    public void runJobTestsParallel() throws BitcodinApiException {
        Class[] cls={CreateJob.class, CreateLocationJob.class, CreateWidevineJob.class, CreatePlayreadyJob.class, CreateCombinedDrmJob.class, CreateMultipleAudioStreamJob.class, CreateHlsEncryptionJob.class, NonBlockingTests.class };

        //Parallel among classes
        JUnitCore.runClasses(ParallelComputer.classes(), cls);
    }

    public static class CreateMultipleAudioStreamJob {
        @Test
        public void createMultipleAudioStreamJob() throws BitcodinApiException, InterruptedException {
            BitcodinApi bitApi = new BitcodinApi(BitcodinApiTest.settings.apikey);
            JobConfig jobConfig = BitcodinApiTest.createJobConfig();

            Input input = BitcodinApiTest.createSintelMultiAudioInput();
            jobConfig.inputId = input.inputId;

            AudioMetaData voicesOff = new AudioMetaData();
            voicesOff.defaultStreamId = 0;
            voicesOff.label = "Voices off";
            voicesOff.language = "audio";

            AudioMetaData voicesOn = new AudioMetaData();
            voicesOn.defaultStreamId = 1;
            voicesOn.label = "Voices on";
            voicesOn.language = "voice";

            jobConfig.audioMetaData = new AudioMetaData[]{voicesOff, voicesOn};
            jobConfig.speed = Speed.STANDARD;

            Job job = bitApi.createJob(jobConfig);

            assertEquals(JobStatus.ENQUEUED, job.status);
            BitcodinApiTest.waitTillJobIsFinished(job, bitApi);
        }
    }

    public static class CreateHlsEncryptionJob {
        @Test
        public void createHlsEncryptionJob() throws BitcodinApiException, InterruptedException {
            BitcodinApi bitApi = new BitcodinApi(BitcodinApiTest.settings.apikey);

            HlsEncryptionConfig hlsEncryptionConfig = new HlsEncryptionConfig();
            hlsEncryptionConfig.method = HlsMethod.SAMPLE_AES;
            hlsEncryptionConfig.key = "cab5b529ae28d5cc5e3e7bc3fd4a544d";

            JobConfig jobConfig = BitcodinApiTest.createJobConfig();
            jobConfig.speed = Speed.STANDARD;
            jobConfig.hlsEncryptionConfig = hlsEncryptionConfig;

            Job job = bitApi.createJob(jobConfig);
            assertEquals(JobStatus.ENQUEUED, job.status);

            BitcodinApiTest.waitTillJobIsFinished(job, bitApi);
        }

    }

    public static class CreateLocationJob {
        @Test
        public void createLocationJob() throws BitcodinApiException, InterruptedException {
            BitcodinApi bitApi = new BitcodinApi(BitcodinApiTest.settings.apikey);

            JobConfig jobConfig = BitcodinApiTest.createJobConfig();
            jobConfig.location = Location.EU_WEST;
            jobConfig.speed = Speed.STANDARD;

            Job job = bitApi.createJob(jobConfig);
            assertEquals(JobStatus.ENQUEUED, job.status);

            BitcodinApiTest.waitTillJobIsFinished(job, bitApi);
        }
    }

    public static class CreatePlayreadyJob {
        @Test
        public void createPlayreadyJob() throws BitcodinApiException, InterruptedException {
            BitcodinApi bitApi = new BitcodinApi(BitcodinApiTest.settings.apikey);
            JobConfig jobConfig = BitcodinApiTest.createJobConfig();

            PlayReadyDrmConfig drmConfig = new PlayReadyDrmConfig();
            drmConfig.system = DrmSystem.PLAYREADY;
            drmConfig.keySeed = "XVBovsmzhP9gRIZxWfFta3VVRPzVEWmJsazEJ46I";
            drmConfig.laUrl = "http://playready.directtaps.net/pr/svc/rightsmanager.asmx";
            drmConfig.method = DrmMethod.MPEG_CENC;
            drmConfig.kid = "746573745f69645f4639465043304e4f";

            jobConfig.drmConfig = drmConfig;
            jobConfig.speed = Speed.STANDARD;

            Job job = bitApi.createJob(jobConfig);
            assertEquals(JobStatus.ENQUEUED, job.status);
            BitcodinApiTest.waitTillJobIsFinished(job, bitApi);
        }



    }

    public static class CreateCombinedDrmJob {
        @Test
        public void createCombinedDrmJob() throws BitcodinApiException, InterruptedException {
            BitcodinApi bitApi = new BitcodinApi(BitcodinApiTest.settings.apikey);
            JobConfig jobConfig = BitcodinApiTest.createJobConfig();

            CombinedDrmConfig drmConfig = new CombinedDrmConfig();
            drmConfig.kid = "eb676abbcb345e96bbcf616630f1a3da";
            drmConfig.key = "100b6c20940f779a4589152b57d2dacb";
            drmConfig.laUrl = "http://playready.directtaps.net/pr/svc/rightsmanager.asmx?PlayRight=1&ContentKey=EAtsIJQPd5pFiRUrV9Layw==";
            drmConfig.pssh = "#CAESEOtnarvLNF6Wu89hZjDxo9oaDXdpZGV2aW5lX3Rlc3QiEGZrajNsamFTZGZhbGtyM2oqAkhEMgA=";

            jobConfig.drmConfig = drmConfig;
            jobConfig.speed = Speed.STANDARD;

            Job job = bitApi.createJob(jobConfig);
            assertEquals(JobStatus.ENQUEUED, job.status);
            BitcodinApiTest.waitTillJobIsFinished(job, bitApi);
        }
    }

    public static class CreateJob {
        @Test
        public void createJob() throws BitcodinApiException, InterruptedException {
            BitcodinApi bitApi = new BitcodinApi(BitcodinApiTest.settings.apikey);
            JobConfig jobConfig = BitcodinApiTest.createJobConfig();
            Job job = bitApi.createJob(jobConfig);

            assertEquals(JobStatus.ENQUEUED, job.status);
            BitcodinApiTest.waitTillJobIsFinished(job, bitApi);
        }
    }

    public static class CreateWidevineJob {
        @Test
        public void createWidevineJob() throws BitcodinApiException, InterruptedException {
            BitcodinApi bitApi = new BitcodinApi(BitcodinApiTest.settings.apikey);
            JobConfig jobConfig = BitcodinApiTest.createJobConfig();

            WidevineDrmConfig drmConfig = new WidevineDrmConfig();
            drmConfig.provider = "widevine_test";
            drmConfig.signingKey = "1ae8ccd0e7985cc0b6203a55855a1034afc252980e970ca90e5202689f947ab9";
            drmConfig.signingIV = "d58ce954203b7c9a9a9d467f59839249";
            drmConfig.requestUrl = "http://license.uat.widevine.com/cenc/getcontentkey";
            drmConfig.contentId = "746573745f69645f4639465043304e4f";

            jobConfig.drmConfig = drmConfig;
            jobConfig.speed = Speed.STANDARD;

            Job job = bitApi.createJob(jobConfig);
            assertEquals(JobStatus.ENQUEUED, job.status);
            BitcodinApiTest.waitTillJobIsFinished(job, bitApi);
        }
    }

    public static class NonBlockingTests {
        @Test(expected=BitcodinApiException.class)
        public void testApiInvalidKey() throws BitcodinApiException {
            BitcodinApi bitApi = new BitcodinApi("THIS_IS_AN_INVALID_KEY");

            bitApi.listInputs(0);
        }

        @Test(expected=BitcodinApiException.class)
        public void createInvalidInput() throws BitcodinApiException {
            BitcodinApi bitApi = new BitcodinApi(BitcodinApiTest.settings.apikey);
            HTTPInputConfig httpInputConfig = new HTTPInputConfig();
            httpInputConfig.url = "http://this/is/an/invalid/url.mkv";

            bitApi.createInput(httpInputConfig);
        }

        @Test(expected=BitcodinApiException.class)
        public void createInvalidS3Output() throws BitcodinApiException {
            BitcodinApi bitApi = new BitcodinApi(BitcodinApiTest.settings.apikey);

            S3OutputConfig s3OutputConfig = new S3OutputConfig();
            s3OutputConfig.accessKey = "THIS_IS_AN_INVALID_ACCESS_KEY";
            s3OutputConfig.secretKey = "THIS_IS_AN_INVALID_SECRET_KEY";
            s3OutputConfig.bucket = "INVALID_BUCKET";
            s3OutputConfig.region = S3Region.EU_WEST_1;
            s3OutputConfig.name = "INVALID_S3_OUTPUT";

            bitApi.createS3Output(s3OutputConfig);
        }

        @Test(expected=BitcodinApiException.class)
        public void createInvalidFTPOutput() throws BitcodinApiException {
            BitcodinApi bitApi = new BitcodinApi(BitcodinApiTest.settings.apikey);

            FTPOutputConfig ftpOutputConfig = new FTPOutputConfig();
            ftpOutputConfig.name = "INVALID_FTP_OUTPUT";
            ftpOutputConfig.host = "invalid.host.com";
            ftpOutputConfig.username = "INVALID_USER";
            ftpOutputConfig.password = "INVALID_PASSWORD";

            bitApi.createFTPOutput(ftpOutputConfig);
        }

        @Test(expected=BitcodinApiException.class)
        public void createInvalidEncodingProfile() throws BitcodinApiException {
            VideoStreamConfig videoConfig = new VideoStreamConfig();
            videoConfig.bitrate = -1;
            videoConfig.width = -1;
            videoConfig.height = -1;
            videoConfig.profile = Profile.MAIN;
            videoConfig.preset = Preset.STANDARD;

            EncodingProfileConfig encodingProfileConfig = new EncodingProfileConfig();
            encodingProfileConfig.name = "Invalid_Profile";
            encodingProfileConfig.videoStreamConfigs.add(videoConfig);

            BitcodinApi bitApi = new BitcodinApi(BitcodinApiTest.settings.apikey);

            bitApi.createEncodingProfile(encodingProfileConfig);
        }

        @Test
        public void testApiKeyGetter() {
            BitcodinApi bitApi = new BitcodinApi(BitcodinApiTest.settings.apikey);
            assertEquals(bitApi.getKey(), BitcodinApiTest.settings.apikey);
        }

        @Test
        public void createInput() throws BitcodinApiException {
            Input input = BitcodinApiTest.createSintelInput();

            assertEquals("Sintel-original-short.mkv", input.filename);
            assertEquals(2, input.mediaConfigurations.size());
            assertEquals(1920, input.mediaConfigurations.get(0).width);
            assertEquals(818, input.mediaConfigurations.get(0).height);
            assertEquals(InputType.URL, InputType.URL);
        }

        @Test
        public void createAzureInput() throws BitcodinApiException {
            BitcodinApi bitApi = new BitcodinApi(BitcodinApiTest.settings.apikey);
            AzureOutputConfig config = BitcodinApiTest.settings.azureOutput;

            AzureInputConfig inputConfig = new AzureInputConfig();
            inputConfig.accountKey = config.accountKey;
            inputConfig.accountName = config.accountName;
            inputConfig.url = "http://bitblobstorage.blob.core.windows.net/php-api-wrapper/Sintel-original-short.mkv";
            Input azureInput = bitApi.createAzureInput(inputConfig);

            assertEquals(InputType.ABS, azureInput.inputType);
        }

        @Test
        public void listInputs() throws BitcodinApiException {
            BitcodinApi bitApi = new BitcodinApi(BitcodinApiTest.settings.apikey);
            Input input = BitcodinApiTest.createSintelInput();
            InputList inputList = bitApi.listInputs(0);
            Input lastRecentInput = inputList.inputs.get(0);

            assertEquals(input.filename, lastRecentInput.filename);
            assertEquals(input.inputId, lastRecentInput.inputId);
        }

        @Test
        public void getInput() throws BitcodinApiException {
            BitcodinApi bitApi = new BitcodinApi(BitcodinApiTest.settings.apikey);
            Input input = BitcodinApiTest.createSintelInput();
            Input sameInput = bitApi.getInput(input.inputId);

            assertEquals(sameInput.filename, input.filename);
            assertEquals(sameInput.inputId, input.inputId);
        }

        @Test(expected=BitcodinApiException.class)
        public void deleteInput() throws BitcodinApiException {
            BitcodinApi bitApi = new BitcodinApi(BitcodinApiTest.settings.apikey);
            Input input = BitcodinApiTest.createSintelInput();

            bitApi.deleteInput(input.inputId);
            bitApi.getInput(input.inputId);
        }

        @Test
        public void createAzureOuptput() throws BitcodinApiException {
            BitcodinApi bitApi = new BitcodinApi(BitcodinApiTest.settings.apikey);
            Output output = bitApi.createAzureOutput(BitcodinApiTest.settings.azureOutput);

            assertEquals(OutputType.AZURE, output.type);
        }

        @Test
        public void createS3Output() throws BitcodinApiException {
            BitcodinApi bitApi = new BitcodinApi(BitcodinApiTest.settings.apikey);
            Output output = bitApi.createS3Output(BitcodinApiTest.settings.s3OutputEUWest);

            assertEquals(OutputType.S3, output.type);
        }

        @Test
        public void createFTPOutput() throws BitcodinApiException {
            BitcodinApi bitApi = new BitcodinApi(BitcodinApiTest.settings.apikey);
            Output output = bitApi.createFTPOutput(BitcodinApiTest.settings.ftpOutput);

            assertEquals(OutputType.FTP, output.type);
        }

        @Test
        public void listOutputs() throws BitcodinApiException {
            BitcodinApi bitApi = new BitcodinApi(BitcodinApiTest.settings.apikey);
            Output output = bitApi.createFTPOutput(BitcodinApiTest.settings.ftpOutput);
            OutputList outputList = bitApi.listOutputs(0);
            Output lastRecentOutput = outputList.outputs.get(0);

            assertEquals(output.outputId, lastRecentOutput.outputId);
        }

        @Test
        public void getOutput() throws BitcodinApiException {
            BitcodinApi bitApi = new BitcodinApi(BitcodinApiTest.settings.apikey);
            Output output = bitApi.createFTPOutput(BitcodinApiTest.settings.ftpOutput);
            Output sameOutput = bitApi.getOutput(output.outputId);

            assertEquals(sameOutput.name, output.name);
            assertEquals(sameOutput.outputId, output.outputId);
        }

        @Test(expected=BitcodinApiException.class)
        public void deleteOutput() throws BitcodinApiException {
            BitcodinApi bitApi = new BitcodinApi(BitcodinApiTest.settings.apikey);
            Output output = bitApi.createFTPOutput(BitcodinApiTest.settings.ftpOutput);

            bitApi.deleteOutput(output.outputId);
            bitApi.getOutput(output.outputId);
        }

        @Test
        public void createEncodingProfile() throws BitcodinApiException {
            BitcodinApi bitApi = new BitcodinApi(BitcodinApiTest.settings.apikey);
            EncodingProfileConfig config = BitcodinApiTest.createEncodingProfileConfig();
            EncodingProfile encodingProfile = bitApi.createEncodingProfile(config);

            assertEquals(config.videoStreamConfigs.get(0).width, encodingProfile.videoStreamConfigs.get(0).width);
            assertEquals(config.videoStreamConfigs.get(0).height, encodingProfile.videoStreamConfigs.get(0).height);
            assertEquals(config.audioStreamConfigs.get(0).bitrate, encodingProfile.audioStreamConfigs.get(0).bitrate);
        }

        @Test
        public void listEncodingProfiles() throws BitcodinApiException {
            BitcodinApi bitApi = new BitcodinApi(BitcodinApiTest.settings.apikey);
            EncodingProfileConfig config = BitcodinApiTest.createEncodingProfileConfig();
            EncodingProfile encodingProfile = bitApi.createEncodingProfile(config);
            EncodingProfileList encodingProfileList = bitApi.listEncodingProfiles(0);
            EncodingProfile lastRecentProfile = encodingProfileList.profiles.get(0);

            assertEquals(encodingProfile.encodingProfileId, lastRecentProfile.encodingProfileId);
        }

        @Test
        public void getEncodingProfile() throws BitcodinApiException {
            BitcodinApi bitApi = new BitcodinApi(BitcodinApiTest.settings.apikey);
            EncodingProfileConfig config = BitcodinApiTest.createEncodingProfileConfig();
            EncodingProfile encodingProfile = bitApi.createEncodingProfile(config);
            EncodingProfile sameProfile = bitApi.getEncodingProfile(encodingProfile.encodingProfileId);

            assertEquals(encodingProfile.name, sameProfile.name);
            assertEquals(encodingProfile.encodingProfileId, sameProfile.encodingProfileId);
        }

        @Test
        public void listJobs() throws BitcodinApiException {
            BitcodinApi bitApi = new BitcodinApi(BitcodinApiTest.settings.apikey);
            JobConfig jobConfig = BitcodinApiTest.createJobConfig();
            jobConfig.speed = Speed.STANDARD;

            Job job = bitApi.createJob(jobConfig);
            JobList jobList = bitApi.listJobs(0);
            JobDetails lastRecentJob = jobList.jobs.get(0);

            assertEquals(job.jobId, lastRecentJob.jobId);
        }

        @Test
        public void getJob() throws BitcodinApiException {
            BitcodinApi bitApi = new BitcodinApi(BitcodinApiTest.settings.apikey);
            JobConfig jobConfig = BitcodinApiTest.createJobConfig();
            jobConfig.speed = Speed.STANDARD;

            Job job = bitApi.createJob(jobConfig);
            JobDetails sameJob = bitApi.getJobDetails(job.jobId);

            assertEquals(job.jobId, sameJob.jobId);
        }

        @Test
        public void transferToS3() throws BitcodinApiException {
            BitcodinApi bitApi = new BitcodinApi(BitcodinApiTest.settings.apikey);
            Output s3Output = bitApi.createS3Output(BitcodinApiTest.settings.s3OutputEUWest);

            BitcodinApiTest.transfer(s3Output);
        }

        @Test
        public void transferToFTP() throws BitcodinApiException {
            BitcodinApi bitApi = new BitcodinApi(BitcodinApiTest.settings.apikey);
            Output ftpOutput = bitApi.createFTPOutput(BitcodinApiTest.settings.ftpOutput);

            BitcodinApiTest.transfer(ftpOutput);
        }

        @Test
        public void transferToAzure() throws BitcodinApiException {
            BitcodinApi bitApi = new BitcodinApi(BitcodinApiTest.settings.apikey);
            Output azureOutput = bitApi.createAzureOutput(BitcodinApiTest.settings.azureOutput);

            BitcodinApiTest.transfer(azureOutput);
        }

        @Test
        public void listTransfers() throws BitcodinApiException {
        /*
         * TODO cannot effectively be implemented without API fix so that
         * transfer returns at least id
         */
        }

        @Test
        public void getStatistics() throws BitcodinApiException {
            BitcodinApi bitApi = new BitcodinApi(BitcodinApiTest.settings.apikey);
            MonthlyStatistic stats = bitApi.getMonthlyStatistics();

            assertNotNull(stats);
        /*
         * TODO values are not designed very well, e.g., totalBytesWritten return GB?
         */
        }

        @Test
        public void getStatisticsFromTo() throws BitcodinApiException {
            BitcodinApi bitApi = new BitcodinApi(BitcodinApiTest.settings.apikey);
            Statistic stats = bitApi.getStatistics("2015-06-21", "2015-06-23");
        /*
         * TODO Range is not working -> fix in API
         */
        }
    }


    public static Input createSintelInput() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(BitcodinApiTest.settings.apikey);
        HTTPInputConfig httpInputConfig = new HTTPInputConfig();
        httpInputConfig.url = "http://bitbucketireland.s3.amazonaws.com/Sintel-original-short.mkv";

        return bitApi.createInput(httpInputConfig);
    }

    public static Input createSintelMultiAudioInput() throws  BitcodinApiException{
        BitcodinApi bitApi = new BitcodinApi(BitcodinApiTest.settings.apikey);
        HTTPInputConfig httpInputConfig = new HTTPInputConfig();
        httpInputConfig.url = "http://bitbucketireland.s3.amazonaws.com/Sintel-two-audio-streams-short.mkv";

        return bitApi.createInput(httpInputConfig);
    }



    public static EncodingProfileConfig createEncodingProfileConfig() {
        VideoStreamConfig videoConfig = new VideoStreamConfig();
        videoConfig.bitrate = 1024 * 1024;
        videoConfig.width = 640;
        videoConfig.height = 480;
        videoConfig.profile = Profile.MAIN;
        videoConfig.preset = Preset.STANDARD;
        
        AudioStreamConfig audioConfig = new AudioStreamConfig();
        audioConfig.defaultStreamId = 0;
        audioConfig.bitrate = 128 * 1024;

        EncodingProfileConfig encodingProfileConfig = new EncodingProfileConfig();
        encodingProfileConfig.name = "JUnitTestProfile";
        encodingProfileConfig.videoStreamConfigs.add(videoConfig);
        encodingProfileConfig.audioStreamConfigs.add(audioConfig);

        return encodingProfileConfig;
    }



    public static JobConfig createJobConfig() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(BitcodinApiTest.settings.apikey);
        JobConfig jobConfig = new JobConfig();
        EncodingProfileConfig config = BitcodinApiTest.createEncodingProfileConfig();
        EncodingProfile encodingProfile = bitApi.createEncodingProfile(config);
        Input input = BitcodinApiTest.createSintelInput();

        jobConfig.encodingProfileId = encodingProfile.encodingProfileId;
        jobConfig.inputId = input.inputId;
        jobConfig.manifestTypes.addElement(ManifestType.MPEG_DASH_MPD);
        jobConfig.manifestTypes.addElement(ManifestType.HLS_M3U8);

        return jobConfig;
    }

    public static void transfer(Output output) throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(BitcodinApiTest.settings.apikey);
        JobList jobList = bitApi.listJobs(0);

        JobDetails finishedJob = null;
        for (JobDetails job : jobList.jobs) {
            if (job.status == JobStatus.FINISHED) {
                finishedJob = job;
                break;
            }
        }

        assertNotNull(finishedJob);

        TransferConfig transferConfig = new TransferConfig();

        transferConfig.jobId = finishedJob.jobId;
        transferConfig.outputId = output.outputId;

        bitApi.transfer(transferConfig);
    }



    static void waitTillJobIsFinished(Job job, BitcodinApi bitApi) throws BitcodinApiException, InterruptedException {

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, 15);
        JobDetails jobDetails;

        do {
            jobDetails = bitApi.getJobDetails(job.jobId);

            Date now = new Date();
            if (jobDetails.status == JobStatus.ERROR) {
                fail("Job Failed!");
            }
            else if(cal.getTime().getTime() < now.getTime()) {
                fail("Job took too long!");
            }

            Thread.sleep(2000);

        } while (jobDetails.status != JobStatus.FINISHED);

        assertEquals(JobStatus.FINISHED, jobDetails.status);
    }
}
