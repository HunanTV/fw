package com.hunantv.fw.view;


import com.hunantv.fw.utils.StreamUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

/**
 * Created by thomas on 15/3/24.
 *
 * A BytesView is used for rendering stream bytes into the http response.
 *
 * Usage: three method constructing the BytesView except use <code>new</code> key
 * <br/> directly:
 * <p>
 *     1: Use fromBytes method: <br/>
 *      <code>
 *          byte[] bytes = new byte[] {1,2,3,4};
 *          BytesView view = BytesView.fromBytes(bytes);
 *      </code>
 *
 *      2: Use fromInputStream method: <br/>
 *      <code>
 *          InputStream is = Some where return a InputStream;
 *          BytesView view = BytesView.fromInputStream(is);
 *      </code>
 *
 *      3: Use ByteArrayOutputStream method: <br/>
 *      <code>
 *          ByteArrayOutputStream ot = Some where return a ByteArrayOutputStream;
 *          BytesView view = BytesView.fromByteArrayOutputStream(ot);
 *      </code>
 * </p>
 *
 */
public class BytesView implements View {
    private byte[] bytes;

    protected BytesView(byte[] bytes) {
        this.bytes = bytes;
    }

    /**
     * Constructing from InputStream.
     *
     * @param is
     * @return
     * @throws IOException
     */
    public static BytesView fromInputStream(InputStream is) throws IOException {
        final byte[] bytes = StreamUtils.copyToByteArray(is);

        return fromBytes(bytes);
    }

    /**
     * Constructing from ByteArrayOutputStream
     * @param out
     * @return
     */
    public static BytesView fromByteArrayOutputStream(ByteArrayOutputStream out) {
        final byte[] bytes = out.toByteArray();

        return fromBytes(bytes);
    }

    @Override
    public byte[] getBytes() {
        return bytes;
    }

    /**
     * Constructing from raw bytes.
     * @param bytes
     * @return
     */
    public static BytesView fromBytes(byte[] bytes) {
        return new BytesView(bytes);
    }


    /**
     * Set this view type as stream view.
     * @return
     */
    @Override
    public boolean isStreamView() {
        return true;
    }

    @Override
    public String render() {
        return null; /*Method ignored*/
    }

    @Override
    public void renderTo(Writer out) throws IOException {
        /* Method ignored */
    }

    @Override
    public Object getV() {
        return null; /*Method ignored*/
    }
}
