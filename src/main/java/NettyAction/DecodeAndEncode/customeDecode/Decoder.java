package NettyAction.DecodeAndEncode.customeDecode;

import com.emrubik.codec.generator.convertor.Convertor;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Description 抚河流量计报文解码
 * @Date 2019/9/25 0025 上午 11:45
 * @Created by Pengrenjun
 */
public class Decoder extends ByteToMessageDecoder {


    private static final int LEN_OFFSET = 1;

    private static final int LEN_LENGTH = 2;
    private static Logger logger = LoggerFactory.getLogger(Decoder.class);
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < LEN_OFFSET + LEN_LENGTH) return;

        in.markReaderIndex();

        byte[] l = new byte[LEN_LENGTH];
        in.getBytes(in.readerIndex() + LEN_OFFSET, l);
        int dataLen = Convertor.reverseBytesToUshort(l) >> 2;

        int frameLen = dataLen + 8;

        if (in.readableBytes() < frameLen) {
            in.resetReaderIndex();
            return;
        }

        if (in.getByte(6) == (byte) 0xc9 && in.getByte(16) == 0x01) {  // 登陆帧有额外8个字节
            frameLen += 8;
            if (in.readableBytes() < frameLen) {
                in.resetReaderIndex();
                return;
            }
        }
        int readerIndex = in.readerIndex();
        ByteBuf frame = in.retain(readerIndex);
        in.readerIndex(readerIndex + frameLen);
        out.add(frame);
//        logger.info("revice msg:"+HexDumper.getHexdump(r));
    }
}
