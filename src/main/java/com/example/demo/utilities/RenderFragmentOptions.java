package com.example.demo.utilities;

import java.time.Duration;

public class RenderFragmentOptions {

    private SseEventType sseEventType;
    private QuerySelector querySelector;
    private FragmentMergeType merge;
    private Duration settleDuration;

    // Constructor
    public RenderFragmentOptions(SseEventType eventType, QuerySelector querySelector, FragmentMergeType merge, Duration settleDuration) {
        this.sseEventType = eventType;
        this.querySelector = querySelector;
        this.merge = merge;
        this.settleDuration = settleDuration;
    }

    public SseEventType getSseEventType() {
        return sseEventType;
    }

    public void setSseEventType(SseEventType sseEventType) {
        this.sseEventType = sseEventType;
    }

    // Getters and Setters
    public QuerySelector getQuerySelector() {
        return querySelector;
    }

    public void setQuerySelector(QuerySelector querySelector) {
        this.querySelector = querySelector;
    }

    public FragmentMergeType getMerge() {
        return merge;
    }

    public void setMerge(FragmentMergeType merge) {
        this.merge = merge;
    }

    public Duration getSettleDuration() {
        return settleDuration;
    }

    public void setSettleDuration(Duration settleDuration) {
        this.settleDuration = settleDuration;
    }

    // Functional interface for the option pattern
    @FunctionalInterface
    public interface RenderFragmentOption {
        void apply(RenderFragmentOptions options);
    }

    // Method to apply options
    public void applyOptions(RenderFragmentOption... options) {
        for (RenderFragmentOption option : options) {
            option.apply(this);
        }
    }
}
