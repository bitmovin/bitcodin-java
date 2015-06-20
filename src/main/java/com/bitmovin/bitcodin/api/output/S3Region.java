package com.bitmovin.bitcodin.api.output;

import com.google.gson.annotations.SerializedName;

public enum S3Region {
    @SerializedName("eu-west-1")
    EU_WEST_1,
    @SerializedName("eu-central-1")
    EU_CENTRAL_1,
    @SerializedName("us-east-1")
    US_EAST_1,
    @SerializedName("us-west-1")
    US_WEST_1,
    @SerializedName("us-west-2")
    US_WEST_2,
    @SerializedName("us-gov-west-1")
    US_GOV_WEST_1,
    @SerializedName("ap-southeast-1")
    AP_SOUTHEAST_1,
    @SerializedName("ap-southeast-2")
    AP_SOUTHEAST_2,
    @SerializedName("ap-northeast-1")
    AP_NORTHEAST_1,
    @SerializedName("sa-east-1")
    SA_EAST_1,
    @SerializedName("ca-north-1")
    CA_NORTH_1
}
