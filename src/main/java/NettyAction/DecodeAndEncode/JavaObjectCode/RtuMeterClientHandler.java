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
 * �豸��¼������->�豸��(�ͻ���) ���з���ˮ�������Ϣ
 *
 */
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
