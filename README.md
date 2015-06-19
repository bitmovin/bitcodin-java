# bitcodin-java
[![build status](https://travis-ci.org/bitmovin/bitcodin-java.svg)](https://travis-ci.org/bitmovin/bitcodin-java)

The bitcodin API for Java is a seamless integration with the [bitcodin cloud transcoding system](http://www.bitcodin.com). Enables the generation of MPEG-DASH and HLS content in just some minutes.

[![bitcodin](http://www.bitmovin.net/wp-content/uploads/2015/03/General-Try-Now-1024x538.jpg)](http://www.bitcodin.com)

# Getting started

First add the library (jar) to your project. The main interaction with bitcodin will be handled through the BitcodinApi class. Therefore instantiate an object as folows:
```java
import com.bitmovin.bitcodin.api.BitcodinApi;

public class BitcodinApiTestExternal {
    public static void main(String[] args) {
        BitcodinApi bitApi = new BitcodinApi("THISISMYAPIKEY");
    }
}
```

