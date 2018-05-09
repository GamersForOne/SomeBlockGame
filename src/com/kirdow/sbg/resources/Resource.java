package com.kirdow.sbg.resources;

import com.kirdow.sbg.resources.data.IResource;
import com.kirdow.sbg.resources.data.Texture;

import java.io.InputStream;

public class Resource {

    private final String file;
    private final ResourceType type;

    private IResource resourceData;

    public Resource(String file, ResourceType type) {
        file = file.replace('\\', '/');
        if (file.startsWith("/"))
            file = file.substring(1);
        this.file = file;
        this.type = type;
    }

    public void bind() {
        if (resourceData == null) {
            if (type == ResourceType.TEXTURE) {
                resourceData = new Texture(this);
            }

            if (resourceData == null)
                return;

            resourceData.load();
        }

        resourceData.bind();
    }

    public InputStream openStream() {
        String path = "/res/" + file;
        if (type != ResourceType.RAW && type != null)
            path += "." + type.getExt();

        return Resource.class.getResourceAsStream(path);
    }

}
