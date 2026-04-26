package me.hannsi.lfjg.render.system.memory;

public record DeferredFree(Allocation allocation, long releaseFrame) {}
