package fw.test.unit;

import java.util.concurrent.CountDownLatch;

import com.hunantv.fw.cache.LocalCache;

import junit.framework.TestCase;

class Worker implements Runnable {

    private CountDownLatch start;
    private CountDownLatch end;
    private int id;

    public Worker(CountDownLatch start, CountDownLatch end, final int id) {
        this.start = start;
        this.end = end;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            start.await();
            LocalCache.instance().set("ID" + id, "ID" + id);
            end.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}

public class MultiThreadLocalCacheTest extends TestCase {

    public void testMultiThread() {
        int N = 20;
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(N);
        for (int i = 0; i < N; i++) {
            new Thread(new Worker(start, end, i)).start();
        }
        start.countDown();
        try {
            end.await();
            for (int i = 0; i < N; i++) {
                assertEquals(LocalCache.instance().get("ID" + i), "ID" + i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
