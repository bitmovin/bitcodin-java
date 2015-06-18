package com.bitmovin.bitcodin.api.test;

import static org.junit.Assert.*;
import org.junit.Test;

import com.bitmovin.bitcodin.api.BitcodinApi;

public class BitcodinApiTest {

    @Test
    public void testApiKeyGetter() {
        
        String key = "THIS_IS_MY_KEY";
        BitcodinApi bitApi = new BitcodinApi(key);

        assertEquals(key, bitApi.getKey());
    }
    
    @Test
    public void createInput() {
        
        BitcodinApi bitApi = new BitcodinApi("THIS_IS_MY_KEY");
        
        System.out.println(bitApi.createInput("http://ftp.nluug.nl/pub/graphics/blender/demo/movies/Sintel.2010.720p.mkv"));
    }

}
