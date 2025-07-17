package com.skryvets.voice;

import org.springframework.core.io.ByteArrayResource;

public class AppByteArrayResource extends ByteArrayResource {

    public AppByteArrayResource(byte[] byteArray) {
        super(byteArray);
    }

    @Override
    public String getFilename() {
        return "audioStream.webm";
    }
}
