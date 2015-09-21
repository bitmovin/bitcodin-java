package com.bitmovin.bitcodin.api.test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import com.bitmovin.bitcodin.api.input.*;
import com.bitmovin.bitcodin.api.job.*;
import com.bitmovin.bitcodin.api.output.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.bitmovin.bitcodin.api.BitcodinApi;
import com.bitmovin.bitcodin.api.exception.BitcodinApiException;
import com.bitmovin.bitcodin.api.media.AudioStreamConfig;
import com.bitmovin.bitcodin.api.media.EncodingProfile;
import com.bitmovin.bitcodin.api.media.EncodingProfileConfig;
import com.bitmovin.bitcodin.api.media.EncodingProfileList;
import com.bitmovin.bitcodin.api.media.Preset;
import com.bitmovin.bitcodin.api.media.Profile;
import com.bitmovin.bitcodin.api.media.VideoStreamConfig;
import com.bitmovin.bitcodin.api.statistics.MonthlyStatistic;
import com.bitmovin.bitcodin.api.statistics.Statistic;
import com.bitmovin.bitcodin.api.transfer.TransferConfig;

public class BitcodinApiTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Settings settings;

    public BitcodinApiTest() throws FileNotFoundException {
        this.settings = Settings.getInstance();
    }

    @Test
    public void testApiInvalidKey() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi("THIS_IS_AN_INVALID_KEY");

        thrown.expect(BitcodinApiException.class);
        bitApi.listInputs(0);
    }

    @Test
    public void createInvalidInput() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        HTTPInputConfig httpInputConfig = new HTTPInputConfig();
        httpInputConfig.url = "http://this/is/an/invalid/url.mkv";

        thrown.expect(BitcodinApiException.class);
        bitApi.createInput(httpInputConfig);
    }

    @Test
    public void createInvalidS3Output() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);

        S3OutputConfig s3OutputConfig = new S3OutputConfig();
        s3OutputConfig.accessKey = "THIS_IS_AN_INVALID_ACCESS_KEY";
        s3OutputConfig.secretKey = "THIS_IS_AN_INVALID_SECRET_KEY";
        s3OutputConfig.bucket = "INVALID_BUCKET";
        s3OutputConfig.region = S3Region.EU_WEST_1;
        s3OutputConfig.name = "INVALID_S3_OUTPUT";

        thrown.expect(BitcodinApiException.class);
        bitApi.createS3Output(s3OutputConfig);
    }
    
    @Test
    public void createInvalidFTPOutput() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);

        FTPOutputConfig ftpOutputConfig = new FTPOutputConfig();
        ftpOutputConfig.name = "INVALID_FTP_OUTPUT";
        ftpOutputConfig.host = "invalid.host.com";
        ftpOutputConfig.username = "INVALID_USER";
        ftpOutputConfig.password = "INVALID_PASSWORD";

        thrown.expect(BitcodinApiException.class);
        bitApi.createFTPOutput(ftpOutputConfig);
    }
    
    @Test
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
        
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);

        thrown.expect(BitcodinApiException.class);
        bitApi.createEncodingProfile(encodingProfileConfig);
    }

    @Test
    public void testApiKeyGetter() {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        assertEquals(bitApi.getKey(), this.settings.apikey);
    }

    public Input createSintelInput() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        HTTPInputConfig httpInputConfig = new HTTPInputConfig();
        httpInputConfig.url = "http://bitbucketireland.s3.amazonaws.com/Sintel-original-short.mkv";

        return bitApi.createInput(httpInputConfig);
    }

    public Input createSintelMultiAudioInput() throws  BitcodinApiException{
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        HTTPInputConfig httpInputConfig = new HTTPInputConfig();
        httpInputConfig.url = "http://bitbucketireland.s3.amazonaws.com/Sintel-two-audio-streams-short.mkv";

        return bitApi.createInput(httpInputConfig);
    }

    @Test
    public void createInput() throws BitcodinApiException {
        Input input = this.createSintelInput();

        assertEquals("Sintel-original-short.mkv", input.filename);
        assertEquals(2, input.mediaConfigurations.size());
        assertEquals(1920, input.mediaConfigurations.get(0).width);
        assertEquals(818, input.mediaConfigurations.get(0).height);
        assertEquals(InputType.URL, InputType.URL);
    }

    @Test
    public void createAzureInput() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        AzureOutputConfig config = this.settings.azureOutput;

        AzureInputConfig inputConfig = new AzureInputConfig();
        inputConfig.accountKey = config.accountKey;
        inputConfig.accountName = config.accountName;
        inputConfig.url = "http://bitblobstorage.blob.core.windows.net/php-api-wrapper/Sintel-original-short.mkv";
        Input azureInput = bitApi.createAzureInput(inputConfig);

        assertEquals(InputType.ABS, azureInput.inputType);
    }

    @Test
    public void listInputs() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        Input input = this.createSintelInput();
        InputList inputList = bitApi.listInputs(0);
        Input lastRecentInput = inputList.inputs.get(0);

        assertEquals(input.filename, lastRecentInput.filename);
        assertEquals(input.inputId, lastRecentInput.inputId);
    }

    @Test
    public void getInput() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        Input input = this.createSintelInput();
        Input sameInput = bitApi.getInput(input.inputId);

        assertEquals(sameInput.filename, input.filename);
        assertEquals(sameInput.inputId, input.inputId);
    }

    @Test
    public void deleteInput() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        Input input = this.createSintelInput();

        bitApi.deleteInput(input.inputId);
        
        thrown.expect(BitcodinApiException.class);
        bitApi.getInput(input.inputId);
    }

    @Test
    public void createAzureOuptput() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        Output output = bitApi.createAzureOutput(this.settings.azureOutput);

        assertEquals(OutputType.AZURE, output.type);
    }

    @Test
    public void createS3Output() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        Output output = bitApi.createS3Output(this.settings.s3OutputEUWest);

        assertEquals(OutputType.S3, output.type);
    }

    @Test
    public void createFTPOutput() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        Output output = bitApi.createFTPOutput(this.settings.ftpOutput);

        assertEquals(OutputType.FTP, output.type);
    }

    @Test
    public void listOutputs() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        Output output = bitApi.createFTPOutput(this.settings.ftpOutput);
        OutputList outputList = bitApi.listOutputs(0);
        Output lastRecentOutput = outputList.outputs.get(0);

        assertEquals(output.outputId, lastRecentOutput.outputId);
    }

    @Test
    public void getOutput() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        Output output = bitApi.createFTPOutput(this.settings.ftpOutput);
        Output sameOutput = bitApi.getOutput(output.outputId);

        assertEquals(sameOutput.name, output.name);
        assertEquals(sameOutput.outputId, output.outputId);
    }

    @Test
    public void deleteOutput() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        Output output = bitApi.createFTPOutput(this.settings.ftpOutput);

        bitApi.deleteOutput(output.outputId);

        thrown.expect(BitcodinApiException.class);
        bitApi.getOutput(output.outputId);
    }

    public EncodingProfileConfig createEncodingProfileConfig() {
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

    @Test
    public void createEncodingProfile() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        EncodingProfileConfig config = this.createEncodingProfileConfig();
        EncodingProfile encodingProfile = bitApi.createEncodingProfile(config);

        assertEquals(config.videoStreamConfigs.get(0).width, encodingProfile.videoStreamConfigs.get(0).width);
        assertEquals(config.videoStreamConfigs.get(0).height, encodingProfile.videoStreamConfigs.get(0).height);
        assertEquals(config.audioStreamConfigs.get(0).bitrate, encodingProfile.audioStreamConfigs.get(0).bitrate);
    }

    @Test
    public void listEncodingProfiles() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        EncodingProfileConfig config = this.createEncodingProfileConfig();
        EncodingProfile encodingProfile = bitApi.createEncodingProfile(config);
        EncodingProfileList encodingProfileList = bitApi.listEncodingProfiles(0);
        EncodingProfile lastRecentProfile = encodingProfileList.profiles.get(0);

        assertEquals(encodingProfile.encodingProfileId, lastRecentProfile.encodingProfileId);
    }

    @Test
    public void getEncodingProfile() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        EncodingProfileConfig config = this.createEncodingProfileConfig();
        EncodingProfile encodingProfile = bitApi.createEncodingProfile(config);
        EncodingProfile sameProfile = bitApi.getEncodingProfile(encodingProfile.encodingProfileId);

        assertEquals(encodingProfile.name, sameProfile.name);
        assertEquals(encodingProfile.encodingProfileId, sameProfile.encodingProfileId);
    }

    public JobConfig createJobConfig() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        JobConfig jobConfig = new JobConfig();
        EncodingProfileConfig config = this.createEncodingProfileConfig();
        EncodingProfile encodingProfile = bitApi.createEncodingProfile(config);
        Input input = this.createSintelInput();

        jobConfig.encodingProfileId = encodingProfile.encodingProfileId;
        jobConfig.inputId = input.inputId;
        jobConfig.manifestTypes.addElement(ManifestType.MPEG_DASH_MPD);
        jobConfig.manifestTypes.addElement(ManifestType.HLS_M3U8);

        return jobConfig;
    }

    @Test
    public void createJob() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        JobConfig jobConfig = this.createJobConfig();
        Job job = bitApi.createJob(jobConfig);

        assertEquals(JobStatus.ENQUEUED, job.status);
    }

    @Test
    public void createWidevineJob() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        JobConfig jobConfig = this.createJobConfig();

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
    }

    @Test
    public void createPlayreadyJob() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        JobConfig jobConfig = this.createJobConfig();

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
    }

    @Test
    public void createCombinedDrmJob() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        JobConfig jobConfig = this.createJobConfig();

        CombinedDrmConfig drmConfig = new CombinedDrmConfig();
        drmConfig.kid = "eb676abbcb345e96bbcf616630f1a3da";
        drmConfig.key = "100b6c20940f779a4589152b57d2dacb";
        drmConfig.laUrl = "http://playready.directtaps.net/pr/svc/rightsmanager.asmx?PlayRight=1&ContentKey=EAtsIJQPd5pFiRUrV9Layw==";
        drmConfig.pssh = "#CAESEOtnarvLNF6Wu89hZjDxo9oaDXdpZGV2aW5lX3Rlc3QiEGZrajNsamFTZGZhbGtyM2oqAkhEMgA=";

        jobConfig.drmConfig = drmConfig;
        jobConfig.speed = Speed.STANDARD;

        Job job = bitApi.createJob(jobConfig);
        assertEquals(JobStatus.ENQUEUED, job.status);
    }

    @Test
    public void createMultipleAudioStreamJob() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        JobConfig jobConfig = this.createJobConfig();

        Input input = this.createSintelMultiAudioInput();
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
    }

    @Test
    public void createHlsEncryptionJob() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);

        HlsEncryptionConfig hlsEncryptionConfig = new HlsEncryptionConfig();
        hlsEncryptionConfig.method = HlsMethod.SAMPLE_AES;
        hlsEncryptionConfig.key = "cab5b529ae28d5cc5e3e7bc3fd4a544d";

        JobConfig jobConfig = this.createJobConfig();
        jobConfig.speed = Speed.STANDARD;
        jobConfig.hlsEncryptionConfig = hlsEncryptionConfig;

        Job job = bitApi.createJob(jobConfig);
        assertEquals(JobStatus.ENQUEUED, job.status);
    }

    @Test
    public void createLocationJob() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);

        JobConfig jobConfig = this.createJobConfig();
        jobConfig.location = Location.EU_WEST;
        jobConfig.speed = Speed.STANDARD;

        Job job = bitApi.createJob(jobConfig);
        assertEquals(JobStatus.ENQUEUED, job.status);
    }

    @Test
    public void listJobs() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        JobConfig jobConfig = this.createJobConfig();
        jobConfig.speed = Speed.STANDARD;

        Job job = bitApi.createJob(jobConfig);
        JobList jobList = bitApi.listJobs(0);
        JobDetails lastRecentJob = jobList.jobs.get(0);

        assertEquals(job.jobId, lastRecentJob.jobId);
    }

    @Test
    public void getJob() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        JobConfig jobConfig = this.createJobConfig();
        jobConfig.speed = Speed.STANDARD;

        Job job = bitApi.createJob(jobConfig);
        JobDetails sameJob = bitApi.getJobDetails(job.jobId);

        assertEquals(job.jobId, sameJob.jobId);
    }

    public void transfer(Output output) throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
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

    @Test
    public void transferToS3() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        Output s3Output = bitApi.createS3Output(this.settings.s3OutputEUWest);

        this.transfer(s3Output);
    }

    @Test
    public void transferToFTP() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        Output ftpOutput = bitApi.createFTPOutput(this.settings.ftpOutput);

        this.transfer(ftpOutput);
    }

    @Test
    public void transferToAzure() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        Output azureOutput = bitApi.createAzureOutput(this.settings.azureOutput);

        this.transfer(azureOutput);
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
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        MonthlyStatistic stats = bitApi.getMonthlyStatistics();

        assertNotNull(stats);
        /*
         * TODO values are not designed very well, e.g., totalBytesWritten return GB?
         */
    }

    @Test
    public void getStatisticsFromTo() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        Statistic stats = bitApi.getStatistics("2015-06-21", "2015-06-23");
        /*
         * TODO Range is not working -> fix in API
         */
    }
}
