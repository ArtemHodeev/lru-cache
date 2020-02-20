package com.sandbox.demo.lru;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LruCache {
    private final static int MAX_SIZE = 5;
    private HashMap<Integer, CacheNode> cachedItems;
    private CacheNode head;
    private CacheNode tail;
    private int count;

    private class CacheNode {
        private CacheNode prev;
        private CacheNode next;
        private String value;

        private CacheNode(){}
        public boolean hasNext() {
            return next != null;
        }
        public boolean hasPrev() {
            return prev != null;
        }
        public CacheNode getNext() {
            return next;
        }
        public CacheNode getPrev() {
            return prev;
        }
        public String getValue() {
            return value;
        }
        public void setNext(CacheNode next) {
            this.next = next;
        }
        public void setPrev(CacheNode prev) {
            this.prev = prev;
        }
        public void setValue(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof CacheNode)) {
                return false;
            }

            if (this == obj) {
                return true;
            }

            CacheNode cacheNode = (CacheNode) obj;
            return this.value.equals(cacheNode.value) && this.next == cacheNode.next && this.prev == cacheNode.prev;
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }
    }

    private LruCache() {
        cachedItems = new HashMap<>();
        count = 0;
    }


    public String getCachedValue(String key) {
        CacheNode actualValue = new CacheNode();

        if (!cachedItems.keySet().contains(key.hashCode())) {
            actualValue.setValue(key);
            actualValue.setNext(head);
            actualValue.setPrev(null);

            if (head != null) {
                head.setPrev(actualValue);
            }

            head = actualValue;
            if (tail == null) {
                tail = head;
            }
            cachedItems.put(actualValue.getValue().hashCode(), actualValue);
            count ++;
        } else {
            actualValue = cachedItems.get(key.hashCode());
            if (actualValue == head) {
                return actualValue.getValue();
            }

            CacheNode prevNode = actualValue.getPrev();
            CacheNode nextNode = actualValue.getNext();
            prevNode.setNext(nextNode);

            if (nextNode != null) {
                nextNode.setPrev(prevNode);
            } else {
                tail = prevNode;
            }

            actualValue.setNext(head);
            actualValue.setPrev(null);
            head.setPrev(actualValue);
            head = actualValue;
        }

        if (count > MAX_SIZE) {
            cachedItems.remove(tail.getValue().hashCode());

            CacheNode tailCandidate = tail.getPrev();
            tailCandidate.setNext(null);
            tail = tailCandidate;
            count --;
        }
        return actualValue.getValue();
    }

    public static void main(String[] args) {
        List<String> samples = Arrays.asList(
                "December", "January", "February",
                "March", "April", "May",
                "June", "July", "August",
                "September", "October", "November");

        LruCache lruCache = new LruCache();

        for (String i: samples) {
            lruCache.getCachedValue(i);
        }

        PrintWriter printWriter = new PrintWriter(System.out);

        CacheNode current = lruCache.head;

        while (current != null) {
            printWriter.println(current.toString());
            current = current.getNext();
        }

        printWriter.println("=================================");
        printWriter.flush();

        lruCache.getCachedValue("December");
        lruCache.getCachedValue("March");
        lruCache.getCachedValue("June");
        lruCache.getCachedValue("June");
        lruCache.getCachedValue("June");
        lruCache.getCachedValue("June");
        lruCache.getCachedValue("February");

        current = lruCache.head;
        while (current != null) {
            printWriter.println(current.toString());
            current = current.getNext();
        }

        printWriter.close();
    }
}
