package Rusile.client.NetworkManager;

import Rusile.common.util.Response;
import Rusile.common.util.Serializers;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class ResponseReceiver {
    private final SocketChannel channel;
    private final SelectionKey key;
    private final Selector selector;

    public ResponseReceiver(SocketChannel channel, SelectionKey key, Selector selector) {
        this.channel = channel;
        this.key = key;
        this.selector = selector;
    }

    public Response receive() throws IOException, ClassNotFoundException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer readBuffer = ByteBuffer.allocate(socketChannel.socket().getReceiveBufferSize());
        socketChannel.read(readBuffer);
        channel.register(selector, SelectionKey.OP_WRITE);
        return Serializers.deSerializeResponse(readBuffer.array());
    }
}
