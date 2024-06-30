package com.company.dms.validation;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
/**
 * Wrapper class for HttpServletRequest that caches the request body for multiple reads.
 */
public  class CachedBodyHttpServletRequest extends HttpServletRequestWrapper {
    private final String body;
    /**
     * Constructs a CachedBodyHttpServletRequest with the specified request and body content.
     *
     * @param request The original HttpServletRequest.
     * @param body    The cached request body content.
     */
    public CachedBodyHttpServletRequest(HttpServletRequest request, String body) {
        super(request);
        this.body = body;
    }
    /**
     * Overrides the getInputStream method to return a ServletInputStream reading from the cached body content.
     *
     * @return A ServletInputStream reading from the cached body content.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));
        return new ServletInputStream() {
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener listener) {
                throw new UnsupportedOperationException();
            }
        };
    }
    /**
     * Overrides the getReader method to return a BufferedReader reading from the cached body content.
     *
     * @return A BufferedReader reading from the cached body content.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }
}

