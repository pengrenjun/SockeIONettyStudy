package NettyAction.DecodeAndEncode.JavaObjectCode;

import NettyAction.DecodeAndEncode.*;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 设备登录后->主端(服务端) 下行发送采集指令
 */
@ChannelHandler.Sharable
public class RtuMeterServerHandler extends ChannelHandlerAdapter {

    Logger logger=LoggerFactory.getLogger(this.getClass());

    //设备登录连接session会话管理器
    private ConcurrentHashMap<String,ChannelHandlerContext> sessionMap=new ConcurrentHashMap<>();


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //super.channelRead(ctx, msg);
        //主端或群设备段的上行数据
        CommonMsg commonMsg=(CommonMsg)msg;
        if(commonMsg.getMsgType().equals(CommonMsg.LOGIN_REQ_TYPE)){
            //设备登录请求
            LoginReq loginReq=(LoginReq)commonMsg;
            logger.info("设备集中器 {}登录上线->{}",loginReq.getRtuCode(),loginReq.toString());
            //缓存设备的连接
            sessionMap.put(loginReq.getRtuCode(),ctx);

            //响应设备登录的请求
            LoginResp loginResp=new LoginResp();
            loginResp.setMsgType(CommonMsg.LOGIN_RESP_TYPE);
            loginResp.setResultCode("200");
            loginResp.setResultMessage("设备集中器:"+loginReq.getRtuCode()+" 已连接服务器,可以进行通信");
            logger.info("主端响应集中器设备的登录请求：{}",loginResp.toString());
            ctx.writeAndFlush(loginResp);

            //发送抄表数据报文
            Thread.sleep(2000);
            CollectMeterDataCmd collectMeterDataCmd=new CollectMeterDataCmd();
            collectMeterDataCmd.setMsgType(CommonMsg.COLLECT_METERDATA_CMD_TYPE);
            collectMeterDataCmd.setMessage(new byte[]{0x11,0x22,0x33,0x79,0x61});
            collectMeterDataCmd.setRtuCode(loginReq.getRtuCode());
            logger.info("主端对设备：{} 发送抄表数据报文 {}",loginReq.getRtuCode(),loginResp.toString());
            ctx.writeAndFlush(collectMeterDataCmd);
        }

        //设备端响应抄表返回的数据
        if(commonMsg.getMsgType().equals(CommonMsg.COLLECT_METERDATA_ACK_TYPE)){

            CollectMeterDataAck meterDataAck=(CollectMeterDataAck)commonMsg;

            logger.info("设备rtu:{} 返回的关联的水表读数信息：{}",meterDataAck.getRtuCode(),meterDataAck.toString());

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
