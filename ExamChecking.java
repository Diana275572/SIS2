import java.text.SimpleDateFormat;
import java.util.Date;

class ExamChecking implements Runnable {
    private static int examSheets = 500;
    private static final int SHEETS_PER_THREAD = 50;
    private static final Object LOCK = new Object();
    private int threadLimit;
    private String name;

    public ExamChecking(String name, int threadLimit) {
        this.name = name;
        this.threadLimit = threadLimit;
    }

    @Override
    public void run() {
        int sheetsChecked = 0;

        for (int i = 0; i < threadLimit; i++) {
            synchronized (LOCK) {
                if (examSheets <= 0) {
                    System.out.println("There is no any exam sheet left! All papers were already checked!");
                    break;
                }
                examSheets -= SHEETS_PER_THREAD;
                sheetsChecked += SHEETS_PER_THREAD;
                System.out.println(name + " checked 50 sheets, at: " + getCurrentTime() +
                        ", exam sheet count is now " + examSheets);
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(name + " was interrupted.");
                break;
            }
        }

        System.out.println(name + " finished checking, at: " + getCurrentTime() +
                ", total sheets checked: " + sheetsChecked);
    }

    private String getCurrentTime() {
        return new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").format(new Date());
    }
}
