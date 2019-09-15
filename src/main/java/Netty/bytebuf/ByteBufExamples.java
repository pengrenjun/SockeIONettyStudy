package Netty.bytebuf;

import io.netty.buffer.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ByteProcessor;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Random;

public class ByteBufExamples extends ChannelHandlerAdapter {
    private final static Random random = new Random();
    private static final ByteBuf BYTE_BUF_FROM_SOMEWHERE = Unpooled.buffer(1024);
    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();
    private static ChannelHandlerContext CHANNEL_HANDLER_CONTEXT_FROM_SOMEWHERE =null;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        CHANNEL_HANDLER_CONTEXT_FROM_SOMEWHERE=ctx;
        super.channelRead(ctx, msg);
    }
    /**
     * �����嵥 5-1 ֧������
     */
    public static void heapBuffer() {
        ByteBuf heapBuf = BYTE_BUF_FROM_SOMEWHERE; //get reference form somewhere
        //��� ByteBuf �Ƿ���һ��֧������
        if (heapBuf.hasArray()) {
            //����У����ȡ�Ը����������
            byte[] array = heapBuf.array();
            //�����һ���ֽڵ�ƫ����
            int offset = heapBuf.arrayOffset() + heapBuf.readerIndex();
            //��ÿɶ��ֽ���
            int length = heapBuf.readableBytes();
            //ʹ�����顢ƫ�����ͳ�����Ϊ����������ķ���
            handleArray(array, offset, length);
        }
    }

    /**
     * �����嵥 5-2 ����ֱ�ӻ�����������
     */
    public static void directBuffer() {
        ByteBuf directBuf = BYTE_BUF_FROM_SOMEWHERE; //get reference form somewhere
        //��� ByteBuf �Ƿ�������֧�š�������ǣ�������һ��ֱ�ӻ�����
        if (!directBuf.hasArray()) {
            //��ȡ�ɶ��ֽ���
            int length = directBuf.readableBytes();
            //����һ���µ�������������иó��ȵ��ֽ�����
            byte[] array = new byte[length];
            //���ֽڸ��Ƶ�������
            directBuf.getBytes(directBuf.readerIndex(), array);
            //ʹ�����顢ƫ�����ͳ�����Ϊ����������ķ���
            handleArray(array, 0, length);
        }
    }

    /**
     * �����嵥 5-3 ʹ�� ByteBuffer �ĸ��ϻ�����ģʽ
     */
    public static void byteBufferComposite(ByteBuffer header, ByteBuffer body) {
        // Use an array to hold the message parts
        ByteBuffer[] message =  new ByteBuffer[]{ header, body };

        // Create a new ByteBuffer and use copy to merge the header and body
        ByteBuffer message2 =
                ByteBuffer.allocate(header.remaining() + body.remaining());
        message2.put(header);
        message2.put(body);
        message2.flip();
    }


    /**
     * �����嵥 5-4 ʹ�� CompositeByteBuf �ĸ��ϻ�����ģʽ
     */
    public static void byteBufComposite() {
        CompositeByteBuf messageBuf = Unpooled.compositeBuffer();
        ByteBuf headerBuf = BYTE_BUF_FROM_SOMEWHERE; // can be backing or direct
        ByteBuf bodyBuf = BYTE_BUF_FROM_SOMEWHERE;   // can be backing or direct
        //�� ByteBuf ʵ��׷�ӵ� CompositeByteBuf
        messageBuf.addComponents(headerBuf, bodyBuf);
        //...
        //ɾ��λ������λ��Ϊ 0����һ��������� ByteBuf
        messageBuf.removeComponent(0); // remove the header

    }

    /**
     * �����嵥 5-5 ���� CompositeByteBuf �е�����
     */
    public static void byteBufCompositeArray() {
        CompositeByteBuf compBuf = Unpooled.compositeBuffer();
        //��ÿɶ��ֽ���
        int length = compBuf.readableBytes();
        //����һ�����пɶ��ֽ������ȵ�������
        byte[] array = new byte[length];
        //���ֽڶ�����������
        compBuf.getBytes(compBuf.readerIndex(), array);
        //ʹ��ƫ�����ͳ�����Ϊ����ʹ�ø�����
        handleArray(array, 0, array.length);
    }

    /**
     * �����嵥 5-6 ��������
     */
    public static void byteBufRelativeAccess() {
        ByteBuf buffer = BYTE_BUF_FROM_SOMEWHERE; //get reference form somewhere
        for (int i = 0; i < buffer.capacity(); i++) {
            byte b = buffer.getByte(i);
            System.out.println((char) b);
        }
    }

    /**
     * �����嵥 5-7 ��ȡ��������
     */
    public static void readAllData() {
        ByteBuf buffer = BYTE_BUF_FROM_SOMEWHERE; //get reference form somewhere
        while (buffer.isReadable()) {
            System.out.println(buffer.readByte());
        }
    }

    /**
     * �����嵥 5-8 д����
     */
    public static void write() {
        // Fills the writable bytes of a buffer with random integers.
        ByteBuf buffer = BYTE_BUF_FROM_SOMEWHERE; //get reference form somewhere
        while (buffer.writableBytes() >= 4) {
            buffer.writeInt(random.nextInt());
        }
    }



    /**
     * �����嵥 5-9 ʹ�� ByteBufProcessor ��Ѱ��\r
     *
     * use {@link io.netty.util.ByteProcessor in Netty 4.1.x}
     */

    @Test
    public  void byteBufProcessor() {
        ByteBuf buffer = BYTE_BUF_FROM_SOMEWHERE; //get reference form somewhere
        int index = buffer.forEachByte(ByteBufProcessor.FIND_CR);
        System.out.println(index);
    }

    /**
     * �����嵥 5-10 �� ByteBuf ������Ƭ
     */
    public static void byteBufSlice() {
        Charset utf8 = Charset.forName("UTF-8");
        //����һ�����ڱ�������ַ������ֽڵ� ByteBuf
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        //������ ByteBuf ������ 0 ��ʼ������ 15 ������һ������Ƭ
        ByteBuf sliced = buf.slice(0, 15);
        //����ӡ��Netty in Action��
        System.out.println(sliced.toString(utf8));
        //�������� 0 �����ֽ�
        buf.setByte(0, (byte)'J');
        //����ɹ�����Ϊ�����ǹ���ģ�������һ�������ĸ��Ķ�����һ��Ҳ�ǿɼ���
        assert buf.getByte(0) == sliced.getByte(0);
    }

    /**
     * �����嵥 5-11 ����һ�� ByteBuf
     */
    public static void byteBufCopy() {
        Charset utf8 = Charset.forName("UTF-8");
        //���� ByteBuf �Ա������ṩ���ַ������ֽ�
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        //������ ByteBuf ������ 0 ��ʼ������ 15 �����ķֶεĸ���
        ByteBuf copy = buf.copy(0, 15);
        //����ӡ��Netty in Action��
        System.out.println(copy.toString(utf8));
        //�������� 0 �����ֽ�
        buf.setByte(0, (byte)'J');
        //����ɹ�����Ϊ���ݲ��ǹ����
        assert buf.getByte(0) != copy.getByte(0);
    }

    /**
     * �����嵥 5-12 get()�� set()�������÷�
     */
    public static void byteBufSetGet() {
        Charset utf8 = Charset.forName("UTF-8");
        //����һ���µ� ByteBuf�Ա�������ַ������ֽ�
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        //��ӡ��һ���ַ�'N'
        System.out.println((char)buf.getByte(0));
        //�洢��ǰ�� readerIndex �� writerIndex
        int readerIndex = buf.readerIndex();
        int writerIndex = buf.writerIndex();
        //������ 0 ������ �ڸ���Ϊ�ַ�'B'
        buf.setByte(0, (byte)'B');
        //��ӡ��һ���ַ���������'B'
        System.out.println((char)buf.getByte(0));
        //����ɹ�����Ϊ��Щ�����������޸���Ӧ������
        assert readerIndex == buf.readerIndex();
        assert writerIndex == buf.writerIndex();
    }

    /**
     * �����嵥 5-13 ByteBuf �ϵ� read()�� write()����
     */
    public static void byteBufWriteRead() {
        Charset utf8 = Charset.forName("UTF-8");
        //����һ���µ� ByteBuf �Ա�������ַ������ֽ�
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        //��ӡ��һ���ַ�'N'
        System.out.println((char)buf.readByte());
        //�洢��ǰ��readerIndex
        int readerIndex = buf.readerIndex();
        //�洢��ǰ��writerIndex
        int writerIndex = buf.writerIndex();
        //���ַ� '?'׷�ӵ�������
        buf.writeByte((byte)'?');
        assert readerIndex == buf.readerIndex();
        //����ɹ�����Ϊ writeByte()�����ƶ��� writerIndex
        assert writerIndex != buf.writerIndex();
    }

    private static void handleArray(byte[] array, int offset, int len) {}

    /**
     * �����嵥 5-14 ��ȡһ���� ByteBufAllocator ������
     */
    public static void obtainingByteBufAllocatorReference(){
        Channel channel = CHANNEL_FROM_SOMEWHERE; //get reference form somewhere
        //�� Channel ��ȡһ����ByteBufAllocator ������
        ByteBufAllocator allocator = channel.alloc();
        //...
        ChannelHandlerContext ctx = CHANNEL_HANDLER_CONTEXT_FROM_SOMEWHERE; //get reference form somewhere
        //�� ChannelHandlerContext ��ȡһ���� ByteBufAllocator ������
        ByteBufAllocator allocator2 = ctx.alloc();
        //...
    }

    /**
     * �����嵥 5-15 ���ü���
     * */
    public static void referenceCounting(){
        Channel channel = CHANNEL_FROM_SOMEWHERE; //get reference form somewhere
        //�� Channel ��ȡByteBufAllocator
        ByteBufAllocator allocator = channel.alloc();
        //...
        //�� ByteBufAllocator����һ�� ByteBuf
        ByteBuf buffer = allocator.directBuffer();
        //������ü����Ƿ�ΪԤ�ڵ� 1
        assert buffer.refCnt() == 1;
        //...
    }

    /**
     * �����嵥 5-16 �ͷ����ü����Ķ���
     */
    public static void releaseReferenceCountedObject(){
        ByteBuf buffer = BYTE_BUF_FROM_SOMEWHERE; //get reference form somewhere
        //���ٵ��ö���Ļ���á������ٵ� 0 ʱ���ö����ͷţ����Ҹ÷������� true
        boolean released = buffer.release();
        //...
    }


}
