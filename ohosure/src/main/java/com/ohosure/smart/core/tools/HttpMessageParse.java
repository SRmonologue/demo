package com.ohosure.smart.core.tools;


import com.ohosure.smart.core.communication.HttpInMessage;
import com.ohosure.smart.database.devkit.log.MLog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * 以HTTP消息格式解析Socket数据
 *
 * @author daxing
 */
public class HttpMessageParse {
    private final static String TAG = HttpMessageParse.class.getSimpleName();
    private static final byte CR = '\r';
    private static final byte LF = '\n';
    private static final byte[] CRLF = {CR, LF};

    public int readResponse(InputStream in, HttpInMessage mHttpResponseMessage)
            throws IOException, InterruptedException {
        // 读取状态行
        String statusLine = readStatusLine(in);
        if (statusLine == null)
            return -1;
        MLog.v(TAG, statusLine);
        mHttpResponseMessage.setStatus(statusLine);
        // 消息报头
        Map<String, String> headers = readHeaders(in);
        mHttpResponseMessage.setHeaders(headers);
        if (headers.get("content-length") == null)
            return -1;
        int contentLength = Integer.valueOf(headers.get("content-length"));
        // 可选的响应正文
        byte[] body = readResponseBody(in, contentLength);
        if (body == null)
            return -1;
        String charset = headers.get("content-type");
        if (charset != null && charset.matches(".+;charset=.+")) {
            charset = charset.split(";")[1].split("=")[1];
        } else {
            charset = "utf-8"; // 默认编码
        }
        mHttpResponseMessage.setJsonContent(new String(body, charset));
        MLog.v(TAG, new String(body, charset));
        return 0;
    }

    private byte[] readResponseBody(InputStream in, int contentLength)
            throws IOException {

        ByteArrayOutputStream buff = new ByteArrayOutputStream(contentLength);

        int b;
        int count = 0;
        while (count++ < contentLength) {
            b = in.read();
            if (b == -1)
                return null;
            buff.write(b);
        }
        return buff.toByteArray();
    }

    private Map<String, String> readHeaders(InputStream in) throws IOException {
        Map<String, String> headers = new HashMap<String, String>();
        String line;
        while (!("".equals(line = readLine(in)))) {

            String[] nv = line.split(": "); // 头部字段的名值都是以(冒号+空格)分隔的
            headers.put(nv[0].toLowerCase(), nv[1]);

            MLog.v(TAG, line);
        }
        return headers;
    }

    private String readStatusLine(InputStream in) throws IOException {
        return readLine(in);
    }

    /**
     * 读取以CRLF分隔的一行，返回结果不包含CRLF
     */
    private String readLine(InputStream in) throws IOException {
        int b;
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        while ((b = in.read()) != CR) {
            if (b == -1)
                return null;
            buff.write(b);
        }
        in.read(); // 读取 LF
        String line = buff.toString();
        return line;
    }
}
