package com.bitmovin.bitcodin.api.job;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by
 * Armin Trattnig [armin.trattnig@bitmovin.net]
 * on 23.11.15
 */
public class MergeAudioChannelConfig
{
    @Expose
    public List<Integer> audioChannels;

    public MergeAudioChannelConfig(List<Integer> audioChannels) {
        this.audioChannels = audioChannels;
    }
}
