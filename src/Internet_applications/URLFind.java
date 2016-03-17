package Internet_applications;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
//import javax.swing.text.html.parser.Parser;
//import javax.swing.text.html.parser.DocumentParser;
//import java.sql.Array;
//import java.time.LocalDate;

import java.time.LocalDateTime;
import java.net.URLDecoder;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
//import java.util.Arrays;
import java.util.EmptyStackException;
import javax.swing.JCheckBox;
//import javax.swing.text.html.parser.DTD;
import org.jsoup.Jsoup;
import org.jsoup.parser.HtmlTreeBuilder;
import org.jsoup.parser.Parser;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Permission;
import java.security.cert.CertStore;
import java.time.LocalDate;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.nodes.Attributes;
import org.jsoup.parser.XmlTreeBuilder;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nickm
 */
public class URLFind extends JFrame implements ActionListener 
{
private JFrame JLang;
private String URL;
//private int entrynum=0;
 private String connect="Connected to: "; 
private JComboBox Lang;
private final JLabel address;
private JTextField txtURL;
private JButton btngo;
private JButton btnback;
private JButton btnforward;
private JCheckBox SLO;
private JButton btnsettings;
//private LocalDate today;
    
    
    //ArrayList to store HTML content
    ArrayList<String> contents=new ArrayList();
      
    // Get methods
    public String getURL()
    {
        return URL;
    }
    Forward F=new Forward();
    Back B=new Back();
    Settings S=new Settings();
   Stack<String> back=new Stack();
   Stack<String> FWD=new Stack();
   StringBuilder b=new StringBuilder();
    //set constants
    private final int HEIGHT=260;
    private final int WIDTH=315;
    
private	String entry="Type a Web Address Here !";

    public URLFind()
    {
         
    super("Internet Browser");
    
   
   
    // set size of frame
	setSize(WIDTH, HEIGHT);
        
        //Make X close the program
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setResizable(false);
      
        //Set layout type
	setLayout(new FlowLayout());
       
        //Window for Language Settings
       JLang=new JFrame("Settings");
       JLang.setResizable(false);
       
       Lang=new JComboBox();
       Lang.addItem("English");
       Lang.addItem("Slovensko");
       Lang.addItem("Deutsch");
       JLang.add(Lang);
     
    //Setup Address label
    address= new JLabel("Address: ");
    add(address); 
   
    
   // Create and Add TextField Object to Frame
    txtURL=new JTextField(entry,27);
    //Highlight text
    txtURL.selectAll();
    //Assign Inputed text to variable (URL) 
    add(txtURL);
    
   //Create / Add Connect Button
   btngo= new JButton("GO");
   btngo.setForeground(Color.BLACK);
   btngo.setBackground(Color.GREEN);
   btngo.setToolTipText("Connect to a Web page");
   Font myFont = new Font("Arial", Font.BOLD | Font.ITALIC, 20);
     btngo.setFont(myFont);
      btngo.addActionListener(this); 
     
   // back and forward buttons
   btnback=new JButton("Back");
   btnback.setToolTipText("Takes you to your previous webpage");
   btnback.addActionListener(B);
   btnback.setEnabled(false);
   add(btnback);
 
    add(btngo);
    
   //Create checkbox to allow user to change the language
    SLO=new JCheckBox("Slovensko");
    SLO.setToolTipText("Change browser language to Slovenian ");
    
  
   //add forward button
   btnforward=new JButton("Forward");
   btnforward.setToolTipText("Advances to the web page forward");
   btnforward.addActionListener(F);
   btnforward.setEnabled(false);
   add(btnforward);
   //add(SLO);
   
   btnsettings=new JButton("Settings");
   btnsettings.setToolTipText("Access your Settings");
   btnsettings.addActionListener(S);
   add(btnsettings);
   //add(Lang);
//Make the Frame Visible
   setVisible(true);
  
}
   
 // Actions to happen when Connect button (click)occurs
@Override
public void actionPerformed(ActionEvent e)
{ 
 createBufferStrategy(200);
 String www="www.";
 String http="http://";
 String content;
 back.ensureCapacity(1);
     if(!b.toString().isEmpty())
     {
      back.push(b.toString());
     }
      
 
      
    if(SLO.isSelected())
    {
        btnback.setText("Nazaj");
        btnforward.setText("Naprej");
        btngo.setText("Najdit");
        connect="Obklučen v: ";
        
    }else
    {
        btnback.setText("Back");
        btnforward.setText("Forward");
        btngo.setText("GO");
        connect="Connected to: ";
    }
    
    
   //Assign a Varible to the input of the JTextField 
    URL=txtURL.getText();
    //What if user types www.example.com
        //https://www.www.example.com
   if(URL.equalsIgnoreCase(http)||URL.equalsIgnoreCase("https://"))
   {
       http="";
   }
   if(URL.equalsIgnoreCase("facebook.com")||URL.equalsIgnoreCase("yahoo.com")||URL.equalsIgnoreCase("youtube.com")||URL.equalsIgnoreCase("nra.org"))
   {
       http="https://";
   }
     if(URL.contains(www))
    {
        www="";
    }
   
    if(URL.equals(entry))
    {
        URL="GOOGLE.com";
    }
    //Create a larger String to bring it all together
    //StringBuilder is more efficent
    b.delete(0,b.length());
    
    b.append(http);
    b.append(www);
    b.append(URL);
    //webaddress=http+www+URL;
  
   //  System.out.println(webaddress);
 
    // Create URL object to connect to a particular webpage using URL variable
        try
       {
       // Create URL and URLConnection objects to make a connection with a page    
       URL myURL=new URL(b.toString());
       
        URLConnection URLConnect = myURL.openConnection();
        btngo.setForeground(Color.BLACK);
        btngo.setBackground(Color.red);
        btngo.setText("Connecting");
       // JOptionPane.showMessageDialog(null, back.capacity());
       // JOptionPane.showMessageDialog(null,FWD.capacity());
        //Use a large number to set Timeout time
            URLConnect.setReadTimeout(999999999);
                
             URLConnect.connect();
                          
             this.setTitle(b.toString());
           if(!back.isEmpty())
           {
               btnback.setEnabled(true);
              
           }
           else
           {
             btnback.setEnabled(false);
           }
        JOptionPane.showMessageDialog(null,connect+b.toString());
            
      
         //   Read from webpage         
          BufferedReader in = new BufferedReader(new InputStreamReader(myURL.openStream()));
       // While loop reads every line of HTML stores it in a variable and then closes the reader
          Path filepath=Paths.get("TEST.html");
          BufferedWriter write = new BufferedWriter(new FileWriter(filepath.toFile()));
        while (( content= in.readLine()) != null)
        {
            write.write(content);
            write.newLine();
            contents.add(content);
            System.out.println(content); 
            contents.add("\n");
            
        }
        if(contents.toString().equals("[]"))
        {
            http="https://";
            b.delete(0,b.length());
            
            b.append(http);
            b.append(www);
            b.append(URL);
            
            URL U =new URL(b.toString());
              URLConnection URLC = U.openConnection();
              URLC.setReadTimeout(999999999);
              URLC.connect();
              JOptionPane.showMessageDialog(null, connect+b.toString());
               
                //   Read from webpage         
          BufferedReader read = new BufferedReader(new InputStreamReader(U.openStream()));
       // While loop reads every line of HTML stores it in a variable and then closes the reader
          Path file=Paths.get("TEST.html");
          BufferedWriter BW = new BufferedWriter(new FileWriter(file.toFile()));
          
        while ((content = read.readLine()) != null)
        {
            BW.write(content);
            BW.newLine();
            contents.add(content);
            System.out.println(content); 
            contents.add("\n");
            
        }
        BW.close();
        read.close();
        }
       
       //URLDecoder.decode(www);
        write.close();
        in.close();
        System.out.println("Done!!");
       System.out.println();
       System.out.println(contents.toString());
       btngo.setText("GO");
       btngo.setBackground(Color.GREEN);
       btngo.setForeground(Color.BLACK);
      //System.out.println(content);
      
        //Whitelist WL=new Whitelist();
   
    //HtmlTreeBuilder H = new HtmlTreeBuilder();
        
       //  Cleaner C=new Cleaner(WL);
            
      //   Parser p=new Parser(H);
         
       //Parse the String of HTML into Document object
     //  p.parseInput(contents.toString(),URL);
       contents.removeAll(contents);
     
     
       System.gc();      
    }catch(MalformedURLException q)
    {
    JOptionPane.showMessageDialog(null,q.getMessage());
         
    }catch(EmptyStackException |    IOException g)
    {
      JOptionPane.showMessageDialog(null,g.getMessage());
      txtURL.setText(entry);
      txtURL.selectAll();
      this.setTitle("Internet Browser");
      btngo.setText("GO");
      btngo.setBackground(Color.red);
      
     }catch(HeadlessException x)
     {
      JOptionPane.showMessageDialog(null, x.getMessage());
         
     }catch(NullPointerException g)
    {
        JOptionPane.showMessageDialog(null,"null");  
    }catch(Exception z)
    {
        JOptionPane.showMessageDialog(null,z.toString());
       
    }
   }   
class Back implements ActionListener
{
    String last="";
         
        @Override
        public void actionPerformed(ActionEvent e) 
        {
           FWD.ensureCapacity(1);   
          e.setSource(btnback);
        //  URLFind UF =new URLFind();
          
          if(!last.isEmpty())
          {
            FWD.push(last);
          }
            // Create URL object to connect to a particular webpage using URL variable
       
            try
       {
       // Create URL and URLConnection objects to make a connection with a page    
       URL myURL=new URL(back.peek());
        btngo.setBackground(Color.RED);
        btngo.setText("Connecting");
        URLConnection URLConnect = myURL.openConnection();
           //Permission t= myURLConnection.getPermission(); 
          // t.checkGuard(myURLConnection);
        //Use a large number to set Timeout time
            URLConnect.setReadTimeout(999999999);
          
             URLConnect.connect();
          
            // btnforward.setEnabled(true);
                if(!FWD.empty())
                {
                    btnforward.setEnabled(true);
                }
                else
                {
                    btnforward.setEnabled(false);
                }
                JOptionPane.showMessageDialog(null,connect+back.peek());
                
                  BufferedReader in = new BufferedReader(new InputStreamReader(myURL.openStream()));
       // While loop reads every line of HTML stores it in a variable and then closes the reader
          Path filepath=Paths.get("TEST.html");
          BufferedWriter write = new BufferedWriter(new FileWriter(filepath.toFile()));
      String content="";
        while ((content = in.readLine()) != null)
        {
            write.write(content);
            write.newLine();
            contents.add(content);
            System.out.println(content); 
            contents.add("\n");
        }
        in.close();
        write.close();
        contents.removeAll(contents);
        btngo.setText("GO");
        btngo.setBackground(Color.GREEN);
        if(back.isEmpty())
        {
            btnback.setEnabled(false);
        }
        else
        {
            btnback.setEnabled(true);
        }
            last=back.pop();
            txtURL.selectAll();
           
             System.gc();
             }catch (MalformedURLException ex) 
            {
                JOptionPane.showMessageDialog(null,ex.getMessage());
            } catch (IOException o) 
            {
                JOptionPane.showInputDialog(o.getMessage());
                 txtURL.setText(entry);
                 txtURL.selectAll();
                 btngo.setBackground(Color.GREEN);
                 btngo.setText("GO");
            }catch (Exception t)
            {
                JOptionPane.showMessageDialog(null,t.getMessage());
                btnback.setEnabled(false);
            }
    
}
}
class Forward implements ActionListener
{
        @Override
        public void actionPerformed(ActionEvent e) 
        {
           
            e.setSource(btnforward);
            
        try
       {
       // Create URL and URLConnection objects to make a connection with a page    
       URL myURL=new URL(FWD.peek());
        URLConnection myURLConnection = myURL.openConnection();
        btngo.setForeground(Color.BLACK);
        btngo.setBackground(Color.RED);
        btngo.setText("Connecting");
           Permission t= myURLConnection.getPermission(); 
           t.checkGuard(myURLConnection);
        //Use a large number to set Timeout time
            myURLConnection.setReadTimeout(999999999);
             myURLConnection.connect();
             
                JOptionPane.showMessageDialog(null,connect+FWD.pop());
                  BufferedReader in = new BufferedReader(new InputStreamReader(myURL.openStream()));
       // While loop reads every line of HTML stores it in a variable and then closes the reader
          Path filepath=Paths.get("TEST.html");
          BufferedWriter write = new BufferedWriter(new FileWriter(filepath.toFile()));
      String content="";
        while ((content = in.readLine()) != null)
        {
            write.write(content);
            write.newLine();
            contents.add(content);
            System.out.println(content); 
            contents.add("\n");
        }
            write.close();
            in.close();
            contents.removeAll(contents);
            
          
            btngo.setBackground(Color.GREEN);
            btngo.setText("GO");
         if(FWD.isEmpty())
            {
                btnforward.setEnabled(false);
            }
         else
         {
             btnforward.setEnabled(true);
         }
         System.gc();
         //txtURL.selectAll();
            }catch (MalformedURLException ex) 
            {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            } catch (IOException o) 
            {
                JOptionPane.showMessageDialog(null,o.getMessage());
                 txtURL.setText(entry);
                 txtURL.selectAll();
                 btngo.setText("GO");
                 btngo.setBackground(Color.GREEN);
            }catch (Exception t)
            {
                JOptionPane.showMessageDialog(null,t.getMessage());
            }
          }
          }
class Settings implements ActionListener
{
         //URLFind UF=new URLFind();
        @Override
        public void actionPerformed(ActionEvent e) 
        {
              JLang.setVisible(true);
           //   UF.setVisible(false);
                
        }
    
}
}
