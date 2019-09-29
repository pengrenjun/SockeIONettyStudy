package NettyAction.http.fileServerDemo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Pattern;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaderNames.LOCATION;
import static io.netty.handler.codec.http.HttpHeaderUtil.setContentLength;
import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpUtil.isKeepAlive;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HttpFileServerHandler extends  SimpleChannelInboundHandler<FullHttpRequest> {
    private final String url;

    public HttpFileServerHandler(String url) {
        this.url = url;
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx,
                             FullHttpRequest request) throws Exception {
        //����ʧ�ܣ�����400���ͻ����������
        if (!request.decoderResult().isSuccess()) {
            sendError(ctx, BAD_REQUEST);
            return;
        }
        //ֻ�ܽ���GET����
        if (request.method() != GET) {
            sendError(ctx, METHOD_NOT_ALLOWED);
            return;
        }
        //��ȡ�͹���URI��������Դ��λ��
        final String uri = request.uri();
        final String path = sanitizeUri(uri);
        //���·�����Ϸ�������403
        if (path == null) {
            sendError(ctx, FORBIDDEN);
            return;
        }
        //����ļ������ڻ����������ļ�������404
        File file = new File(path);
        if (file.isHidden() || !file.exists()) {
            sendError(ctx, NOT_FOUND);
            return;
        }
        //�����Ŀ¼������Ŀ¼�����Ӹ������
        if (file.isDirectory()) {
            if (uri.endsWith("/")) {
                sendListing(ctx, file);
            } else {
                sendRedirect(ctx, uri + '/');
            }
            return;
        }
        //������Ǹ��ļ�������403
        if (!file.isFile()) {
            sendError(ctx, FORBIDDEN);
            return;
        }
        RandomAccessFile randomAccessFile = null;
        try {
            //ʹ������ļ���д����ֻ���ķ�ʽ���ļ�
            randomAccessFile = new RandomAccessFile(file, "r");// ��ֻ���ķ�ʽ���ļ�
        } catch (FileNotFoundException fnfe) {
            sendError(ctx, NOT_FOUND);
            return;
        }
        //��ȡ�ļ��ĳ���
        long fileLength = randomAccessFile.length();
        //����ɹ���httpӦ����Ϣ
        HttpResponse response = new DefaultHttpResponse(HTTP_1_1, OK);
        //��Ϣͷ������content length�� content type
        setContentLength(response, fileLength);
        setContentTypeHeader(response, file);
        //�ж��Ƿ���KeepAlive
        if (isKeepAlive(request)) {
            //����ǣ���Ӧ����Ϣͷ������connectionΪkeepalive
            response.headers().set(CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }
        //����Ӧͷд��channel��������Ӧ��Ϣ
        ctx.write(response);
        ChannelFuture sendFileFuture;
        //ͨ��Netty��ChunkedFile����ֱ�ӽ��ļ�д�뵽���ͻ�����
        sendFileFuture = ctx.write(new ChunkedFile(randomAccessFile, 0,
                fileLength, 8192), ctx.newProgressivePromise());
        //ΪsendFileFuture���GenericFutureListener
        sendFileFuture.addListener(new ChannelProgressiveFutureListener() {
            @Override
            public void operationProgressed(ChannelProgressiveFuture future,
                                            long progress, long total) {
                if (total < 0) { // total unknown
                    System.err.println("Transfer progress: " + progress);
                } else {
                    System.err.println("Transfer progress: " + progress + " / "
                            + total);
                }
            }

            @Override
            public void operationComplete(ChannelProgressiveFuture future)
                    throws Exception {
                //���������ɣ���ӡTransfer complete.
                System.out.println("Transfer complete.");
            }
        });
        //���ʹ��chunked���룬�����Ҫ����һ����������Ŀ���Ϣ�壬��LastHttpContent.EMPTY_LAST_CONTENT���͵��������У���ʶ���е���Ϣ���Ѿ��������
        //ͬʱ����flush������֮ǰ�ڷ��ͻ���������Ϣˢ�µ�SocketChannel�з��͸��Է�
        ChannelFuture lastContentFuture = ctx
                .writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
        //����Ƿ�keepalive�ģ����һ����Ϣ������֮�󣬷����Ҫ�����ر�����
        if (!isKeepAlive(request)) {
            lastContentFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        if (ctx.channel().isActive()) {
            sendError(ctx, INTERNAL_SERVER_ERROR);
        }
    }

    private static final Pattern INSECURE_URI = Pattern.compile(".*[<>&\"].*");

    private String sanitizeUri(String uri) {
        try {
            //��uri����
            uri = URLDecoder.decode(uri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            try {
                uri = URLDecoder.decode(uri, "ISO-8859-1");
            } catch (UnsupportedEncodingException e1) {
                throw new Error();
            }
        }
        //����ɹ�����uri���кϷ����ж�
        if (!uri.startsWith(url)) {
            return null;
        }
        if (!uri.startsWith("/")) {
            return null;
        }
        //���ָ����滻Ϊ����ϵͳ�ļ�·���ָ���
        uri = uri.replace('/', File.separatorChar);
        if (uri.contains(File.separator + '.')
                || uri.contains('.' + File.separator) || uri.startsWith(".")
                || uri.endsWith(".") || INSECURE_URI.matcher(uri).matches()) {
            return null;
        }
        //���ļ�·��ƴ�ӣ���ǰ���г������ڵĹ���Ŀ¼+uri�ľ���·��
        return System.getProperty("user.dir") + File.separator + uri;
    }

    private static final Pattern ALLOWED_FILE_NAME = Pattern
            .compile("[A-Za-z0-9][-_A-Za-z0-9\\.]*");

    private static void sendListing(ChannelHandlerContext ctx, File dir) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK);
        response.headers().set(CONTENT_TYPE, "text/html; charset=UTF-8");
        StringBuilder buf = new StringBuilder();
        String dirPath = dir.getPath();
        buf.append("<!DOCTYPE html>\r\n");
        buf.append("<html><head><title>");
        buf.append(dirPath);
        buf.append(" Ŀ¼��");
        buf.append("</title></head><body>\r\n");
        buf.append("<h3>");
        buf.append(dirPath).append(" Ŀ¼��");
        buf.append("</h3>\r\n");
        buf.append("<ul>");
        buf.append("<li>���ӣ�<a href=\"../\">..</a></li>\r\n");
        for (File f : dir.listFiles()) {
            if (f.isHidden() || !f.canRead()) {
                continue;
            }
            String name = f.getName();
            if (!ALLOWED_FILE_NAME.matcher(name).matches()) {
                continue;
            }
            buf.append("<li>���ӣ�<a href=\"");
            buf.append(name);
            buf.append("\">");
            buf.append(name);
            buf.append("</a></li>\r\n");
        }
        buf.append("</ul></body></html>\r\n");
        //�����Ӧ��Ϣ�Ļ������
        ByteBuf buffer = Unpooled.copiedBuffer(buf, CharsetUtil.UTF_8);
        //���������е���Ӧ��Ϣ��ŵ�HTTP��Ӧ����Ϣ��
        response.content().writeBytes(buffer);
        //�ͷŻ�����
        buffer.release();
        //����Ӧ��Ϣ���͵�����������ˢ�µ�SocketChannel
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private static void sendRedirect(ChannelHandlerContext ctx, String newUri) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, FOUND);
        response.headers().set(LOCATION, newUri);
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private static void sendError(ChannelHandlerContext ctx,
                                  HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
                status, Unpooled.copiedBuffer("Failure: " + status.toString()
                + "\r\n", CharsetUtil.UTF_8));
        response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private static void setContentTypeHeader(HttpResponse response, File file) {
        MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
        response.headers().set(CONTENT_TYPE,
                mimeTypesMap.getContentType(file.getPath()));
    }

}
