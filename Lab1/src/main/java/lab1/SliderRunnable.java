package lab1;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Getter
@Setter
public class SliderRunnable  implements Runnable{
    private int difference;
    private boolean running;

    private final JSlider slider;

    public SliderRunnable(int difference, JSlider slider) {
        this.difference = difference;
        this.slider = slider;
        this.running = true;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            moveSlider();
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void moveSlider() {
        synchronized (slider) {
            if(slider.getValue()>10&&slider.getValue()<90) {
                slider.setValue( slider.getValue()+difference );
            }
        }
    }
}
