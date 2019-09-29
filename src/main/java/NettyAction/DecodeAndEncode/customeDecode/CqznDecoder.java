package NettyAction.DecodeAndEncode.customeDecode;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.ArrayList;
import java.util.List;

public class CqznDecoder extends ByteToMessageDecoder {
    /**
     * The length of the message request header that contains the length of the
     * message
     */
    public final static int BASE_LENGTH = 6;

    /** Offset digits for message length */
    private int offsetLength = 0;

    /**
     * Create a new instance Decoder
     *
     * @param offsetLength
     *            Offset length
     */
    public CqznDecoder(int offsetLength) {
        this.offsetLength = offsetLength;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {

        // The readable length must be greater than the base length
        if (buffer.readableBytes() >= BASE_LENGTH) {

            // Record the header of the header
            int beginReader;

            while (true) {

                // Get the header at the beginning of the header
                beginReader = buffer.readerIndex();

                // Mark the header at the beginning of the header
                buffer.markReaderIndex();

                // Read the beginning of the protocol flag, end the while loop
                if (buffer.readByte() == Constant.START_VALUE) {
                    break;
                }

                // Not read the header, skip a byte
                // Every time you skip, one byte, read, and start the header tag
                buffer.resetReaderIndex();
                buffer.readByte();

                // When skipped, after a byte,The length of the packet becomes
                // unsatisfactory. At this point, it should end. Waiting for the
                // following data to arrive
                if (buffer.readableBytes() < BASE_LENGTH) {
                    return;
                }
            }

            // Length
            int length_L = buffer.readByte() & Constant.BYTE_MAX;
            int length_H = buffer.readByte() & Constant.BYTE_MAX;
            int length = length_L + (length_H << 8);

            if (offsetLength != 0) {
                length >>= offsetLength;
            }

            // Length we don't care
            buffer.readByte();
            buffer.readByte();
            buffer.readByte();

            // Determine whether the request packet data is in progress
            if (buffer.readableBytes() < (length + 2)) {

                // Restore read pointer
                buffer.readerIndex(beginReader);
                return;
            }

            // Read data
            byte[] data = new byte[length];
            buffer.readBytes(data);

            // Cs
            byte cs = buffer.readByte();

            // End
            byte end = buffer.readByte();

            if (end != Constant.END_VALUE) {
                return;
            }

            // Output array
            byte[] bytes = getListByte(new byte[] { Constant.START_VALUE, (byte) length_L, (byte) length_H,
                    (byte) length_L, (byte) length_H, Constant.START_VALUE }, data, new byte[] { cs, end });

            // Bytes to @code{ByteBuf}
            ByteBuf buf = Unpooled.buffer();
            buf.writeBytes(bytes);

            // Wrap a @code{ByteBuf} array into a buffer
            out.add(buf);
        }
    }

    private byte[] getListByte(byte[]... bytes) {
        List<byte[]> collect = new ArrayList<byte[]>();
        if (bytes != null) {
            for (int j = 0; j < bytes.length; j++) {
                if (bytes[j] != null) {
                    collect.add(bytes[j]);
                }
            }
        }
        byte[] aa0 = null;
        // Each time the two arrays are merged so the number of merges is
        // collect.size (), the first
        // is the virtual array
        for (int i = 0; i < collect.size(); i++) {
            int aa0Length = aa0 == null ? 0 : aa0.length;
            byte[] aa1 = (byte[]) collect.get(i);
            byte[] c = new byte[aa0Length + aa1.length];
            if (aa0 != null) {
                System.arraycopy(aa0, 0, c, 0, aa0Length);
            }
            System.arraycopy(aa1, 0, c, aa0Length, aa1.length);
            aa0 = c;
        }
        return aa0;
    }

}
