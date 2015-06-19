package com.bitmovin.bitcodin.api.test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

import com.bitmovin.bitcodin.api.BitcodinApi;
import com.bitmovin.bitcodin.api.input.HTTPInputConfig;
import com.bitmovin.bitcodin.api.input.Input;
import com.bitmovin.bitcodin.api.input.InputList;
import com.bitmovin.bitcodin.api.job.Job;
import com.bitmovin.bitcodin.api.job.JobConfig;
import com.bitmovin.bitcodin.api.job.JobList;
import com.bitmovin.bitcodin.api.media.EncodingProfile;
import com.bitmovin.bitcodin.api.media.EncodingProfileConfig;
import com.bitmovin.bitcodin.api.media.EncodingProfileList;
import com.bitmovin.bitcodin.api.media.VideoStreamConfig;
import com.bitmovin.bitcodin.api.output.FTPOutputConfig;
import com.bitmovin.bitcodin.api.output.GCSOutputConfig;
import com.bitmovin.bitcodin.api.output.OutputList;
import com.bitmovin.bitcodin.api.output.S3OutputConfig;
import com.bitmovin.bitcodin.api.statistics.Statistic;
import com.bitmovin.bitcodin.api.transfer.TransferList;

public class BitcodinApiTest {

    private Settings settings;
    
    public BitcodinApiTest() throws FileNotFoundException {
        this.settings = Settings.getInstance();
    }
    
    @Test
    public void testApiKeyGetter() {

        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        assertEquals(this.settings.apikey, bitApi.getKey());
    }
    @Test
    public void createInput() {

        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        HTTPInputConfig httpInputConfig = new HTTPInputConfig();
        httpInputConfig.url = "http://ftp.nluug.nl/pub/graphics/blender/demo/movies/Sintel.2010.720p.mkv";
        Input input = bitApi.createInput(httpInputConfig);
        
        assertEquals(input.filename, "Sintel.2010.720p.mkv");
        assertEquals(input.mediaConfigurations.size(), 2);
        assertEquals(input.mediaConfigurations.get(0).width, 1280);
        assertEquals(input.mediaConfigurations.get(0).height, 544);
    }
    @Test
    public void listInputs() {

        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        InputList inputList = bitApi.listInputs(0);
        
        assertEquals(inputList.inputs.get(0).filename, "Sintel.2010.720p.mkv");
    }
    @Test
    public void getInput() {

        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        HTTPInputConfig httpInputConfig = new HTTPInputConfig();
        httpInputConfig.url = "http://ftp.nluug.nl/pub/graphics/blender/demo/movies/Sintel.2010.720p.mkv";
        Input input = bitApi.createInput(httpInputConfig);
        
        assertEquals(input.filename, "Sintel.2010.720p.mkv");
        assertEquals(input.mediaConfigurations.size(), 2);
        assertEquals(input.mediaConfigurations.get(0).width, 1280);
        assertEquals(input.mediaConfigurations.get(0).height, 544);
        
        Input sameInput = bitApi.getInput(input.inputId);
        
        assertEquals(input.filename, sameInput.filename);
    }
    @Test
    public void deleteInput() {

        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        HTTPInputConfig httpInputConfig = new HTTPInputConfig();
        httpInputConfig.url = "http://ftp.nluug.nl/pub/graphics/blender/demo/movies/Sintel.2010.720p.mkv";
        Input input = bitApi.createInput(httpInputConfig);
        
        assertEquals(input.filename, "Sintel.2010.720p.mkv");
        assertEquals(input.mediaConfigurations.size(), 2);
        assertEquals(input.mediaConfigurations.get(0).width, 1280);
        assertEquals(input.mediaConfigurations.get(0).height, 544);
        
        Input sameInput = bitApi.getInput(input.inputId);
        assertEquals(input.filename, sameInput.filename);
        
        bitApi.deleteInput(input.inputId);
        assertNull(bitApi.getInput(input.inputId));
    }
    @Test
    public void createS3Output() {

        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        S3OutputConfig s3OutputConfig = new S3OutputConfig();
        
        s3OutputConfig.accessKey = "YOUR_ACCESS_KEY";
        s3OutputConfig.secretKey = "YOUR_SECRET_KEY";
        s3OutputConfig.host = "s3-eu-west-1.amazonaws.com";
        s3OutputConfig.name = "OUTPUT_NAME";
        s3OutputConfig.bucket = "YOUR_BUCKET";
        s3OutputConfig.prefix = "DIRECTORY/SUBDIRECTORY";
        
        bitApi.createS3Output(s3OutputConfig);
        
    }
    @Test
    public void createGCSOutput() {

        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        GCSOutputConfig gcsOutputConfig = new GCSOutputConfig();
        
        gcsOutputConfig.accessKey = "YOUR_ACCESS_KEY";
        gcsOutputConfig.secretKey = "YOUR_SECRET_KEY";
        gcsOutputConfig.name = "OUTPUT_NAME";
        gcsOutputConfig.bucket = "YOUR_BUCKET";
        gcsOutputConfig.prefix = "DIRECTORY/SUBDIRECTORY";
        
        bitApi.createGCSOutput(gcsOutputConfig);
        
    }
    @Test
    public void createFTPOutput() {

        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        FTPOutputConfig ftpOutputConfig = new FTPOutputConfig();
        
        ftpOutputConfig.name = "OUTPUT_NAME";
        ftpOutputConfig.host = "YOUR_HOST";
        
        bitApi.createFTPOutput(ftpOutputConfig);
        
    }
    @Test
    public void listOutputs() {

        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        OutputList outputList = bitApi.listOutputs(0);
        
        assertNotNull(outputList);
    }
    @Test
    public void getOutput() {
        /*TODO*/
    }
    @Test
    public void deleteOutput() {
        /*TODO*/
    }
    @Test
    public void createEncodingProfile() {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        
        VideoStreamConfig videoConfig = new VideoStreamConfig();
        videoConfig.bitrate = 1 * 1024 * 1024;
        videoConfig.width = 640;
        videoConfig.height = 480;
        videoConfig.profile = "Main";
        videoConfig.preset = "Standard";
        
        EncodingProfileConfig encodingProfileConfig = new EncodingProfileConfig();
        encodingProfileConfig.name = "JUnitTestProfile";
        encodingProfileConfig.videoStreamConfigs.add(videoConfig);
        
        EncodingProfile encodingProfile = bitApi.createEncodingProfile(encodingProfileConfig);
        
        assertEquals(encodingProfile.videoStreamConfigs.get(0).width, 640);
        assertEquals(encodingProfile.videoStreamConfigs.get(0).height, 480);
    }
    @Test
    public void listEncodingProfiles() {

        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        EncodingProfileList encodingProfileList = bitApi.listEncodingProfiles(0);
        
        assertEquals(encodingProfileList.profiles.get(0).name, "JUnitTestProfile");
    }
    @Test
    public void getEncodingProfile() {

        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        
        VideoStreamConfig videoConfig = new VideoStreamConfig();
        videoConfig.bitrate = 8 * 1024 * 1024;
        videoConfig.width = 1920;
        videoConfig.height = 1080;
        videoConfig.profile = "Main";
        videoConfig.preset = "Standard";
        
        EncodingProfileConfig encodingProfileConfig = new EncodingProfileConfig();
        encodingProfileConfig.name = "JUnitTestProfile";
        encodingProfileConfig.videoStreamConfigs.add(videoConfig);
        
        EncodingProfile encodingProfile = bitApi.createEncodingProfile(encodingProfileConfig);
        
        assertEquals(encodingProfile.videoStreamConfigs.get(0).width, 1920);
        assertEquals(encodingProfile.videoStreamConfigs.get(0).height, 1080);
        
        EncodingProfile sameProfile = bitApi.getEncodingProfile(encodingProfile.encodingProfileId);
        
        assertEquals(sameProfile.name, encodingProfile.name);
    }
    @Test
    public void createJob() {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        JobConfig jobConfig = new JobConfig();
        jobConfig.encodingProfileId = 6938;
        jobConfig.inputId = 2765;
        jobConfig.manifestTypes.addElement("mpd");
        jobConfig.manifestTypes.addElement("m3u8");
        
        Job job = bitApi.createJob(jobConfig);
        
        assertEquals(job.status, "Enqueued");
    }
    @Test
    public void listJobs() {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        JobList jobList = bitApi.listJobs(0);
        
        assertNotNull(jobList.jobs.get(0));
    }
    @Test
    public void getJob() {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        Job job = bitApi.getJob(2471);
        
        assertEquals(job.jobId, 2471);
    }
    @Test
    public void transfer() {
        /*TODO*/
    }
    @Test
    public void listTransfers() {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        TransferList transferList = bitApi.listTransfers(6838);
    }
    @Test
    public void getStatistics() {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        Statistic stats = bitApi.getStatistics();
        
        assertNotNull(stats);
    }
    @Test
    public void getStatisticsFromTo() {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        Statistic stats = bitApi.getStatistics("2015-06-01", "2015-06-17");
        
        System.out.println(stats.jobCountFinished);
        
        assertNotNull(stats);
    }
}
