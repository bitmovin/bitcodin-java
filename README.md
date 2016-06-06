# [![bitmovin](https://cloudfront-prod.bitmovin.com/wp-content/themes/Bitmovin-V-0.1/images/logo3.png)](http://www.bitmovin.com)
[![build status](https://travis-ci.org/bitmovin/bitcodin-java.svg)](https://travis-ci.org/bitmovin/bitcodin-java) 
[![Coverage Status](https://coveralls.io/repos/bitmovin/bitcodin-java/badge.svg?branch=master)](https://coveralls.io/r/bitmovin/bitcodin-java?branch=master)

The bitcodin API for Java is a seamless integration with the [bitmovin cloud transcoding service](http://www.bitmovin.com). It enables the generation of MPEG-DASH and HLS content in just some minutes.

# Getting started
## Maven
Add this to your pom.xml:
```xml
<dependency>
    <groupId>com.bitmovin.bitcodin.api</groupId>
    <artifactId>bitcodin-java</artifactId>
    <version>1.6.3</version>
</dependency>
```
## Simple Java Project
Just add the library (jar) of the [latest release](https://github.com/bitmovin/bitcodin-java/releases) to your project.
## Interaction with bitcodin
The main interaction with [bitmovin](http://www.bitmovin.com) will be handled through the BitcodinApi class. Therefore instantiate an object with your API key, which can be found in the settings of your bitcodin user account, as shown in the figure below.

![APIKey](https://cloudfront-prod.bitmovin.com/wp-content/uploads/2016/04/api-key.png)

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
The following example demonstrates how to create a simple transcoding job, generating MPEG-DASH and Apple HLS out of a single MP4.
```java
public class TranscodeSintelToDASHAndHLS {

    public static void main(String[] args) throws InterruptedException {
        
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
        videoConfig.preset = Preset.PREMIUM;

        EncodingProfileConfig encodingProfileConfig = new EncodingProfileConfig();
        encodingProfileConfig.name = "JUnitTestProfile";
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
        
        JobDetails jobDetails;
        
        do {
            try {
                jobDetails = bitApi.getJobDetails(job.jobId);
                System.out.println("Status: " + jobDetails.status.toString() +
                                   " - Enqueued Duration: " + jobDetails.enqueueDuration + "s" +
                                   " - Realtime Factor: " + jobDetails.realtimeFactor +
                                   " - Encoded Duration: " + jobDetails.encodedDuration + "s" +
                                   " - Output: " + jobDetails.bytesWritten/1024/1024 + "MB");
            } catch (BitcodinApiException e) {
                System.out.println("Could not get any job details");
                return;
            }
            
            if (jobDetails.status == JobStatus.ERROR) {
                System.out.println("Error during transcoding");
                return;
            }
            
            Thread.sleep(2000);
            
        } while (jobDetails.status != JobStatus.FINISHED);
        
        System.out.println("Job with ID " + job.jobId + " finished successfully!");
    }
}
```

