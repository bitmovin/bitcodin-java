# bitcodin-java
[![build status](https://travis-ci.org/bitmovin/bitcodin-java.svg)](https://travis-ci.org/bitmovin/bitcodin-java)

The bitcodin API for Java is a seamless integration with the [bitcodin cloud transcoding system](http://www.bitcodin.com). It enables the generation of MPEG-DASH and HLS content in just some minutes.

[![bitcodin](http://www.bitmovin.net/wp-content/uploads/2015/03/General-Try-Now-1024x538.jpg)](http://www.bitcodin.com)

# Getting started

First add the library (jar) to your project. The main interaction with bitcodin will be handled through the BitcodinApi class. Therefore instantiate an object with your API key, which can be found in the settings of your bitcodin user account, as shown in the figure below.

![APIKey](http://www.bitcodin.com/wp-content/uploads/2015/06/api_key.png)

An example how you can instantiate the bitcodin API is shown in the following:

```java
import com.bitmovin.bitcodin.api.BitcodinApi;

public class BitcodinApiTestExternal {
    public static void main(String[] args) {
        BitcodinApi bitApi = new BitcodinApi("THISISMYAPIKEY");
    }
}
```

