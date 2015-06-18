package com.bitmovin.bitcodin.api.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.bitmovin.bitcodin.api.BitcodinApi;
import com.bitmovin.bitcodin.api.FTPOutputConfig;
import com.bitmovin.bitcodin.api.GCSOutputConfig;
import com.bitmovin.bitcodin.api.Input;
import com.bitmovin.bitcodin.api.InputList;
import com.bitmovin.bitcodin.api.S3OutputConfig;

public class BitcodinApiTest {

    @Test
    public void testApiKeyGetter() {

        BitcodinApi bitApi = new BitcodinApi(Settings.apikey);
        assertEquals(Settings.apikey, bitApi.getKey());
    }
    @Test
    public void createInput() {

        BitcodinApi bitApi = new BitcodinApi(Settings.apikey);
        Input input = bitApi.createInput("http://ftp.nluug.nl/pub/graphics/blender/demo/movies/Sintel.2010.720p.mkv");
        
        assertEquals(input.filename, "Sintel.2010.720p.mkv");
        assertEquals(input.mediaConfigurations.size(), 2);
        assertEquals(input.mediaConfigurations.get(0).width, 1280);
        assertEquals(input.mediaConfigurations.get(0).height, 544);
    }
    @Test
    public void listInputs() {

        BitcodinApi bitApi = new BitcodinApi(Settings.apikey);
        InputList inputList = bitApi.listInputs(0);
        
        assertEquals(inputList.inputs.get(0).filename, "Sintel.2010.720p.mkv");
    }
    @Test
    public void getInput() {

        BitcodinApi bitApi = new BitcodinApi(Settings.apikey);
        Input input = bitApi.createInput("http://ftp.nluug.nl/pub/graphics/blender/demo/movies/Sintel.2010.720p.mkv");
        
        assertEquals(input.filename, "Sintel.2010.720p.mkv");
        assertEquals(input.mediaConfigurations.size(), 2);
        assertEquals(input.mediaConfigurations.get(0).width, 1280);
        assertEquals(input.mediaConfigurations.get(0).height, 544);
        
        Input sameInput = bitApi.getInput(input.inputId);
        
        assertEquals(input.filename, sameInput.filename);
    }
    @Test
    public void deleteInput() {

        BitcodinApi bitApi = new BitcodinApi(Settings.apikey);
        Input input = bitApi.createInput("http://ftp.nluug.nl/pub/graphics/blender/demo/movies/Sintel.2010.720p.mkv");
        
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

        BitcodinApi bitApi = new BitcodinApi(Settings.apikey);
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

        BitcodinApi bitApi = new BitcodinApi(Settings.apikey);
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

        BitcodinApi bitApi = new BitcodinApi(Settings.apikey);
        FTPOutputConfig ftpOutputConfig = new FTPOutputConfig();
        
        ftpOutputConfig.name = "OUTPUT_NAME";
        ftpOutputConfig.host = "YOUR_HOST";
        
        bitApi.createFTPOutput(ftpOutputConfig);
        
    }
}
