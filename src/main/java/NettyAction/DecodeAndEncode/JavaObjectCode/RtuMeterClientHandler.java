package NettyAction.DecodeAndEncode.JavaObjectCode;

import NettyAction.DecodeAndEncode.CommonMsg;
import NettyAction.DecodeAndEncode.LoginReq;
import NettyAction.DecodeAndEncode.LoginResp;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Random;

/**
 * 设备登录请求处理->设备段(客户端) 上行返回水表读数信息
 *
 */
public class RtuMeterClientHandler extends ChannelHandlerAdapter {

    Logger logger=LoggerFactory.getLogger(this.getClass());


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //super.channelActive(ctx);
        //设备上线连接主端后发送登录请求
        //登录报文(基于java序列化对象 实际开发中是自定义的协议字节报文格式)
        LoginReq loginReq=new LoginReq();
        loginReq.setLoginTime(new Date(System.currentTimeMillis()));
        loginReq.setMsgType(CommonMsg.LOGIN_REQ_TYPE);
        //生成一个采集器号
        loginReq.setRtuCode("rtu-001-002-"+new Random().nextInt(100));
        logger.info("集中器:{} 发送登录请求",loginReq.getRtuCode());
        ctx.writeAndFlush(loginReq);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //super.channelRead(ctx, msg);
        CommonMsg commonMsg=(CommonMsg) msg;
        //登录应答
        if(commonMsg.getMsgType().equals(CommonMsg.LOGIN_RESP_TYPE)){
            LoginResp loginResp=(LoginResp) commonMsg;
            logger.info("登录请求的响应结果->{}",loginResp.toString());
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
