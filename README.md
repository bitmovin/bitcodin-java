# [![bitcodin](http://www.bitcodin.com/wp-content/uploads/2014/10/bitcodin-small.gif)](http://www.bitcodin.com)
[![build status](https://travis-ci.org/bitmovin/bitcodin-java.svg)](https://travis-ci.org/bitmovin/bitcodin-java) 
[![Coverage Status](https://coveralls.io/repos/bitmovin/bitcodin-java/badge.svg?branch=master)](https://coveralls.io/r/bitmovin/bitcodin-java?branch=master)

The bitcodin API for Java is a seamless integration with the [bitcodin cloud transcoding system](http://www.bitcodin.com). It enables the generation of MPEG-DASH and HLS content in just some minutes.

# Getting started
First add the library (jar) to your project. The main interaction with bitcodin will be handled through the BitcodinApi class. Therefore instantiate an object with your API key, which can be found in the settings of your bitcodin user account, as shown in the figure below.

![APIKey](http://www.bitcodin.com/wp-content/uploads/2015/06/api_key.png)

An example how you can instantiate the bitcodin API is shown in the following:

```java
import com.bitmovin.bitcodin.api.BitcodinApi;

public class BitcodinApiTest {
    public static void main(String[] args) {
        BitcodinApi bitApi = new BitcodinApi("THISISMYAPIKEY");
    }
}
```
# Example
The following example demonstrates how to create a simple transcoding job.
```java
import com.bitmovin.bitcodin.api.BitcodinApi;
import com.bitmovin.bitcodin.api.exception.BitcodinApiException;
import com.bitmovin.bitcodin.api.input.HTTPInputConfig;
import com.bitmovin.bitcodin.api.input.Input;
import com.bitmovin.bitcodin.api.job.Job;
import com.bitmovin.bitcodin.api.job.JobConfig;
import com.bitmovin.bitcodin.api.job.ManifestType;
import com.bitmovin.bitcodin.api.media.EncodingProfile;
import com.bitmovin.bitcodin.api.media.EncodingProfileConfig;
import com.bitmovin.bitcodin.api.media.Preset;
import com.bitmovin.bitcodin.api.media.Profile;
import com.bitmovin.bitcodin.api.media.VideoStreamConfig;

public class TranscodeSintelToDASHAndHLS {

    public static void main(String[] args) {
        
        /* Create BitcodinApi */
        String apiKey = "YOUR_API_KEY";
        BitcodinApi bitApi = new BitcodinApi(apiKey);
        
        /* Create URL Input */
        HTTPInputConfig httpInputConfig = new HTTPInputConfig();
        httpInputConfig.url = "http://ftp.nluug.nl/pub/graphics/blender/demo/movies/Sintel.2010.720p.mkv";

        Input input;
        try {
            input = bitApi.createInput(httpInputConfig);
        } catch (BitcodinApiException e) {
            System.out.println("Could not create input: " + e.getMessage());
            return;
        }
        
        System.out.println("Created Input: " + input.filename);
        
        /* Create EncodingProfile */
        VideoStreamConfig videoConfig = new VideoStreamConfig();
        videoConfig.bitrate = 1 * 1024 * 1024;
        videoConfig.width = 640;
        videoConfig.height = 480;
        videoConfig.profile = Profile.MAIN;
        videoConfig.preset = Preset.STANDARD;

        EncodingProfileConfig encodingProfileConfig = new EncodingProfileConfig();
        encodingProfileConfig.name = "ExampleProfile";
        encodingProfileConfig.videoStreamConfigs.add(videoConfig);
        
        EncodingProfile encodingProfile;
        try {
            encodingProfile = bitApi.createEncodingProfile(encodingProfileConfig);
        } catch (BitcodinApiException e) {
            System.out.println("Could not create encoding profile: " + e.getMessage());
            return;
        }
        
        /* Create Job */
        JobConfig jobConfig = new JobConfig();
        jobConfig.encodingProfileId = encodingProfile.encodingProfileId;
        jobConfig.inputId = input.inputId;
        jobConfig.manifestTypes.addElement(ManifestType.MPEG_DASH_MPD);
        jobConfig.manifestTypes.addElement(ManifestType.HLS_M3U8);

        Job job;
        try {
            job = bitApi.createJob(jobConfig);
        } catch (BitcodinApiException e) {
            System.out.println("Could not create job: " + e.getMessage());
            return;
        }
        
        System.out.println("Job with ID " + job.jobId + " submitted successfully!");
    }
}
```

