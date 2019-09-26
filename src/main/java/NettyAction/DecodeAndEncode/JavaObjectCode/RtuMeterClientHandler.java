package NettyAction.DecodeAndEncode.JavaObjectCode;

import NettyAction.DecodeAndEncode.*;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 设备登录请求处理->设备段(客户端) 上行返回水表读数信息
 *
 */
@ChannelHandler.Sharable
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
        //主段抄表指令
        if(commonMsg.getMsgType().equals(CommonMsg.COLLECT_METERDATA_CMD_TYPE)){
            CollectMeterDataCmd collectMeterDataCmd=(CollectMeterDataCmd) commonMsg;

            logger.info("设备端接收到的主段下行指令信息：{}",collectMeterDataCmd.toString());
            String rtuCode=collectMeterDataCmd.getRtuCode();
            //设备端发送表数据给服务端
            CollectMeterDataAck collectMeterDataAck=new CollectMeterDataAck();
            collectMeterDataAck.setRtuCode(rtuCode);
            collectMeterDataAck.setMsgType(CommonMsg.COLLECT_METERDATA_ACK_TYPE);

            List<MeterData> meterDataList=new ArrayList<>();

            int meterCount=10;
            //水表数据
            for(int i=0;i<meterCount;i++){
                MeterData meterData=new MeterData();
                meterData.setTimestamp(new Date(System.currentTimeMillis()));
                meterData.setMeterCode("meter-0102-0809-"+new Random().nextInt(1000));
                meterData.setMeterReading(new Random().nextDouble()*100);
                meterDataList.add(meterData);
            }
            collectMeterDataAck.setMeterDataList(meterDataList);

            logger.info("设备端响应主端的抄表rtu:{} 指令,返回rtu关联的所有水表数据",rtuCode);
            ctx.writeAndFlush(collectMeterDataAck);
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
