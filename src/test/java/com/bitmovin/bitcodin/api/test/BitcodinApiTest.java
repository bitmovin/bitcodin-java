package com.bitmovin.bitcodin.api.test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

import com.bitmovin.bitcodin.api.BitcodinApi;
import com.bitmovin.bitcodin.api.exception.BitcodinApiException;
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
import com.bitmovin.bitcodin.api.output.OutputList;
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

    public Input createSintelInput() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        HTTPInputConfig httpInputConfig = new HTTPInputConfig();
        httpInputConfig.url = "http://ftp.nluug.nl/pub/graphics/blender/demo/movies/Sintel.2010.720p.mkv";
        
        return bitApi.createInput(httpInputConfig);
    }
    @Test
    public void createInput() throws BitcodinApiException {
        Input input = this.createSintelInput();

        assertEquals(input.filename, "Sintel.2010.720p.mkv");
        assertEquals(input.mediaConfigurations.size(), 2);
        assertEquals(input.mediaConfigurations.get(0).width, 1280);
        assertEquals(input.mediaConfigurations.get(0).height, 544);
    }

    @Test
    public void listInputs() throws BitcodinApiException {
        Input input = this.createSintelInput();
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        InputList inputList = bitApi.listInputs(0);

        Input lastRecentInput = inputList.inputs.get(0);
        
        assertEquals(lastRecentInput.filename, input.filename);
        assertEquals(lastRecentInput.inputId, input.inputId);
    }

    @Test
    public void getInput() throws BitcodinApiException {
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
    public void deleteInput() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        HTTPInputConfig httpInputConfig = new HTTPInputConfig();
        httpInputConfig.url = "http://ftp.nluug.nl/pub/graphics/blender/demo/movies/Sintel.2010.720p.mkv";
        Input input = bitApi.createInput(httpInputConfig);

        assertEquals(input.filename, "Sintel.2010.720p.mkv");
        assertEquals(input.mediaConfigurations.size(), 2);
        assertEquals(input.mediaConfigurations.get(0).width, 1280);
        assertEquals(input.mediaConfigurations.get(0).height, 544);

        bitApi.deleteInput(input.inputId);
        /* TODO: fix API input delete is not working */
        //assertNull(bitApi.getInput(input.inputId));
    }

    @Test
    public void createS3Output() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        bitApi.createS3Output(this.settings.s3OutputEUWest);
    }

    @Test
    public void createGCSOutput() throws BitcodinApiException {
        /* TODO */
    }

    @Test
    public void createFTPOutput() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        bitApi.createFTPOutput(this.settings.ftpOutput);
    }

    @Test
    public void listOutputs() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        OutputList outputList = bitApi.listOutputs(0);

        assertNotNull(outputList);
    }

    @Test
    public void getOutput() throws BitcodinApiException {
        /* TODO */
    }

    @Test
    public void deleteOutput() throws BitcodinApiException {
        /* TODO */
    }

    @Test
    public void createEncodingProfile() throws BitcodinApiException {
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
    public void listEncodingProfiles() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        EncodingProfileList encodingProfileList = bitApi.listEncodingProfiles(0);

        assertEquals(encodingProfileList.profiles.get(0).name, "JUnitTestProfile");
    }

    @Test
    public void getEncodingProfile() throws BitcodinApiException {
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
    public void createJob() throws BitcodinApiException {
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
    public void listJobs() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        JobList jobList = bitApi.listJobs(0);

        assertNotNull(jobList.jobs.get(0));
    }

    @Test
    public void getJob() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        Job job = bitApi.getJob(2471);

        assertEquals(job.jobId, 2471);
    }

    @Test
    public void transfer() throws BitcodinApiException {
        /* TODO */
    }

    @Test
    public void listTransfers() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        TransferList transferList = bitApi.listTransfers(6838);
    }

    @Test
    public void getStatistics() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        Statistic stats = bitApi.getStatistics();

        assertNotNull(stats);
    }

    @Test
    public void getStatisticsFromTo() throws BitcodinApiException {
        BitcodinApi bitApi = new BitcodinApi(this.settings.apikey);
        Statistic stats = bitApi.getStatistics("2015-06-01", "2015-06-10");
        assertNotNull(stats);
    }
}
