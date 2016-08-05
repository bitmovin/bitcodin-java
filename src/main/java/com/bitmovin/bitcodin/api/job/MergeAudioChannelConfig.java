package com.bitmovin.bitcodin.api.job;

import java.util.List;

import com.google.gson.annotations.Expose;

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
