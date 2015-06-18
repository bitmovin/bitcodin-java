package com.bitmovin.bitcodin.api.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.bitmovin.bitcodin.api.BitcodinApi;

public class BitcodinApiTest {

    @Test
    public void testApiKeyGetter() {

        BitcodinApi bitApi = new BitcodinApi(Settings.apikey);
        assertEquals(Settings.apikey, bitApi.getKey());
    }

    @Test
    public void createInput() {

        BitcodinApi bitApi = new BitcodinApi(Settings.apikey);

        System.out.println(bitApi.createInput("http://ftp.nluug.nl/pub/graphics/blender/demo/movies/Sintel.2010.720p.mkv"));
    }

}
