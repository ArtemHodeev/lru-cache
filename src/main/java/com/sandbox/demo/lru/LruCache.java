package com.sandbox.demo.lru;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/*
* Implementation of LRU Cache via Singleton pattern
* */
public class LruCache {
    public HashMap<Integer, CacheNode> cachedItems;
    public CacheNode head;
    public CacheNode tail;
    public int count;

    public static LruCache instance;

    private class CacheNode {
        private CacheNode prev;
        private CacheNode next;
        private String value;

        private CacheNode(){}
        private CacheNode getNext() {
            return next;
        }
        private CacheNode getPrev() {
            return prev;
        }
        private String getValue() {
            return value;
        }
        private void setNext(CacheNode next) {
            this.next = next;
        }
        private void setPrev(CacheNode prev) {
            this.prev = prev;
        }
        private void setValue(String value) {
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

    public static LruCache getInstance() {
        if (instance == null) {
            instance = new LruCache();
        }

        return instance;
    }

    public String getCachedValue(String key) {
        CacheNode actualValue = new CacheNode();

        if (!cachedItems.keySet().contains(key.hashCode())) {
            actualValue.setValue(key);
            actualValue.setNext(head);
            actualValue.setPrev(null);

            add(actualValue);
        } else {
            actualValue = cachedItems.get(key.hashCode());
            remove(actualValue);
            add(actualValue);
        }

        if (count > 5) {
            remove(tail);
        }

        return actualValue.getValue();
    }

    public List<String> getAllCachedValues() {
        ArrayList<String> allCachedValues = new ArrayList<>();
        CacheNode current = head;

        while (current != null) {
            allCachedValues.add(current.getValue());
            current = current.getNext();
        }

        return allCachedValues;
    }

    public CacheNode add(CacheNode cacheNode) {
        cacheNode.setNext(head);
        cacheNode.setPrev(null);
        if (head != null) {
            head.setPrev(cacheNode);
        } else {
            tail = cacheNode;
        }

        head = cacheNode;
        cachedItems.put(cacheNode.getValue().hashCode(), cacheNode);
        count ++;

        return head;
    }

    private CacheNode remove(CacheNode actualValue) {
        CacheNode prevNode = actualValue.getPrev();
        CacheNode nextNode = actualValue.getNext();

        if (prevNode != null) {
            prevNode.setNext(nextNode);
        } else {
            head = nextNode;
        }

        if (nextNode != null) {
            nextNode.setPrev(prevNode);
        } else {
            tail = prevNode;
        }

        cachedItems.remove(actualValue.getValue().hashCode());
        count --;

        return actualValue;
    }

    public void clearCache() {
        while (head != null) {
            remove(tail);
        }
    }

    public static void main(String[] args) {
    }
}
