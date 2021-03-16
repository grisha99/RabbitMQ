package ru.grishchenko.consumer;

import static org.junit.Assert.*;
import org.junit.Test;

public class MessageObjectTest {

    @Test
    public void testMsgType() {
        assertEquals(new MessageObject("").getType(), MessageObject.MsgType.UNDEFINED);
        assertEquals(new MessageObject(" ").getType(), MessageObject.MsgType.UNDEFINED);
        assertEquals(new MessageObject(" a a").getType(), MessageObject.MsgType.UNDEFINED);
        assertEquals(new MessageObject("a a").getType(), MessageObject.MsgType.UNDEFINED);
        assertEquals(new MessageObject("set_topic").getType(), MessageObject.MsgType.SUBSCRIBE);
        assertEquals(new MessageObject(" set_topic").getType(), MessageObject.MsgType.SUBSCRIBE);
        assertEquals(new MessageObject(" set_topic asd").getType(), MessageObject.MsgType.SUBSCRIBE);
        assertEquals(new MessageObject(" set_topic    asd  ").getType(), MessageObject.MsgType.SUBSCRIBE);
        assertEquals(new MessageObject("unset_topic").getType(), MessageObject.MsgType.UNSUBSCRIBE);
        assertEquals(new MessageObject(" unset_topic").getType(), MessageObject.MsgType.UNSUBSCRIBE);
        assertEquals(new MessageObject("exit").getType(), MessageObject.MsgType.EXIT);
    }

    @Test
    public void testMsgKey() {
        assertEquals(new MessageObject("").getKey(), "");
        assertEquals(new MessageObject("a asd").getKey(), "asd");
        assertEquals(new MessageObject("sad     ddd").getKey(), "ddd");
        assertEquals(new MessageObject(" sad     ddd   ").getKey(), "ddd");
        assertEquals(new MessageObject("sad     ddd   asdasd").getKey(), "ddd");
    }
}
