package deliveroo.cronparser;

//5 4 * * sun
//0 0,12 1 */2 *
/*
    Tasks left:
        ----------- Writing test suites - Done
        - Writing validations, custom exceptions
        - Writing comments, javadocs
        - Checking how to build the project using command line and creating the README file
        ----------- Handling a few more corner cases around interval parser - Done
        ----------- Change CronExpression -> CronString, so subExpression can change to just expression -> Done
        - Code cleanup etc
 */
public class Main {
    public static void main(String[] args) {
        RawCronString cronString = new RawCronString(args[0]);
        SimplifiedCronString simplifiedCronString = cronString.simplifyCronString();

        System.out.println(simplifiedCronString.toString());
    }
}
