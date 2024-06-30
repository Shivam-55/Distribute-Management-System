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
 * Extends HttpServletRequestWrapper to cache the request body for multiple reads.
 */
public  class CachedBodyHttpServletRequest extends HttpServletRequestWrapper {
    private final String body;

    /**
     * Constructs a new CachedBodyHttpServletRequest.
     *
     * @param request The original HttpServletRequest object.
     * @param body    The cached request body as a String.
     */
    public CachedBodyHttpServletRequest(HttpServletRequest request, String body) {
        super(request);
        this.body = body;
    }
    /**
     * Overrides getInputStream to provide a cached input stream for the request body.
     *
     * @return The ServletInputStream object for the cached request body.
     * @throws IOException if an I/O error occurs.
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
     * Overrides getReader to provide a BufferedReader for the cached request body.
     *
     * @return The BufferedReader object for the cached request body.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }
}
