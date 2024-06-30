package com.example.inventoryservice.Validation;

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
 * HttpServletRequest wrapper to cache the request body.
 */
public  class CachedBodyHttpServletRequest extends HttpServletRequestWrapper {
    private final String body;
    /**
     * Constructs a CachedBodyHttpServletRequest object with the specified request and body.
     * @param request the original HttpServletRequest object
     * @param body the cached request body
     */
    public CachedBodyHttpServletRequest(HttpServletRequest request, String body) {
        super(request);
        this.body = body;
    }
    /**
     * Retrieves the input stream of the cached request body.
     * @return a ServletInputStream object representing the cached request body
     * @throws IOException if an I/O error occurs
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
     * Retrieves the reader of the cached request body.
     * @return a BufferedReader object representing the cached request body
     * @throws IOException if an I/O error occurs
     */
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }
}
