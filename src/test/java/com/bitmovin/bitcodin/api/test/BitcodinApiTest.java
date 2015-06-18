package com.bitmovin.bitcodin.api.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.bitmovin.bitcodin.api.BitcodinApi;
import com.bitmovin.bitcodin.api.Input;
import com.bitmovin.bitcodin.api.InputList;

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

}
