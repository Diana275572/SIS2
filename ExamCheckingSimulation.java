public class ExamCheckingSimulation {
    public static void main(String[] args) {
        ExamChecking aide1 = new ExamChecking("Dana", 6);
        ExamChecking aide2 = new ExamChecking("Alibek", 4);

        Thread t1 = new Thread(aide1);
        Thread t2 = new Thread(aide2);

        t1.setName("Dana");
        t2.setName("Alibek");
        t2.setPriority(Thread.MAX_PRIORITY);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted.");
        }

        System.out.println("Process finished with exit code 0");
    }
}
