# bitcodin-java
[![build status](https://travis-ci.org/bitmovin/bitcodin-java.svg)](https://travis-ci.org/bitmovin/bitcodin-java)

bitcodin API for Java enables interaction with the bitcodin cloud transcoding system. Enables the generation of MPEG-DASH and HLS content in just some minutes.

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

