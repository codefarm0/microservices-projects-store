package com.greenlearner.product.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenlearner.product.dto.Product;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.TeeOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author - GreenLearner(https://www.youtube.com/c/greenlearner)
 */
@Component
@Slf4j
//@Order(1)
public class RequestResponseLoggers implements Filter {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        //password
        //ssn, pan, aadhar
        //personal


        MyCustomHttpRequestWrapper requestWrapper = new MyCustomHttpRequestWrapper((HttpServletRequest) request);

        String uri = requestWrapper.getRequestURI();
        log.info("Requeust URI: {}", uri);

        log.info("Requeust Method: {}", requestWrapper.getMethod());
        String requestData = new String(requestWrapper.getByteArray()).replaceAll("\n", " ");

        if("/v1/addProduct".equalsIgnoreCase(uri)){
            Product product = objectMapper.readValue(requestData, Product.class);

            product.setCurrency("****");

            requestData = objectMapper.writeValueAsString(product);
        }

        log.info("Requeust Body: {}", requestData);

        MyCustomHttpResponseWrapper responseWrapper = new MyCustomHttpResponseWrapper((HttpServletResponse) response);

        chain.doFilter(requestWrapper, responseWrapper);

        String responseResult = new String(responseWrapper.getBaos().toByteArray());
        if("/v1/addProduct".equalsIgnoreCase(uri)){
            Product product = objectMapper.readValue(responseResult, Product.class);

            product.setCurrency("****");

            responseResult = objectMapper.writeValueAsString(product);
        }
        log.info("Response status - {}", responseWrapper.getStatus());
        log.info("Response Body - {}", responseResult);
    }


    private class MyCustomHttpRequestWrapper extends HttpServletRequestWrapper {

        private byte[] byteArray;

        public MyCustomHttpRequestWrapper(HttpServletRequest request) {
            super(request);
            try {
                byteArray = IOUtils.toByteArray(request.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException("Issue while reading request stream");
            }
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {

            return new MyDelegatingServletInputStream(new ByteArrayInputStream(byteArray));

        }

        public byte[] getByteArray() {
            return byteArray;
        }
    }

    private class MyCustomHttpResponseWrapper extends HttpServletResponseWrapper {

        private ByteArrayOutputStream baos = new ByteArrayOutputStream();

        private PrintStream printStream = new PrintStream(baos);

        public ByteArrayOutputStream getBaos() {
            return baos;
        }

        public MyCustomHttpResponseWrapper(HttpServletResponse response) {
            super(response);
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            return new MyDelegatingServletOutputStream(new TeeOutputStream(super.getOutputStream(), printStream));
        }

        @Override
        public PrintWriter getWriter() throws IOException {
            return new PrintWriter(new TeeOutputStream(super.getOutputStream(), printStream));
        }
    }
}
