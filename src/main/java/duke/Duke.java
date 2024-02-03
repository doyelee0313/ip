package duke;
import java.util.Scanner;

public class Duke {
    /**
     * The main method for the Duke program.
     * Reads user commands and actions until the user enters "bye"
     *
     * @param args The command-line
     */
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;

    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (DukeException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public void run() {

        ui.welcome();

        while(true) {

            Scanner sc = new Scanner(System.in);        
            String order = sc.nextLine();
            parser = new Parser(order);
            String[] orders = order.split(" "); 

            if (orders[0].equals("bye")) {
                ui.bye();
                System.exit(0);
            } else if (orders[0].equals("list")) {
                ui.list(tasks.getTasks());
            } else if (orders[0].equals("unmark")) {
                try {
                    int number = parser.parseUnmark(tasks.getSize());
                    tasks.unmark(number);
                    ui.unmark(tasks.getTask(number));
                } catch (DukeException e) {
                    System.out.println(e.getMessage());
                }
            } else if (orders[0].equals("mark")) {
                try {
                    int number = parser.parseMark(tasks.getSize());
                    tasks.mark(number);
                    ui.mark(tasks.getTask(number));
                } catch (DukeException e) {
                    System.out.println(e.getMessage());
                }
            } else if (orders[0].equals("todo")) {  
                try {
                    Task task = parser.parseTodo();
                    tasks.addTask(task);
                    ui.addedMessage(task);
                    ui.totalTask(tasks.getSize());
                    storage.save(tasks.getTasks());
                } catch (DukeException e) {
                    System.out.println(e.getMessage());
                }
            } else if (orders[0].equals("deadline")) {  
                try {
                    Task task = parser.parseDeadline();
                    tasks.addTask(task);
                    ui.addedMessage(task);
                    ui.totalTask(tasks.getSize());
                    storage.save(tasks.getTasks());
                } catch (DukeException e) {
                    System.out.println(e.getMessage());
                }
            } else if (orders[0].equals("event")) {  
                try {
                    Task task = parser.parseEvent();
                    tasks.addTask(task);
                    ui.addedMessage(task);
                    ui.totalTask(tasks.getSize());
                    storage.save(tasks.getTasks());
                } catch (DukeException e) {
                    System.out.println(e.getMessage());
                }
            } else if (orders[0].equals("delete")) {
                try {
                    int number = parser.parseDelete(tasks.getSize());
                    ui.deletedMessage(tasks.getTask(number));
                    tasks.deleteTask(number);
                    ui.totalTask(tasks.getSize());
                    storage.save(tasks.getTasks());
                } catch (DukeException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                ui.dontUnderstand();
            }
        }
    }
    
    public static void main(String[] args) {
        new Duke("/Users/leedoye/ip/src/data/duke_tasks.txt").run();
    }
}



