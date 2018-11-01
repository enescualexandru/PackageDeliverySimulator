import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Courier implements Runnable {
    private List<Package> packagesToDeliver;
    private String destination;
    private int distance;
    private CountDownLatch latch;

    Courier(List<Package> packagesToDeliver, String destination, int distance, CountDownLatch latch) {
        this.packagesToDeliver = packagesToDeliver;
        this.destination = destination;
        this.distance = distance;
        this.latch = latch;
    }

    /**
     * Simulates the actions during the 'delivery' of the packages
     */
    @Override
    public void run() {
        Random random = new Random();
        String name = "Courier " + Thread.currentThread().getName().substring(7);
        try {
            Thread.sleep(random.nextInt(10) * 300);
        } catch (InterruptedException e) {
            System.out.println("Ops ! I had an accident before to start towards " + destination);
            e.getMessage();
        }
        System.out.println(name + " .. starts delivering " + packagesToDeliver.size() + " packages to " + destination);
        try {
            Thread.sleep(distance * 1000);
        } catch (InterruptedException e) {
            System.out.println("Ops ! I had an accident on the way trying to deliver to " + destination);
            e.getMessage();
        }

        System.out.println(name + " .. delivered " + packagesToDeliver.size() + " packages to " + destination);

        //signals latch that the delivery was done, decrementing it by one
        latch.countDown();
    }
}
