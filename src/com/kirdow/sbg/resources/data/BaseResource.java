package com.kirdow.sbg.resources.data;

import com.kirdow.sbg.resources.Resource;

public abstract class BaseResource<T> implements IResource {

    protected final Resource resource;

    private T resourcedata;

    private boolean loaded;

    public BaseResource(Resource resource, T _default) {
        this.resource = resource;
        this.resourcedata = _default;

        this.loaded = false;
    }

    public final Resource getResource() {
        return resource;
    }

    public T getData() {
        load();
        return resourcedata;
    }

    protected void setData(T data) {
        this.resourcedata = data;
    }

    public final void load() {
        if (loaded) return;

        loadResource();
        loaded = true;
    }

    protected abstract void loadResource();
}
