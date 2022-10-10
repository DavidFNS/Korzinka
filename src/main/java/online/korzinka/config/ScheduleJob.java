package online.korzinka.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
@EnableScheduling
public class ScheduleJob {
    private FileOutputStream outputStreamDelay ;
    private FileOutputStream outputStreamRate;

    public ScheduleJob() throws FileNotFoundException {
        this.outputStreamDelay =  new FileOutputStream("src/main/resources/templates/scheduleDelay.txt", true);
        this.outputStreamRate = new FileOutputStream(
                    getClass().getClassLoader().getResource("templates/scheduleRate.txt").getPath(), false);
    }

//    {
//        try {
//            outputStreamDelay = new FileOutputStream(
//                    getClass().getClassLoader().getResource("scheduleDelay.txt").getPath(), true);
//            outputStreamRate = new FileOutputStream(
//                    getClass().getClassLoader().getResource("scheduleRate.txt.txt").getPath(), true);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    @Scheduled(fixedDelay = 5000)
//    public void delay() throws InterruptedException, IOException {
//        String s = "fixedDelay: " + new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(new Date());
//        outputStreamDelay.write(s.getBytes());
//        outputStreamDelay.write(System.lineSeparator().getBytes());
//        outputStreamDelay.flush();
//
//        System.out.println(s);
//        Thread.sleep(1000*2);
//    }

//    @Async
//    @Scheduled(fixedRate = 3000)
//    public void rate() throws InterruptedException, IOException {
//        String s = "fixedRate: " + new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(new Date());
//        outputStreamRate.write(s.getBytes());
//        outputStreamRate.write(System.lineSeparator().getBytes());
//        outputStreamRate.flush();
//
//        System.out.println(s);
//        Thread.sleep(1000);
//    }

    @Scheduled(cron = "* 30 12 8-15 * *")
    public void cron3(){
        System.out.println("Cron(Task3): " + new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(new Date()));
    }

    @Scheduled(cron = "* */10 */2 16 * *")
    public void cron2(){
        System.out.println("Cron(Task(2)): " + new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(new Date()));
    }
}
