package supportGUI;

import java.io.PrintStream;
import javax.swing.SwingUtilities;

public class Viewer {

    private static String title = "Simovies";
    private static String fileName = HardCodedParameters.defaultParamFileName;

    private static FramedGUI framedGUI;

    public Viewer() {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Viewer.framedGUI = new FramedGUI(Viewer.title);
            }
        });
    }

    private static void readArguments(String[] args) {
        if ((args.length > 0) && (args[0].charAt(0) != '-')) {
            System.err.println("Syntax error: use option -h for help.");
            return;
        }
        for (int i = 0; i < args.length; i++) {
            if (args[i].charAt(0) == '-') {
                if (args[(i + 1)].charAt(0) == '-') {
                    System.err.println("Option " + args[i] + " expects an argument but received none.");
                    return;
                }
                switch (args[i]) {
                    case "-inFile":
                        fileName = args[(i + 1)];
                        break;
                    case "-h":
                        System.out.println("Options:");
                        System.out.println(" -inFile FILENAME: (UNUSED AT THE MOMENT) set file name for input " +
                                        "parameters. Default name is" + HardCodedParameters.defaultParamFileName + ".");
                        break;
                    default:
                        System.err.println("Unknown option " + args[i] + ".");
                        return;
                }
                i++;
            }
        }
    }
}
