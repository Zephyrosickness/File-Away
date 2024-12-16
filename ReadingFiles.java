//Allows us to use the FileChooser wizard GUI to pick files
import javax.swing.*;
//Needed imports for working w/ IO (input/output)
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardOpenOption.CREATE;

public class  ReadingFiles {
    //need to add the "throws IOException" after typical main phrase
    public static void main(String[] args) throws IOException   {


        JFileChooser chooser = new JFileChooser();
        try {
            File workingDirectory = new File(System.getProperty("user.dir")); //this is the current directory
            chooser.setCurrentDirectory(workingDirectory); //sets the default directory to the current one
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                File selectedFile = chooser.getSelectedFile(); //sets selected selectedFilePath to what the user clicked
                Path selectedFilePath = selectedFile.toPath(); //this is the path of the selected selectedFilePath

                InputStream in = new BufferedInputStream(Files.newInputStream(selectedFilePath, CREATE)); //u need this to create a reader: this allows all bytes to be read
                BufferedReader reader = new BufferedReader(new InputStreamReader(in)); //this is the actual reader

                /*
                    Everything starting here is what is actually displayed back to the user
                    This is typically where you will make changes.
                */

                System.out.println("File Path: " + selectedFilePath);
                int line = 0;
                String rec = ""; //rec holds what the reader finds on the line
                int words = 1;
                int chars = 0;
                int spaces = 0;

                //Moving through selectedFilePath, reading, and printing each line of the selected selectedFilePath
                while (reader.ready()) {
                    rec = reader.readLine();
                    line++;
                    for(char currentChar:rec.toCharArray()){
                        if(currentChar==' '){spaces++;}
                        chars++;
                    }
                    System.out.printf("\nLine %d: %s ", line, rec); //Prints the line # and the contents of the line
                }
                reader.close(); // must close the selectedFilePath to seal it and clear buffer

                words = line+spaces;
                int charsWithoutSpaces = chars-spaces;
                System.out.println("\n\nData read!"); //Success message
                System.out.println("\nLINES: "+line+"\nWORDS: "+words+"\nSPACES: "+spaces+"\nCHARACTERS: "+chars+"\nCHARACTERS (excluding spaces): "+charsWithoutSpaces);


                //we are in try/catch land now after this line
            }else{
                //This else statement is hit when the user closes the JFileChooser Wizard without selecting file
                System.out.println("File not selected. Please restart program.");
                System.exit(0);
            }
        }
        //This catch block is hit when the user file the user attempts to open a file that can not be found
        catch (FileNotFoundException e) {
            System.out.println("File not found!");
            e.printStackTrace();
        }
        //This catch block is hit for all other IO Exceptions
        catch (IOException e) {e.printStackTrace();}
    }
}