import java.util.*;
import java.util.concurrent.locks.*;

public class Main {
    public static void main(String[] args) {
        List<String> buffer = new ArrayList<>();
        ReentrantLock bufferLock = new ReentrantLock();

        OurProducer producer = new OurProducer(buffer, ThreadColor.ANSI_CORAL, bufferLock);
        OurConsumer consumer1 = new OurConsumer(buffer, ThreadColor.ANSI_PURPLE, bufferLock);
        OurConsumer consumer2 = new OurConsumer(buffer, ThreadColor.ANSI_PEACH, bufferLock);
        OurConsumer consumer3 = new OurConsumer(buffer, ThreadColor.ANSI_MINT, bufferLock);

        new Thread(producer).start();
        new Thread(consumer1).start();
        new Thread(consumer2).start();
        new Thread(consumer3).start();
    }
}

class OurProducer implements Runnable {
    private final List<String> buffer;
    private final String color;
    private final ReentrantLock bufferLock;
    private final String[] words = {
            "Romantic", "Impressionism", "HipHop", "Baroque", "Minimalism",
            "Cubism", "Modern", "Surrealism", "Symphonic", "Grunge",
            "PopArt", "Expressionist", "Classical", "Abstract", "Techno"
    };

    public OurProducer(List<String> buffer, String color, ReentrantLock bufferLock) {
        this.buffer = buffer;
        this.color = color;
        this.bufferLock = bufferLock;
    }

    public void run() {
        Random random = new Random();
        for (String word : words) {
            try {
                System.out.println(color + "Adding: " + word + ThreadColor.ANSI_RESET);
                bufferLock.lock();
                buffer.add(word);
            } finally {
                bufferLock.unlock();
            }
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                System.out.println("Producer was interrupted");
            }
        }

        try {
            System.out.println(color + "Adding EOF and exiting..." + ThreadColor.ANSI_RESET);
            bufferLock.lock();
            buffer.add("EOF");
        } finally {
            bufferLock.unlock();
        }
    }
}

class OurConsumer implements Runnable {
    private final List<String> buffer;
    private final String color;
    private final ReentrantLock bufferLock;

    public OurConsumer(List<String> buffer, String color, ReentrantLock bufferLock) {
        this.buffer = buffer;
        this.color = color;
        this.bufferLock = bufferLock;
    }

    public void run() {
        while (true) {
            bufferLock.lock();
            try {
                if (buffer.isEmpty()) {
                    continue;
                }
                if (buffer.get(0).equals("EOF")) {
                    System.out.println(color + "Finishing..." + ThreadColor.ANSI_RESET);
                    break;
                } else {
                    String item = buffer.remove(0);
                    System.out.println(color + "Length: " + item.length());
                    StringBuilder reversed = new StringBuilder(item);
                    System.out.println(color + "Reversed: " + reversed.reverse() + ThreadColor.ANSI_RESET);
                }
            } finally {
                bufferLock.unlock();
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
