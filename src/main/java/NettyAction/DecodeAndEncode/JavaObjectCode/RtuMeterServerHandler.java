package NettyAction.DecodeAndEncode.JavaObjectCode;

import NettyAction.DecodeAndEncode.*;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * �豸��¼��->����(�����) ���з��Ͳɼ�ָ��
 */
@ChannelHandler.Sharable
public class RtuMeterServerHandler extends ChannelHandlerAdapter {

    Logger logger=LoggerFactory.getLogger(this.getClass());

    //�豸��¼����session�Ự������
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
        //���˻�Ⱥ�豸�ε���������
        CommonMsg commonMsg=(CommonMsg)msg;
        if(commonMsg.getMsgType().equals(CommonMsg.LOGIN_REQ_TYPE)){
            //�豸��¼����
            LoginReq loginReq=(LoginReq)commonMsg;
            logger.info("�豸������ {}��¼����->{}",loginReq.getRtuCode(),loginReq.toString());
            //�����豸������
            sessionMap.put(loginReq.getRtuCode(),ctx);

            //��Ӧ�豸��¼������
            LoginResp loginResp=new LoginResp();
            loginResp.setMsgType(CommonMsg.LOGIN_RESP_TYPE);
            loginResp.setResultCode("200");
            loginResp.setResultMessage("�豸������:"+loginReq.getRtuCode()+" �����ӷ�����,���Խ���ͨ��");
            logger.info("������Ӧ�������豸�ĵ�¼����{}",loginResp.toString());
            ctx.writeAndFlush(loginResp);

            //���ͳ������ݱ���
            Thread.sleep(2000);
            CollectMeterDataCmd collectMeterDataCmd=new CollectMeterDataCmd();
            collectMeterDataCmd.setMsgType(CommonMsg.COLLECT_METERDATA_CMD_TYPE);
            collectMeterDataCmd.setMessage(new byte[]{0x11,0x22,0x33,0x79,0x61});
            collectMeterDataCmd.setRtuCode(loginReq.getRtuCode());
            logger.info("���˶��豸��{} ���ͳ������ݱ��� {}",loginReq.getRtuCode(),loginResp.toString());
            ctx.writeAndFlush(collectMeterDataCmd);
        }

        //�豸����Ӧ�����ص�����
        if(commonMsg.getMsgType().equals(CommonMsg.COLLECT_METERDATA_ACK_TYPE)){

            CollectMeterDataAck meterDataAck=(CollectMeterDataAck)commonMsg;

            logger.info("�豸rtu:{} ���صĹ�����ˮ�������Ϣ��{}",meterDataAck.getRtuCode(),meterDataAck.toString());

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
