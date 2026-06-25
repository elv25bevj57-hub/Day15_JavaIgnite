class Classroom {
    private boolean attendanceStarted = false;

    public synchronized void waitForAttendance() {
        while (!attendanceStarted) {
            try {
                System.out.println("Student Waiting...");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Student Marked Present");
    }

    public synchronized void startAttendance() {
        attendanceStarted = true;
        System.out.println("Teacher Started Attendance");
        notifyAll();
    }
}

class TeacherThread extends Thread {
    private Classroom classroom;

    public TeacherThread(Classroom classroom) {
        this.classroom = classroom;
    }

    public void run() {
        classroom.startAttendance();
    }
}

class StudentThread extends Thread {
    private Classroom classroom;

    public StudentThread(Classroom classroom) {
        this.classroom = classroom;
    }

    public void run() {
        classroom.waitForAttendance();
    }
}

public class Main {
    public static void main(String[] args) {
        Classroom classroom = new Classroom();

        StudentThread s1 = new StudentThread(classroom);
        StudentThread s2 = new StudentThread(classroom);
        StudentThread s3 = new StudentThread(classroom);

        TeacherThread teacher = new TeacherThread(classroom);

        s1.start();
        s2.start();
        s3.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        teacher.start();
    }
}
