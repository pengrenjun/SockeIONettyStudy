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
 * �豸��¼������->�豸��(�ͻ���) ���з���ˮ�������Ϣ
 *
 */
@ChannelHandler.Sharable
public class RtuMeterClientHandler extends ChannelHandlerAdapter {

    Logger logger=LoggerFactory.getLogger(this.getClass());


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //super.channelActive(ctx);
        //�豸�����������˺��͵�¼����
        //��¼����(����java���л����� ʵ�ʿ��������Զ����Э���ֽڱ��ĸ�ʽ)
        LoginReq loginReq=new LoginReq();
        loginReq.setLoginTime(new Date(System.currentTimeMillis()));
        loginReq.setMsgType(CommonMsg.LOGIN_REQ_TYPE);
        //����һ���ɼ�����
        loginReq.setRtuCode("rtu-001-002-"+new Random().nextInt(100));
        logger.info("������:{} ���͵�¼����",loginReq.getRtuCode());
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
        //��¼Ӧ��
        if(commonMsg.getMsgType().equals(CommonMsg.LOGIN_RESP_TYPE)){
            LoginResp loginResp=(LoginResp) commonMsg;
            logger.info("��¼�������Ӧ���->{}",loginResp.toString());
        }
        //���γ���ָ��
        if(commonMsg.getMsgType().equals(CommonMsg.COLLECT_METERDATA_CMD_TYPE)){
            CollectMeterDataCmd collectMeterDataCmd=(CollectMeterDataCmd) commonMsg;

            logger.info("�豸�˽��յ�����������ָ����Ϣ��{}",collectMeterDataCmd.toString());
            String rtuCode=collectMeterDataCmd.getRtuCode();
            //�豸�˷��ͱ����ݸ������
            CollectMeterDataAck collectMeterDataAck=new CollectMeterDataAck();
            collectMeterDataAck.setRtuCode(rtuCode);
            collectMeterDataAck.setMsgType(CommonMsg.COLLECT_METERDATA_ACK_TYPE);

            List<MeterData> meterDataList=new ArrayList<>();

            int meterCount=10;
            //ˮ������
            for(int i=0;i<meterCount;i++){
                MeterData meterData=new MeterData();
                meterData.setTimestamp(new Date(System.currentTimeMillis()));
                meterData.setMeterCode("meter-0102-0809-"+new Random().nextInt(1000));
                meterData.setMeterReading(new Random().nextDouble()*100);
                meterDataList.add(meterData);
            }
            collectMeterDataAck.setMeterDataList(meterDataList);

            logger.info("�豸����Ӧ���˵ĳ���rtu:{} ָ��,����rtu����������ˮ������",rtuCode);
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
