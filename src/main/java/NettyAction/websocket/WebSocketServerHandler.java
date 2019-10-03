package NettyAction.websocket;

import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;


import java.util.Date;

import static io.netty.handler.codec.http.HttpHeaderUtil.isKeepAlive;
import static io.netty.handler.codec.http.HttpHeaderUtil.setContentLength;

public class WebSocketServerHandler extends SimpleChannelInboundHandler {

    private WebSocketServerHandshaker handshaker;

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
        //第一次握手请求的消息由http协议承载 所以他是一个http请求
        System.out.println(msg.toString());
        //传统的http接入
        if (msg instanceof FullHttpRequest) {
            //处理WebSocket的握手请求
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {//websocket接入
            handleWebsocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    //处理客户端的Websocket请求
    private void handleWebsocketFrame(ChannelHandlerContext ctx, WebSocketFrame msg) {
        //关闭链路指令
        if (msg instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) msg.retain());
            return;
        }

        //PING 消息
        if (msg instanceof PingWebSocketFrame) {
            ctx.write(new PongWebSocketFrame(msg.content().retain()));
            return;
        }

        //非文本
        if (!(msg instanceof TextWebSocketFrame)) {
            throw new UnsupportedOperationException(String.format("%s frame type not support", msg.getClass().getName()));

        }

        //应答消息
        String requset = ((TextWebSocketFrame) msg).text();
        ctx.channel().write(new TextWebSocketFrame(requset + " >>>>Now is " + new Date().toString()));

    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest msg) {

        //HTTP 请异常
        if (!msg.decoderResult().isSuccess() || !"websocket".equals(msg.headers().get("Upgrade"))) {
            System.out.println(msg.decoderResult().isSuccess());
            System.out.println(msg.headers().get("Upgrade"));
            sendHttpResponse(ctx, msg, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }

        //握手
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://localhost:8080/websocket", null, false);
        handshaker = wsFactory.newHandshaker(msg);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());

        } else {
            handshaker.handshake(ctx.channel(), msg);
        }
    }

    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest msg, FullHttpResponse resp) {

        //响应
        if (resp.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(resp.status().toString(), CharsetUtil.UTF_8);
            resp.content().writeBytes(buf);
            buf.release();
            setContentLength(resp, resp.content().readableBytes());
        }

        //非Keep-Alive,关闭链接
        ChannelFuture future = ctx.channel().writeAndFlush(resp);
        if (!isKeepAlive(resp) || resp.status().code() != 200) {
            future.addListener(ChannelFutureListener.CLOSE);
        }


    }
}
