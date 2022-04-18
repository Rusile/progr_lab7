package Rusile.client.NetworkManager;

import Rusile.common.util.Request;
import Rusile.common.util.Serializers;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class RequestSender {
    private static final int SLEEP_TIME = 500;
    private final SocketChannel channel;
    private final Selector selector;

    public RequestSender(SocketChannel channel, Selector selector) {
        this.channel = channel;
        this.selector = selector;
    }

    public void send(Request request) throws InterruptedException, IOException {
        ByteBuffer buffer = Serializers.serializeRequest(request);
        channel.write(buffer);
        channel.register(selector, SelectionKey.OP_READ);
        Thread.sleep(SLEEP_TIME);
    }
}
