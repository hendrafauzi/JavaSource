import org.apache.commons.io.output.TeeOutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class HttpLoggingFilter implements Filter
{
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void init(FilterConfig filterConfig)
    {
        /*Do nothing*/
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    {
        try
        {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;

            BufferedRequestWrapper bufferedRequest = new BufferedRequestWrapper(httpServletRequest);
            BufferedResponseWrapper bufferedResponse = new BufferedResponseWrapper(httpServletResponse);

            String requestPath = (httpServletRequest.getPathInfo() == null) ? httpServletRequest.getServletPath() : httpServletRequest.getPathInfo();

            ThreadContext.put(LoggerParam.REQUEST_ID, String.valueOf(System.currentTimeMillis()));
            ThreadContext.put(LoggerParam.REMOTE_HOST, httpServletRequest.getRemoteHost());
            ThreadContext.put(LoggerParam.REMOTE_PORT, String.valueOf(httpServletRequest.getRemotePort()));
            ThreadContext.put(LoggerParam.HTTP_METHOD, httpServletRequest.getMethod());
            ThreadContext.put(LoggerParam.REQUEST_PATH, requestPath);
            ThreadContext.put(LoggerParam.REQUEST_PARAM, getRequestParam(httpServletRequest));
            ThreadContext.put(LoggerParam.REQUEST_BODY, bufferedRequest.getRequestBody());

            logger.info("event: START, httpMethod: {}, requestPath: {}, remoteHost: {}, remotePort: {}, requestParam: {}, requestBody: {}",
                    ThreadContext.get(LoggerParam.HTTP_METHOD),
                    ThreadContext.get(LoggerParam.REQUEST_PATH),
                    ThreadContext.get(LoggerParam.REMOTE_HOST),
                    ThreadContext.get(LoggerParam.REMOTE_PORT),
                    ThreadContext.get(LoggerParam.REQUEST_PARAM),
                    ThreadContext.get(LoggerParam.REQUEST_BODY)
            );
            chain.doFilter(bufferedRequest, bufferedResponse);
            ThreadContext.put(LoggerParam.RESPONSE_BODY, bufferedResponse.getContent());

            logger.info("event: END, httpMethod: {}, requestPath: {}, responseBody: {}",
                    ThreadContext.get(LoggerParam.HTTP_METHOD),
                    ThreadContext.get(LoggerParam.REQUEST_PATH),
                    ThreadContext.get(LoggerParam.RESPONSE_BODY));
        }
        catch (NullPointerException a)
        {
            logger.info("event: END, httpMethod: {}, requestPath: {}, response: {}",
                    ThreadContext.get(LoggerParam.HTTP_METHOD),
                    ThreadContext.get(LoggerParam.REQUEST_PATH),
                    "Redirecting page...");
        }
        catch (Exception a)
        {
            logger.debug(a);
        }

        ThreadContext.clearMap();
    }

    private String getRequestParam(HttpServletRequest request)
    {
        Enumeration<?> requestParamNames = request.getParameterNames();
        StringBuilder sb = new StringBuilder();

        while (requestParamNames.hasMoreElements())
        {
            String requestParamName = (String) requestParamNames.nextElement();
            String requestParamValue = request.getParameter(requestParamName);
            sb.append(requestParamName).append(":").append(requestParamValue);
        }

        if (sb.length() <= 2)
        {
            return "";
        }

        return sb.toString();
    }

    @Override
    public void destroy()
    {
        /*Do nothing*/
    }

    public static final class BufferedRequestWrapper extends HttpServletRequestWrapper
    {
        private ByteArrayOutputStream baos;
        private byte[] buffer;

        BufferedRequestWrapper(HttpServletRequest req) throws IOException
        {
            super(req);
            // Read InputStream and store its content in a buffer.
            InputStream is = req.getInputStream();
            this.baos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int letti;

            while ((letti = is.read(buf)) > 0)
            {
                this.baos.write(buf, 0, letti);
            }

            this.buffer = this.baos.toByteArray();
        }

        @Override
        public ServletInputStream getInputStream()
        {
            ByteArrayInputStream bais;
            BufferedServletInputStream bsis;

            bais = new ByteArrayInputStream(this.buffer);
            bsis = new BufferedServletInputStream(bais);

            return bsis;
        }

        String getRequestBody() throws IOException
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.getInputStream(), StandardCharsets.UTF_8));
            String line;
            StringBuilder inputBuffer = new StringBuilder();

            do
            {
                line = reader.readLine();

                if (null != line)
                {
                    inputBuffer.append(line.trim());
                }
            }
            while (line != null);

            reader.close();
            return inputBuffer.toString().trim();
        }
    }

    private static final class BufferedServletInputStream extends ServletInputStream
    {
        private ByteArrayInputStream bais;

        BufferedServletInputStream(ByteArrayInputStream bais)
        {
            this.bais = bais;
        }

        @Override
        public int available()
        {
            return this.bais.available();
        }

        @Override
        public int read()
        {
            return this.bais.read();
        }

        @Override
        public int read(byte[] buf, int off, int len)
        {
            return this.bais.read(buf, off, len);
        }

        @Override
        public boolean isFinished()
        {
            return false;
        }

        @Override
        public boolean isReady()
        {
            return false;
        }

        @Override
        public void setReadListener(ReadListener readListener)
        {
            /*Do nothing*/
        }
    }

    public static class TeeServletOutputStream extends ServletOutputStream
    {
        private final TeeOutputStream targetStream;

        TeeServletOutputStream(OutputStream one, OutputStream two)
        {
            targetStream = new TeeOutputStream(one, two);
        }

        @Override
        public void write(int arg0) throws IOException
        {
            this.targetStream.write(arg0);
        }

        @Override
        public void flush() throws IOException
        {
            super.flush();
            this.targetStream.flush();
        }

        @Override
        public void close() throws IOException
        {
            super.close();
            this.targetStream.close();
        }

        @Override
        public boolean isReady()
        {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener writeListener)
        {
            /*Do nothing*/
        }
    }

    public static class BufferedResponseWrapper implements HttpServletResponse
    {
        HttpServletResponse original;
        TeeServletOutputStream tee;
        ByteArrayOutputStream bos;

        BufferedResponseWrapper(HttpServletResponse response)
        {
            original = response;
        }

        String getContent() throws UnsupportedEncodingException
        {
            return bos.toString("UTF-8");
        }

        public PrintWriter getWriter() throws IOException
        {
            return original.getWriter();
        }

        public ServletOutputStream getOutputStream() throws IOException
        {
            if (tee == null)
            {
                bos = new ByteArrayOutputStream();
                tee = new TeeServletOutputStream(original.getOutputStream(), bos);
            }

            return tee;
        }

        @Override
        public String getCharacterEncoding()
        {
            return original.getCharacterEncoding();
        }

        @Override
        public String getContentType()
        {
            return original.getContentType();
        }

        @Override
        public void setCharacterEncoding(String charset)
        {
            original.setCharacterEncoding(charset);
        }

        @Override
        public void setContentLength(int len)
        {
            original.setContentLength(len);
        }

        @Override
        public void setContentLengthLong(long l)
        {
            original.setContentLengthLong(l);
        }

        @Override
        public void setContentType(String type)
        {
            original.setContentType(type);
        }

        @Override
        public void setBufferSize(int size)
        {
            original.setBufferSize(size);
        }

        @Override
        public int getBufferSize()
        {
            return original.getBufferSize();
        }

        @Override
        public void flushBuffer() throws IOException
        {
            tee.flush();
        }

        @Override
        public void resetBuffer()
        {
            original.resetBuffer();
        }

        @Override
        public boolean isCommitted()
        {
            return original.isCommitted();
        }

        @Override
        public void reset()
        {
            original.reset();
        }

        @Override
        public void setLocale(Locale loc)
        {
            original.setLocale(loc);
        }

        @Override
        public Locale getLocale()
        {
            return original.getLocale();
        }

        @Override
        public void addCookie(Cookie cookie)
        {
            original.addCookie(cookie);
        }

        @Override
        public boolean containsHeader(String name)
        {
            return original.containsHeader(name);
        }

        @Override
        public String encodeURL(String url)
        {
            return original.encodeURL(url);
        }

        @Override
        public String encodeRedirectURL(String url)
        {
            return original.encodeRedirectURL(url);
        }

        @Override
        public String encodeUrl(String url)
        {
            return original.encodeURL(url);
        }

        @Override
        public String encodeRedirectUrl(String url)
        {
            return original.encodeRedirectURL(url);
        }

        @Override
        public void sendError(int sc, String msg) throws IOException
        {
            original.sendError(sc, msg);
        }

        @Override
        public void sendError(int sc) throws IOException
        {
            original.sendError(sc);
        }

        @Override
        public void sendRedirect(String location) throws IOException
        {
            original.sendRedirect(location);
        }

        @Override
        public void setDateHeader(String name, long date)
        {
            original.setDateHeader(name, date);
        }

        @Override
        public void addDateHeader(String name, long date)
        {
            original.addDateHeader(name, date);
        }

        @Override
        public void setHeader(String name, String value)
        {
            original.setHeader(name, value);
        }

        @Override
        public void addHeader(String name, String value)
        {
            original.addHeader(name, value);
        }

        @Override
        public void setIntHeader(String name, int value)
        {
            original.setIntHeader(name, value);
        }

        @Override
        public void addIntHeader(String name, int value)
        {
            original.addIntHeader(name, value);
        }

        @Override
        public void setStatus(int sc)
        {
            original.setStatus(sc);
        }

        @Override
        public void setStatus(int sc, String sm)
        {
            try
            {
                original.sendError(sc, sm);
            }
            catch (IOException e)
            {
                logger.error("Error when setStatus: {}", e);
            }
        }

        @Override
        public int getStatus()
        {
            return original.getStatus();
        }

        @Override
        public String getHeader(String s)
        {
            return original.getHeader(s);
        }

        @Override
        public Collection<String> getHeaders(String s)
        {
            return original.getHeaders(s);
        }

        @Override
        public Collection<String> getHeaderNames()
        {
            return original.getHeaderNames();
        }
    }
}