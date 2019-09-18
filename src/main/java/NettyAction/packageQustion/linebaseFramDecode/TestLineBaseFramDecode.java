package NettyAction.packageQustion.linebaseFramDecode;

import NettyAction.TimeServerAndClient.democlient.TimeClient;
import NettyAction.TimeServerAndClient.demoserver.TimeServer;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import org.junit.Test;

/**
 * ���Ի���LineBasedFrameDecoder����������
 */
public class TestLineBaseFramDecode {

    @Test
    public void StartTimeServer(){

        //���LineBasedFrameDecoder��StringDecoder����������
        new TimeServer()
                .initServerAndBindPort(8002)
                .addChildHandlers(new LineBasedFrameDecoder(1024),new StringDecoder(),
                        new TimeServerLineBfDecodeHandler())
                .start();

        /**
         * �����ͳһ���յ��˿ͻ��˷��͵�������ָ��
         *          +-------------------------------------------------+
         *          |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
         * +--------+-------------------------------------------------+----------------+
         * |00000000| 71 63 74 0d 0a 71 63 74 0d 0a 71 63 74 0d 0a 71 |qct..qct..qct..q|
         * |00000010| 63 74 0d 0a 71 63 74 0d 0a 71 63 74 0d 0a 71 63 |ct..qct..qct..qc|
         * |00000020| 74 0d 0a 71 63 74 0d 0a 71 63 74 0d 0a 71 63 74 |t..qct..qct..qct|
         * |00000030| 0d 0a                                           |..              |
         * +--------+-------------------------------------------------+----------------+
         * 23:02:24.203 [nioEventLoopGroup-1-0] INFO  N.p.l.TimeServerLineBfDecodeHandler - channelRead->ʱ����������յ��ͻ��˷��͵�ָ��:qct, counter number:1
         * 23:02:24.212 [nioEventLoopGroup-1-0] INFO  i.n.handler.logging.LoggingHandler - [id: 0xe5335710, /127.0.0.1:50839 => /127.0.0.1:8002] WRITE: 18B
         *          +-------------------------------------------------+
         *          |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
         * +--------+-------------------------------------------------+----------------+
         * |00000000| 32 30 31 39 2d 39 2d 31 38 20 32 33 3a 30 32 3a |2019-9-18 23:02:|
         * |00000010| 32 34                                           |24              |
         * +--------+-------------------------------------------------+----------------+
         * 23:02:24.214 [nioEventLoopGroup-1-0] INFO  N.p.l.TimeServerLineBfDecodeHandler - channelRead->ʱ����������յ��ͻ��˷��͵�ָ��:qct, counter number:2
         * 23:02:24.214 [nioEventLoopGroup-1-0] INFO  i.n.handler.logging.LoggingHandler - [id: 0xe5335710, /127.0.0.1:50839 => /127.0.0.1:8002] WRITE: 18B
         *          +-------------------------------------------------+
         *          |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
         * +--------+-------------------------------------------------+----------------+
         * |00000000| 32 30 31 39 2d 39 2d 31 38 20 32 33 3a 30 32 3a |2019-9-18 23:02:|
         * |00000010| 32 34                                           |24              |
         * +--------+-------------------------------------------------+----------------+
         * 23:02:24.215 [nioEventLoopGroup-1-0] INFO  N.p.l.TimeServerLineBfDecodeHandler - channelRead->ʱ����������յ��ͻ��˷��͵�ָ��:qct, counter number:3
         * 23:02:24.215 [nioEventLoopGroup-1-0] INFO  i.n.handler.logging.LoggingHandler - [id: 0xe5335710, /127.0.0.1:50839 => /127.0.0.1:8002] WRITE: 18B
         *          +-------------------------------------------------+
         *          |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
         * +--------+-------------------------------------------------+----------------+
         * |00000000| 32 30 31 39 2d 39 2d 31 38 20 32 33 3a 30 32 3a |2019-9-18 23:02:|
         * |00000010| 32 34                                           |24              |
         * +--------+-------------------------------------------------+----------------+
         * 23:02:24.215 [nioEventLoopGroup-1-0] INFO  N.p.l.TimeServerLineBfDecodeHandler - channelRead->ʱ����������յ��ͻ��˷��͵�ָ��:qct, counter number:4
         * 23:02:24.218 [nioEventLoopGroup-1-0] INFO  i.n.handler.logging.LoggingHandler - [id: 0xe5335710, /127.0.0.1:50839 => /127.0.0.1:8002] WRITE: 18B
         *          +-------------------------------------------------+
         *          |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
         * +--------+-------------------------------------------------+----------------+
         * |00000000| 32 30 31 39 2d 39 2d 31 38 20 32 33 3a 30 32 3a |2019-9-18 23:02:|
         * |00000010| 32 34                                           |24              |
         * +--------+-------------------------------------------------+----------------+
         * 23:02:24.218 [nioEventLoopGroup-1-0] INFO  N.p.l.TimeServerLineBfDecodeHandler - channelRead->ʱ����������յ��ͻ��˷��͵�ָ��:qct, counter number:5
         * 23:02:24.219 [nioEventLoopGroup-1-0] INFO  i.n.handler.logging.LoggingHandler - [id: 0xe5335710, /127.0.0.1:50839 => /127.0.0.1:8002] WRITE: 18B
         *          +-------------------------------------------------+
         *          |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
         * +--------+-------------------------------------------------+----------------+
         * |00000000| 32 30 31 39 2d 39 2d 31 38 20 32 33 3a 30 32 3a |2019-9-18 23:02:|
         * |00000010| 32 34                                           |24              |
         * +--------+-------------------------------------------------+----------------+
         * 23:02:24.219 [nioEventLoopGroup-1-0] INFO  N.p.l.TimeServerLineBfDecodeHandler - channelRead->ʱ����������յ��ͻ��˷��͵�ָ��:qct, counter number:6
         * 23:02:24.220 [nioEventLoopGroup-1-0] INFO  i.n.handler.logging.LoggingHandler - [id: 0xe5335710, /127.0.0.1:50839 => /127.0.0.1:8002] WRITE: 18B
         *          +-------------------------------------------------+
         *          |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
         * +--------+-------------------------------------------------+----------------+
         * |00000000| 32 30 31 39 2d 39 2d 31 38 20 32 33 3a 30 32 3a |2019-9-18 23:02:|
         * |00000010| 32 34                                           |24              |
         * +--------+-------------------------------------------------+----------------+
         * 23:02:24.220 [nioEventLoopGroup-1-0] INFO  N.p.l.TimeServerLineBfDecodeHandler - channelRead->ʱ����������յ��ͻ��˷��͵�ָ��:qct, counter number:7
         * 23:02:24.220 [nioEventLoopGroup-1-0] INFO  i.n.handler.logging.LoggingHandler - [id: 0xe5335710, /127.0.0.1:50839 => /127.0.0.1:8002] WRITE: 18B
         *          +-------------------------------------------------+
         *          |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
         * +--------+-------------------------------------------------+----------------+
         * |00000000| 32 30 31 39 2d 39 2d 31 38 20 32 33 3a 30 32 3a |2019-9-18 23:02:|
         * |00000010| 32 34                                           |24              |
         * +--------+-------------------------------------------------+----------------+
         * 23:02:24.225 [nioEventLoopGroup-1-0] INFO  N.p.l.TimeServerLineBfDecodeHandler - channelRead->ʱ����������յ��ͻ��˷��͵�ָ��:qct, counter number:8
         * 23:02:24.225 [nioEventLoopGroup-1-0] INFO  i.n.handler.logging.LoggingHandler - [id: 0xe5335710, /127.0.0.1:50839 => /127.0.0.1:8002] WRITE: 18B
         *          +-------------------------------------------------+
         *          |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
         * +--------+-------------------------------------------------+----------------+
         * |00000000| 32 30 31 39 2d 39 2d 31 38 20 32 33 3a 30 32 3a |2019-9-18 23:02:|
         * |00000010| 32 34                                           |24              |
         * +--------+-------------------------------------------------+----------------+
         * 23:02:24.226 [nioEventLoopGroup-1-0] INFO  N.p.l.TimeServerLineBfDecodeHandler - channelRead->ʱ����������յ��ͻ��˷��͵�ָ��:qct, counter number:9
         * 23:02:24.226 [nioEventLoopGroup-1-0] INFO  i.n.handler.logging.LoggingHandler - [id: 0xe5335710, /127.0.0.1:50839 => /127.0.0.1:8002] WRITE: 18B
         *          +-------------------------------------------------+
         *          |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
         * +--------+-------------------------------------------------+----------------+
         * |00000000| 32 30 31 39 2d 39 2d 31 38 20 32 33 3a 30 32 3a |2019-9-18 23:02:|
         * |00000010| 32 34                                           |24              |
         * +--------+-------------------------------------------------+----------------+
         * 23:02:24.226 [nioEventLoopGroup-1-0] INFO  N.p.l.TimeServerLineBfDecodeHandler - channelRead->ʱ����������յ��ͻ��˷��͵�ָ��:qct, counter number:10
         * 23:02:24.227 [nioEventLoopGroup-1-0] INFO  i.n.handler.logging.LoggingHandler - [id: 0xe5335710, /127.0.0.1:50839 => /127.0.0.1:8002] WRITE: 18B
         *          +-------------------------------------------------+
         *          |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
         * +--------+-------------------------------------------------+----------------+
         * |00000000| 32 30 31 39 2d 39 2d 31 38 20 32 33 3a 30 32 3a |2019-9-18 23:02:|
         * |00000010| 32 34                                           |24              |
         * +--------+-------------------------------------------------+----------------+
         * 23:02:24.227 [nioEventLoopGroup-1-0] INFO  N.p.l.TimeServerLineBfDecodeHandler - channelReadComplete ->�������Ӧ���ݣ�2019-9-18 23:02:24
         * 23:02:24.227 [nioEventLoopGroup-1-0] INFO  i.n.handler.logging.LoggingHandler - [id: 0xe5335710, /127.0.0.1:50839 => /127.0.0.1:8002] FLUSH
         */


    }

    @Test
    public void StartTimeClient(){

        //���LineBasedFrameDecoder��StringDecoder����������
        new TimeClient()
                .connect("localhost",8002)
                .addHandlers(new LineBasedFrameDecoder(1024),new StringDecoder(),
                        new TimeClientLineBfDecodeHandler())
                .start();

        /**�ͻ������η���ָ����ͳһ���յ�����˵���Ӧ
         * 23:02:24.106 [nioEventLoopGroup-0-1] INFO  N.T.demoserver.TimeServerHandler - channelActive-> �ͻ���TimeClient��������� localhost/127.0.0.1:8002 ����������
         * 23:02:24.126 [nioEventLoopGroup-0-1] INFO  N.T.demoserver.TimeServerHandler - channelActive->��������˷���ָ��..0
         * 23:02:24.127 [nioEventLoopGroup-0-1] INFO  i.n.handler.logging.LoggingHandler - [id: 0x89429407, /127.0.0.1:50839 => localhost/127.0.0.1:8002] WRITE: 5B
         *          +-------------------------------------------------+
         *          |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
         * +--------+-------------------------------------------------+----------------+
         * |00000000| 71 63 74 0d 0a                                  |qct..           |
         * +--------+-------------------------------------------------+----------------+
         * 23:02:24.166 [nioEventLoopGroup-0-1] INFO  i.n.handler.logging.LoggingHandler - [id: 0x89429407, /127.0.0.1:50839 => localhost/127.0.0.1:8002] FLUSH
         * 23:02:24.167 [nioEventLoopGroup-0-1] INFO  N.T.demoserver.TimeServerHandler - channelActive->��������˷���ָ��..1
         * 23:02:24.168 [nioEventLoopGroup-0-1] INFO  i.n.handler.logging.LoggingHandler - [id: 0x89429407, /127.0.0.1:50839 => localhost/127.0.0.1:8002] WRITE: 5B
         *          +-------------------------------------------------+
         *          |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
         * +--------+-------------------------------------------------+----------------+
         * |00000000| 71 63 74 0d 0a                                  |qct..           |
         * +--------+-------------------------------------------------+----------------+
         * 23:02:24.168 [nioEventLoopGroup-0-1] INFO  i.n.handler.logging.LoggingHandler - [id: 0x89429407, /127.0.0.1:50839 => localhost/127.0.0.1:8002] FLUSH
         * 23:02:24.168 [nioEventLoopGroup-0-1] INFO  N.T.demoserver.TimeServerHandler - channelActive->��������˷���ָ��..2
         * 23:02:24.168 [nioEventLoopGroup-0-1] INFO  i.n.handler.logging.LoggingHandler - [id: 0x89429407, /127.0.0.1:50839 => localhost/127.0.0.1:8002] WRITE: 5B
         *          +-------------------------------------------------+
         *          |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
         * +--------+-------------------------------------------------+----------------+
         * |00000000| 71 63 74 0d 0a                                  |qct..           |
         * +--------+-------------------------------------------------+----------------+
         * 23:02:24.168 [nioEventLoopGroup-0-1] INFO  i.n.handler.logging.LoggingHandler - [id: 0x89429407, /127.0.0.1:50839 => localhost/127.0.0.1:8002] FLUSH
         * 23:02:24.168 [nioEventLoopGroup-0-1] INFO  N.T.demoserver.TimeServerHandler - channelActive->��������˷���ָ��..3
         * 23:02:24.169 [nioEventLoopGroup-0-1] INFO  i.n.handler.logging.LoggingHandler - [id: 0x89429407, /127.0.0.1:50839 => localhost/127.0.0.1:8002] WRITE: 5B
         *          +-------------------------------------------------+
         *          |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
         * +--------+-------------------------------------------------+----------------+
         * |00000000| 71 63 74 0d 0a                                  |qct..           |
         * +--------+-------------------------------------------------+----------------+
         * 23:02:24.169 [nioEventLoopGroup-0-1] INFO  i.n.handler.logging.LoggingHandler - [id: 0x89429407, /127.0.0.1:50839 => localhost/127.0.0.1:8002] FLUSH
         * 23:02:24.169 [nioEventLoopGroup-0-1] INFO  N.T.demoserver.TimeServerHandler - channelActive->��������˷���ָ��..4
         * 23:02:24.169 [nioEventLoopGroup-0-1] INFO  i.n.handler.logging.LoggingHandler - [id: 0x89429407, /127.0.0.1:50839 => localhost/127.0.0.1:8002] WRITE: 5B
         *          +-------------------------------------------------+
         *          |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
         * +--------+-------------------------------------------------+----------------+
         * |00000000| 71 63 74 0d 0a                                  |qct..           |
         * +--------+-------------------------------------------------+----------------+
         * 23:02:24.169 [nioEventLoopGroup-0-1] INFO  i.n.handler.logging.LoggingHandler - [id: 0x89429407, /127.0.0.1:50839 => localhost/127.0.0.1:8002] FLUSH
         * 23:02:24.169 [nioEventLoopGroup-0-1] INFO  N.T.demoserver.TimeServerHandler - channelActive->��������˷���ָ��..5
         * 23:02:24.169 [nioEventLoopGroup-0-1] INFO  i.n.handler.logging.LoggingHandler - [id: 0x89429407, /127.0.0.1:50839 => localhost/127.0.0.1:8002] WRITE: 5B
         *          +-------------------------------------------------+
         *          |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
         * +--------+-------------------------------------------------+----------------+
         * |00000000| 71 63 74 0d 0a                                  |qct..           |
         * +--------+-------------------------------------------------+----------------+
         * 23:02:24.169 [nioEventLoopGroup-0-1] INFO  i.n.handler.logging.LoggingHandler - [id: 0x89429407, /127.0.0.1:50839 => localhost/127.0.0.1:8002] FLUSH
         * 23:02:24.170 [nioEventLoopGroup-0-1] INFO  N.T.demoserver.TimeServerHandler - channelActive->��������˷���ָ��..6
         * 23:02:24.173 [nioEventLoopGroup-0-1] INFO  i.n.handler.logging.LoggingHandler - [id: 0x89429407, /127.0.0.1:50839 => localhost/127.0.0.1:8002] WRITE: 5B
         *          +-------------------------------------------------+
         *          |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
         * +--------+-------------------------------------------------+----------------+
         * |00000000| 71 63 74 0d 0a                                  |qct..           |
         * +--------+-------------------------------------------------+----------------+
         * 23:02:24.173 [nioEventLoopGroup-0-1] INFO  i.n.handler.logging.LoggingHandler - [id: 0x89429407, /127.0.0.1:50839 => localhost/127.0.0.1:8002] FLUSH
         * 23:02:24.173 [nioEventLoopGroup-0-1] INFO  N.T.demoserver.TimeServerHandler - channelActive->��������˷���ָ��..7
         * 23:02:24.173 [nioEventLoopGroup-0-1] INFO  i.n.handler.logging.LoggingHandler - [id: 0x89429407, /127.0.0.1:50839 => localhost/127.0.0.1:8002] WRITE: 5B
         *          +-------------------------------------------------+
         *          |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
         * +--------+-------------------------------------------------+----------------+
         * |00000000| 71 63 74 0d 0a                                  |qct..           |
         * +--------+-------------------------------------------------+----------------+
         * 23:02:24.175 [nioEventLoopGroup-0-1] INFO  i.n.handler.logging.LoggingHandler - [id: 0x89429407, /127.0.0.1:50839 => localhost/127.0.0.1:8002] FLUSH
         * 23:02:24.176 [nioEventLoopGroup-0-1] INFO  N.T.demoserver.TimeServerHandler - channelActive->��������˷���ָ��..8
         * 23:02:24.176 [nioEventLoopGroup-0-1] INFO  i.n.handler.logging.LoggingHandler - [id: 0x89429407, /127.0.0.1:50839 => localhost/127.0.0.1:8002] WRITE: 5B
         *          +-------------------------------------------------+
         *          |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
         * +--------+-------------------------------------------------+----------------+
         * |00000000| 71 63 74 0d 0a                                  |qct..           |
         * +--------+-------------------------------------------------+----------------+
         * 23:02:24.176 [nioEventLoopGroup-0-1] INFO  i.n.handler.logging.LoggingHandler - [id: 0x89429407, /127.0.0.1:50839 => localhost/127.0.0.1:8002] FLUSH
         * 23:02:24.176 [nioEventLoopGroup-0-1] INFO  N.T.demoserver.TimeServerHandler - channelActive->��������˷���ָ��..9
         * 23:02:24.176 [nioEventLoopGroup-0-1] INFO  i.n.handler.logging.LoggingHandler - [id: 0x89429407, /127.0.0.1:50839 => localhost/127.0.0.1:8002] WRITE: 5B
         *          +-------------------------------------------------+
         *          |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
         * +--------+-------------------------------------------------+----------------+
         * |00000000| 71 63 74 0d 0a                                  |qct..           |
         * +--------+-------------------------------------------------+----------------+
         * 23:02:24.176 [nioEventLoopGroup-0-1] INFO  i.n.handler.logging.LoggingHandler - [id: 0x89429407, /127.0.0.1:50839 => localhost/127.0.0.1:8002] FLUSH
         * 23:02:24.229 [nioEventLoopGroup-0-1] INFO  i.n.handler.logging.LoggingHandler - [id: 0x89429407, /127.0.0.1:50839 => localhost/127.0.0.1:8002] RECEIVED: 180B
         *          +-------------------------------------------------+
         *          |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
         * +--------+-------------------------------------------------+----------------+
         * |00000000| 32 30 31 39 2d 39 2d 31 38 20 32 33 3a 30 32 3a |2019-9-18 23:02:|
         * |00000010| 32 34 32 30 31 39 2d 39 2d 31 38 20 32 33 3a 30 |242019-9-18 23:0|
         * |00000020| 32 3a 32 34 32 30 31 39 2d 39 2d 31 38 20 32 33 |2:242019-9-18 23|
         * |00000030| 3a 30 32 3a 32 34 32 30 31 39 2d 39 2d 31 38 20 |:02:242019-9-18 |
         * |00000040| 32 33 3a 30 32 3a 32 34 32 30 31 39 2d 39 2d 31 |23:02:242019-9-1|
         * |00000050| 38 20 32 33 3a 30 32 3a 32 34 32 30 31 39 2d 39 |8 23:02:242019-9|
         * |00000060| 2d 31 38 20 32 33 3a 30 32 3a 32 34 32 30 31 39 |-18 23:02:242019|
         * |00000070| 2d 39 2d 31 38 20 32 33 3a 30 32 3a 32 34 32 30 |-9-18 23:02:2420|
         * |00000080| 31 39 2d 39 2d 31 38 20 32 33 3a 30 32 3a 32 34 |19-9-18 23:02:24|
         * |00000090| 32 30 31 39 2d 39 2d 31 38 20 32 33 3a 30 32 3a |2019-9-18 23:02:|
         * |000000a0| 32 34 32 30 31 39 2d 39 2d 31 38 20 32 33 3a 30 |242019-9-18 23:0|
         * |000000b0| 32 3a 32 34                                     |2:24            |
         * +--------+-------------------------------------------------+----------------+
         */
    }
}
