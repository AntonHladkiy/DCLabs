package lab1.A;
import lab1.SliderRunnable;
import javax.swing.*;

public class TaskA {
    private static final JSlider slider = new JSlider();

    private static final Thread thread10 = new Thread(new SliderRunnable(-1, slider));
    private static final Thread thread90 = new Thread(new SliderRunnable(1, slider));

    public static void main(String[] args) {

        JFrame frame = new JFrame("Lab1 Part lab1.A");
        slider.setEnabled(false);
        slider.setBounds(85, 30, 220, 20);


        JButton startButton = new JButton("Start");
        startButton.setBounds(150, 70, 90, 30);
        startButton.addActionListener(e -> startThreads());

        JLabel thread10PriorityLabel = new JLabel("10 Priority = " + thread10.getPriority());
        thread10PriorityLabel.setBounds(10, 70, 90, 30);

        JLabel thread90PriorityLabel = new JLabel("90 Priority = " + thread90.getPriority());
        thread90PriorityLabel.setBounds(300, 70, 90, 30);


        JSlider Thread10PrioritySlider = new JSlider();
        Thread10PrioritySlider.setBounds(10, 110, 150, 30);
        Thread10PrioritySlider.setMaximum( 10 );
        Thread10PrioritySlider.setValue( 5 );
        Thread10PrioritySlider.setMinimum( 1 );
        Thread10PrioritySlider.addChangeListener(e -> {
            changeThreadPriority(Thread10PrioritySlider,thread10);
            thread10PriorityLabel.setText("10 Priority = " + thread10.getPriority());
        });

        JSlider Thread90PrioritySlider = new JSlider();
        Thread90PrioritySlider.setBounds(230, 110, 150, 30);
        Thread90PrioritySlider.setMaximum( 10 );
        Thread90PrioritySlider.setValue( 5 );
        Thread90PrioritySlider.setMinimum( 1 );
        Thread90PrioritySlider.addChangeListener(e -> {
            changeThreadPriority(Thread90PrioritySlider,thread90);
            thread90PriorityLabel.setText("90 Priority = " + thread90.getPriority());
        });

        frame.add(startButton);
        frame.add(thread10PriorityLabel);
        frame.add(thread90PriorityLabel);
        frame.add(Thread90PrioritySlider);
        frame.add(Thread10PrioritySlider);
        frame.add(slider);
        frame.setSize(400, 300);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    private static void startThreads() {
        thread10.setDaemon(true);
        thread90.setDaemon(true);
        thread10.start();
        thread90.start();
    }

    private static void changeThreadPriority(JSlider slider,Thread thread) {
        thread.setPriority( slider.getValue() );
    }
}
