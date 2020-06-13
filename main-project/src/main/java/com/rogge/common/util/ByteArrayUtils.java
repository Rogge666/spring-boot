package com.rogge.common.util;

import java.io.*;

/**
 * [Description]
 * <p>
 * [How to use]
 * <p>
 * [Tips]
 *
 * @author Created by Rogge on 2020-05-23.
 * @since 1.0.0
 */
public class ByteArrayUtils {

    public static <T> byte[] objectToBytes(T obj) {
        byte[] bytes = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream sOut = null;
        try {
            sOut = new ObjectOutputStream(out);
            sOut.writeObject(obj);
            sOut.flush();
            bytes = out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sOut != null) {
                try {
                    sOut.close();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return bytes;
    }

    public static <T> T bytesToObject(byte[] bytes) {
        T t = null;
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ObjectInputStream sIn = null;
        try {
            sIn = new ObjectInputStream(in);
            t = (T) sIn.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sIn != null) {
                try {
                    in.close();
                    sIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return t;
    }
}
