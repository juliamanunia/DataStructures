package com.miskevich.datastructures;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class AnyBlockingQueueTest {

    @DataProvider(name = "provideQueues")
    public Object[][] provideData() {
        Queue<String> blockingQueueArray = new ArrayBlockingQueue<>(3);
        Queue<String> blockingQueueLinked = new LinkedBlockingQueue<>(3);

        return new Object[][] {
                { blockingQueueArray },
                { blockingQueueLinked }
        };
    }

    @Test(dataProvider = "provideQueues")
    public void testPeekEmptyQueue(Queue<String> blockingQueue){
        assertEquals(blockingQueue.peek(), null);
    }

    @Test(dataProvider = "provideQueues")
    public void testPeekNotEmptyQueue(Queue<String> blockingQueue){
        blockingQueue.push("str0");
        blockingQueue.push("str1");
        assertEquals(blockingQueue.peek(), "str0");
        assertEquals(blockingQueue.size(), 2);
    }

    @Test(dataProvider = "provideQueues")
    public void testPollEmptyQueue(Queue<String> blockingQueue){
        assertEquals(blockingQueue.poll(), null);
    }

    @Test(dataProvider = "provideQueues")
    public void testPollNotEmptyQueue(Queue<String> blockingQueue){
        blockingQueue.push("str0");
        blockingQueue.push("str1");
        assertEquals(blockingQueue.poll(), "str0");
        assertEquals(blockingQueue.size(), 1);
        assertEquals(String.valueOf(blockingQueue), "[str1]");
    }

    @Test(dataProvider = "provideQueues")
    public void testPushEmptyQueue(Queue<String> blockingQueue){
        blockingQueue.push("str0");
        assertEquals(blockingQueue.size(), 1);
        assertEquals(String.valueOf(blockingQueue), "[str0]");
    }

    @Test(dataProvider = "provideQueues")
    public void testPushNotEmptyQueue(Queue<String> blockingQueue){
        blockingQueue.push("str0");
        blockingQueue.push("str1");
        assertEquals(blockingQueue.size(), 2);
        assertEquals(String.valueOf(blockingQueue), "[str0, str1]");
    }

    @Test(expectedExceptions = NullPointerException.class, expectedExceptionsMessageRegExp = "Null values are prohibited in the queue",
            dataProvider = "provideQueues")
    public void testPushNull(Queue<String> blockingQueue){
        blockingQueue.push(null);
    }

    @Test(dataProvider = "provideQueues")
    public void testSizeEmptyQueue(Queue<String> blockingQueue){
        assertEquals(blockingQueue.size(), 0);
    }

    @Test(dataProvider = "provideQueues")
    public void testSizeNotEmptyQueue(Queue<String> blockingQueue){
        blockingQueue.push("str0");
        assertEquals(blockingQueue.size(), 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Capacity of the queue can't be <= 0")
    public void testInvalidCapacityArrayQueue(){
        new ArrayBlockingQueue<>(0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Capacity of the queue can't be <= 0")
    public void testInvalidCapacityLinkedQueue(){
        new LinkedBlockingQueue<>(-1);
    }

}