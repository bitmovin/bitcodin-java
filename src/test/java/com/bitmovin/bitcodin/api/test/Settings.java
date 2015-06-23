package com.bitmovin.bitcodin.api.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.bitmovin.bitcodin.api.output.FTPOutputConfig;
import com.bitmovin.bitcodin.api.output.S3OutputConfig;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

public class Settings {
    @Expose
    public String apikey;
    @Expose
    public S3OutputConfig s3OutputEUWest;
    @Expose
    public FTPOutputConfig ftpOutput;

    static private Settings settings = null;
    static private String settingsfile  = "settings.json";
    static private String localSettingsfile = "local_settings.json";
    
    static public Settings getInstance() throws FileNotFoundException
    {
        if (settings == null)
        {
            InputStream inputStream = Settings.class.getClassLoader().getResourceAsStream(Settings.settingsfile);
            
            if (inputStream == null)
            {
                inputStream = Settings.class.getClassLoader().getResourceAsStream(Settings.localSettingsfile);
                
                if (inputStream == null)
                  throw new FileNotFoundException("Could not find any config file: " + Settings.settingsfile);
            }

            BufferedReader  reader = new BufferedReader(new InputStreamReader(inputStream));
            Gson            gson   = new Gson();

            settings = gson.fromJson(reader, Settings.class);
        }
        
        return settings;
    }
}
