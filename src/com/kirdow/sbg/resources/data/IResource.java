package com.kirdow.sbg.resources.data;

import com.kirdow.sbg.resources.Resource;

public interface IResource {

    Resource getResource();

    void load();

    void bind();

}
