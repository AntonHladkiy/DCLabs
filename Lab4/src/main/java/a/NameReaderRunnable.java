package a;

import java.io.*;

public class NameReaderRunnable implements Runnable {

    private final String phoneNumber;
    protected String fileName;
    protected File inputFile;
    protected BufferedReader bufferedReader;

    public NameReaderRunnable(String fileName, String phoneNumber) {
        this.fileName = fileName;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void run() {
        System.out.println(findFullNameByPhoneNumber(phoneNumber));
    }

    private String findFullNameByPhoneNumber(String phoneNumber) {
        Manager.getLock().readLock().lock();
        try {
            inputFile = new File(fileName);
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
            String readLine;
            String[] splittedLine;
            while ((readLine = bufferedReader.readLine()) != null) {
                splittedLine = readLine.split(" ");
                if (splittedLine[splittedLine.length - 1].equals(phoneNumber)) {
                    bufferedReader.close();
                    Manager.getLock().readLock().unlock();
                    return phoneNumber + "'s full name is " + splittedLine[0]
                            + " " + splittedLine[1] + " " + splittedLine[2];
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Manager.getLock().readLock().unlock();
        return phoneNumber + "'s full name isn't present in the file";
    }
}