package it.imtlucca.lecture3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ImmutableColor {
    // Values must be between 0 and 255.
    final private int red;
    final private int green;
    final private int blue;
    final private String name;

    private void check(int red,
                       int green,
                       int blue) {
        if (red < 0 || red > 255
                || green < 0 || green > 255
                || blue < 0 || blue > 255) {
            throw new IllegalArgumentException();
        }
    }

    public ImmutableColor(int red,
                          int green,
                          int blue,
                          String name) {
        check(red, green, blue);
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.name = name;
    }


    public int getRGB() {
        return ((red << 16) | (green << 8) | blue);
    }

    public String getName() {
        return name;
    }

    public ImmutableColor invert() {
        return new ImmutableColor(255 - red,
                255 - green,
                255 - blue,
                "Inverse of " + name);
    }

    public static void main(String[] args) throws InterruptedException {
        ImmutableColor color = new ImmutableColor(0, 0, 0, "Pitch Black");
        ExecutorService executor = Executors.newCachedThreadPool();

        executor.execute(() -> {
            int myColorInt = color.getRGB();      //S1
            System.out.println(myColorInt);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String myColorName = color.getName(); //S2
            System.out.println(myColorName);
        });

        executor.execute(() -> {
            synchronized (color) {
                color.invert();
            }
        });
        executor.shutdown();
        executor.awaitTermination(1000, TimeUnit.MILLISECONDS);
    }
}
