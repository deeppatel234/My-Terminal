import java.util.Scanner;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.net.*;



/**
 *
 * @author Deep Patel
 */
public class MyTerminal {

    public static String Path ;
    
    public String getPath()
    {
        return Path;
    }
    
    public void setPath(String tempPath)
    {
        Path = tempPath;
    }
    
    public void pathdisplay()
    {
        System.out.print(Path);
    }
        
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String command;
        
        MyTerminal mt = new MyTerminal();
        newFile nf = new newFile();
        deleteFile df = new deleteFile();
        changDir cd = new changDir();
        showItems si = new showItems();
        mkDir mk = new mkDir();
        Help hp = new Help();
        catFile cf = new catFile();
        moveFile mf = new moveFile();
        copyFile copf = new copyFile();
        //Path = "A:\\MyOSData\\";
        //Path = "A:\\";
        Path = "C:\\";
        do
        {
            mt.pathdisplay();
            command = in.nextLine();
                  
            try
            {
                if (command.equals("exit"))
                    System.out.println("Exit Succesfully.");
                
                else if(command.equals("show"))
                    si.showitems(mt.getPath());
                
           /*    else if(command.equals("clear"))
                {
                    try{
                    Runtime.getRuntime().exec("clear");
                    }catch(IOException e){}
                }           
                    //System.out.flush();
              */  
                else if(command.equals("date"))
                {
                    Date dNow = new Date( );
                    SimpleDateFormat ft = new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
                    System.out.println("Current Date: " + ft.format(dNow));
                }
                
                else if(command.equals("help"))
                    hp.AllHelp();
                
                else if(command.equals("ip"))
                {
                    try
                    {
                         System.out.println("IPV4 Address : " + Inet4Address.getLocalHost().getHostAddress());
                    }
                    catch(UnknownHostException e)
                    {
                          System.out.println("Error..");  
                    }
                }
                
                else if(command.equals("dir"))
                    System.out.println("Workind Directory : " + Path);
                
                else if(command.substring(0, 3).equals("cd "))
                    cd.changeDir(command.substring(3, command.length()));
                              
                else if(command.substring(0, 4).equals("new "))
                    nf.newfile(command.substring(4, command.length()),mt.getPath());
            
                else if(command.substring(0, 4).equals("cat "))
                    cf.catfile(command.substring(4, command.length()),mt.getPath());
                
                else if (command.substring(0, 4).equals("del "))
                    df.deletefile(command.substring(4, command.length()),mt.getPath());
            
                else if(command.substring(0, 5).equals("help "))
                    hp.help(command.substring(5, command.length()));
                
                else if(command.substring(0, 5).equals("copy "))
                    copf.copyfile(mt.getPath(),command.substring(5, command.length()));
                
                else if(command.substring(0, 5).equals("move "))
                    mf.movefile(mt.getPath(),command.substring(5, command.length()));
                                
                else if (command.substring(0, 5).equals("edit "))
                    System.out.println("edit");

                else if (command.substring(0, 5).equals("show "))
                    si.shows(command.substring(5, command.length()),mt.getPath());
                        
                else if(command.substring(0, 6).equals("mkdir "))
                    mk.makedir(command.substring(6, command.length()),mt.getPath());
                        
                else
                    System.out.println("Command not found!!!!!");
            }
            catch(StringIndexOutOfBoundsException e)
            {
                    System.out.println("Command not found!!!!!!!!!!");
            }
        }while(!command.equals("exit"));
        in.close();
    }
}

class newFile
{
    public void newfile(String fname,String path)
    {
        //fname = fname+".txt";
        
        File myFile = new File(path,fname);
        myFile.getParentFile().mkdirs();
        
             
        try
        {
            if(myFile.exists())
            {
                System.out.println("File is exists.");
            }
            else
            {
                myFile.createNewFile();
            }
        }
        catch (IOException e)
        {
                    System.out.println("File Not Created.");
        }
    }
}

class deleteFile
{
                
    public void deletefile(String fname,String path)
    {
        deleteOp(path,fname);
    }
    
    
    
    void deleteOp(String path,String fname)
    {
        File f1 = new File(path,fname);
              
        if(f1.isFile())
        {
            if(f1.exists())
            {
                f1.delete();
                System.out.println(f1.getName() + " is deleted."); 
            }
            else
            {
                System.out.println("File Not Found."); 
            }
        }
        else if (f1.isDirectory())
        {
            String a[] = f1.list();
            for(String temp : a)
                deleteOp(f1.getPath()+ "\\",temp);
            
            if(f1.exists())
            {
                f1.delete();
                System.out.println(f1.getName() + " is deleted."); 
            }
            else
            {
                System.out.println("Directory Not Found."); 
            }
        }
        else
        {
            
        }
    }
}

class catFile
{
    public void catfile(String fname,String path)
    {
        System.out.println("");
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(path+fname));
            String line = null;
            while((line = br.readLine()) != null)
            {
                System.out.println(line);
            }
            br.close();
        }catch(IOException e)
        {
            System.out.println("Invalid File.");
        }
        System.out.println("");
    
    }
}

class changDir
{
    public void changeDir(String cdcom)
    {
        MyTerminal mt = new MyTerminal();
        String path =  mt.getPath();
        int max = 0;
        if(cdcom.equals("."))
        {
            //if(!path.equals("A:\\MyOSData\\"))
            //if(!path.equals("A:\\"))
            if(!path.equals("C:\\"))
            {
                for(int i = path.length()-3 ; i > 0 ; i--)
                {
                    if(path.charAt(i) == '\\' )
                        {
                            max = i;
                            break;
                        }
                }
                path = path.substring(0, max+1);
                mt.setPath(path);
            }
        }
        else if(cdcom.subSequence(0, 2).equals(".."))
        {
            cdcom = cdcom.substring(3, cdcom.length()).toUpperCase();
            if(cdcom.length() == 1)
            {
                path = cdcom + ":\\";
                mt.setPath(path);
            }
            else
            {
                System.out.println("Drive Not Set."); 
            }
        }
        else
        {
            int flag = 0;
            File sf = new File(path);
            String dir[] = sf.list();
        
            for(String temp : dir)
            {
                File tf = new File(path + "\\" + temp);
                if(tf.isDirectory() && temp.equals(cdcom))
                {
                    path = path + cdcom + "\\";
                    mt.setPath(path);
                    flag = 1;
                }
            }
        
            if(flag == 0)
                System.out.println("Directory Not Found."); 
        }
    }
}


class showItems
{
    public void shows(String Comm,String path)
    {
        switch(Comm)
        {
            case "dir":
                showDir(path);
                break;
            case "files":
                showFiles(path);
                break;
            default:
                System.out.println("Command not found!!!!!");
                break;
        }
    }
    
    public void showitems(String path)
    {
        int dir = 0,file = 0;
        File sf = new File(path);
        String All[] = sf.list();
        BasicFileAttributes attr = null;
        for(String temp : All)
        {
            File check = new File(path + "/" + temp);
            
            Path name = check.toPath();

            try
            {
                attr = Files.readAttributes(name, BasicFileAttributes.class);
            }
            catch (IOException exception)
            {
                 System.out.println("Exception handled when trying to get file " +
                                        "attributes: " + exception.getMessage());
            }
            if(check.isDirectory())
            {
                dir++;
                System.out.println(String.format("%30s", attr.creationTime()) + "   <DIR>   " + temp);  
            }
            else
            {
                file++;
                System.out.println(String.format("%30s", attr.creationTime())   + "           " + temp);  
            }
        }
        
        File size = new File(path);
        System.out.println("\n" + String.format("%30s", "Total Directorys : ") + dir + " Dir(s)");
        System.out.println(String.format("%30s", "Total Files : ") + file + " File(s) " );
        System.out.println(String.format("%30s", "Free Space : ") + size.getFreeSpace() + " bytes.");
    }
    
    public void showDir(String path)
    {
        int dir = 0;
        File sf = new File(path);
        String All[] = sf.list();
        BasicFileAttributes attr = null;
        for(String temp : All)
        {
            File check = new File(path + "/" + temp);
            
            Path name = check.toPath();

            try
            {
                attr = Files.readAttributes(name, BasicFileAttributes.class);
            }
            catch (IOException exception)
            {
                 System.out.println("Exception handled when trying to get file " +
                                        "attributes: " + exception.getMessage());
            }
            if(check.isDirectory())
            {
                dir++;
                System.out.println(String.format("%30s", attr.creationTime()) + "   <DIR>   " + temp);  
            }
        }
        
        File size = new File(path);
        System.out.println("\n" + String.format("%30s", "Total Directorys : ") + dir + " Dir(s)");
        System.out.println(String.format("%30s", "Free Space : ") + size.getFreeSpace() + " bytes.");
    }
    
    public void showFiles(String path)
    {
        int file = 0;
        File sf = new File(path);
        String All[] = sf.list();
        BasicFileAttributes attr = null;
        for(String temp : All)
        {
            File check = new File(path + "/" + temp);
            
            Path name = check.toPath();

            try
            {
                attr = Files.readAttributes(name, BasicFileAttributes.class);
            }
            catch (IOException exception)
            {
                 System.out.println("Exception handled when trying to get file " +
                                        "attributes: " + exception.getMessage());
            }
            
            if(check.isFile())
            {
                file++;
                System.out.println(String.format("%30s", attr.creationTime())   + "           " + temp);  
            }
        }
        
        File size = new File(path);
        System.out.println(String.format("%30s", "Total Files : ") + file + " File(s) " );
        System.out.println(String.format("%30s", "Free Space : ") + size.getFreeSpace() + " bytes.");
    }
}

class mkDir
{
    public void makedir(String dname,String path)
    {
        path = path + dname;
        File mk = new File(path);
        mk.mkdir();
    }
}

class copyFile
{
    String FName;
            
    public void copyfile(String path,String comm)
    {
        int temp = 0;
        String NewPath;                
        for(int i = 0 ; i < comm.length() ; i++)
        {
            if(comm.charAt(i) == '|')
            {
                temp = i;
            }
        }
        
        FName = comm.subSequence(0, temp-1).toString();
        NewPath = comm.subSequence(temp+2 , comm.length()).toString();
        path = path + FName;
        
        NewPath += FName;
        
        File mk = new File(NewPath);
        mk.mkdir();
        
        copyOp(path,NewPath);
    }
    
    
    
    void copyOp(String path,String newpath)
    {
        File f1 = new File(path);
        File f2 = new File(newpath);
        
        if(f1.isFile())
        {
            try
            {
                Files.copy(f1.toPath() , f2.toPath());
                System.out.println(f1.getName() + " is copied successful!");
            }
            catch(IOException e)
            {
                System.out.println("File is failed to copy!" + e);
            }
        }
        else if (f1.isDirectory())
        {
            File mk = new File(newpath);
            mk.mkdir();
            
            String a[] = f1.list();
            for(String temp : a)
                copyOp(f1.getPath()+ "\\" + temp,newpath + "\\" + temp);
        }
        else
        {
            
        }
    }
}

class moveFile
{
    String FName;
            
    public void movefile(String path,String comm)
    {
        String Temppath = path;
        int temp = 0;
        String NewPath;                
        for(int i = 0 ; i < comm.length() ; i++)
        {
            if(comm.charAt(i) == '|')
            {
                temp = i;
            }
        }
        
        FName = comm.subSequence(0, temp-1).toString();
        NewPath = comm.subSequence(temp+2 , comm.length()).toString();
        
        
        path = path + FName;
        
        NewPath += FName;
        
        File mk = new File(NewPath);
        mk.mkdir();
        
        moveOp(path,NewPath);
        
             
        File del = new File(Temppath , FName);
        if(del.isDirectory() && del.exists())
        {        
            deleteOp(Temppath, FName);
        }
    }    
    
    
    void deleteOp(String path,String fname)
    {
        File f1 = new File(path,fname);
              
        if (f1.isDirectory())
        {
            String a[] = f1.list();
            for(String temp : a)
                deleteOp(f1.getPath()+ "\\",temp);
            
            if(f1.exists())
            {
                f1.delete();
                
            }
        }
    }
    
    void moveOp(String path,String newpath)
    {
        File f1 = new File(path);
        File f2 = new File(newpath);
        
        if(f1.isFile())
        {
            try
            {
                Files.move(f1.toPath() , f2.toPath());
                System.out.println(f1.getName() + " is moved successful!");
            }
            catch(IOException e)
            {
                System.out.println("File is failed to move!" + e);
            }
        }
        else if (f1.isDirectory())
        {
            File mk = new File(newpath);
            mk.mkdir();
            
            String a[] = f1.list();
            for(String temp : a)
                moveOp(f1.getPath()+ "\\" + temp,newpath + "\\" + temp);
        }
        else
        {
            
        }
    }
}

class Help
{
    public void help(String hname)
    {
        switch(hname)
        {
            case "dir":
                System.out.println("show working directory. \nCommand : DIR");
                break;
            case "cat":
                System.out.println("show the contents of the file. \nCommand : CAT [filename]");
                break;
            case "cd":
                System.out.println("changes the current directory. \n \n"
                        + "Commands : \n"
                        + "CD [directory] \n" +
                          "CD [..] [drive] \n" +
                          "CD [.] \n" + "  ..  Specifies that you want to change to the parent directory. \n" +
                          "  .   Specifies that you want to go back to the current directory. \n"
                          );
                break;
            case "show":
                System.out.println("Displays a list of files and subdirectories in a directory. \n \n" + 
                                   "Command : SHOW  \n"
                                 + "          SHOW dir  \n"
                                 + "          Show files  ");
                break;        
            case "date":
                System.out.println("Display the Date \n \nCommand : DATE");
                break;
            case "mkdir":
                System.out.println("If Command Extensions are enabled MKDIR changes as follows : \n "
                                   + "MKDIR creates any intermediate directories in the path, if needed. \n"
                        + "\nCommand : MKDIR [Directory Name] ");
                break;
            case "new":
                System.out.println("NEW is used to make a new file in current directory \n"
                        + "Command : NEW [filename]");
                break;
            case "del":
                System.out.println("DELETE is used to delete a file in current directory \n"
                        + "Command : DELETE [filename]"  );
                break;
                
            case "edit":
                System.out.println("EDIT is used to edit a text file in current directory \n"
                        + "Command : EDIT [filename]");
                break;
            case "copy":
                System.out.println("COPY is used to copy a file to source to destination. \n"
                        + "Command : COPY [filename] | [destination-path]"  );
                break;
            case "move":
                System.out.println("MOVE is used to move a file to source to destination. \n"
                        + "Command : MOVE [filename] | [destination-path]");
                break;
            default:
                System.out.println("Command Not Found!!!");
                break;
        }
    }
    
    public void AllHelp()
    {
        System.out.println("All Commands : ");
        System.out.println("NEW    : Used to make a new file in current directory");
        System.out.println("EDIT   : Used to edit a text file in current directory");
        System.out.println("DEL : Used to delete a file in current directory");
        System.out.println("DIR    : Show working directory.");
        System.out.println("CD     : Changes the current directory.");
        System.out.println("SHOW   : Displays a list of files and subdirectories in a directory."); 
        System.out.println("DATE   : Display the Date.");
        System.out.println("MKDIR  : Used to make a directory in current directory. ");
        System.out.println("CAT    : Show Content of the file. ");
        System.out.println("COPY   : To move a file to source to destination.");
        System.out.println("MOVE   : To copy a file to source to destination.");
        System.out.println("For more information on a specific command, type HELP [command-name]");
    }
}
