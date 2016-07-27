package Internet_applications;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.NoRouteToHostException;
import java.net.URLConnection;
import java.nio.file.Files;
//import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
//import javax.swing.text.html.parser.DTD;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * By Niko Marsic
 * Date: November 27th, 2015
 * 
 */ 
 
/**Project: WebPointer
 *
 * @author nickm
 */
public class URLFind extends JFrame implements ActionListener 
{
private JLabel info;
private final JLabel address;
private JTextField txtURL;
private JButton btngo;
private JButton btnback;
private JButton btnforward;
private String Home="http://Google.com";
private  String content;
//private  String domain= "";
private String webaddress;    
   

  //Create required data structures for this project 
  private Forward F=new Forward();
  private Back B=new Back();
  private Stack<String> back=new Stack();
  private Stack<String> FWD=new Stack();
  private StringBuilder b=new StringBuilder();
  private ArrayList<String> contents=new ArrayList();
  private String[] domains = new String[]{".org",".net",".edu",".it",".ru",".biz",".co",".ac",".se",".mil",".ca",".az",".in",".fra",".am",".af",".si"} ; 
    //set constants
    private final int HEIGHT=260;
    private final int WIDTH=315;
    
private	final String entry="Type a Web Address Here !";

    /**
     *
     */
    public URLFind()
    {
         
    super("WebPointer");
    
   
    // set size of frame
	setSize(WIDTH, HEIGHT);
    
    
        
        //Make X close the program
        
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setResizable(false);
      
        //Set layout type
	setLayout(new FlowLayout());
       
         
       
     //Lang.addItemListener(this);
    //Setup Address label
    address= new JLabel("Address:");
    add(address); 
   
    
   // Create and Add TextField Object to Frame
    txtURL=new JTextField(entry,28);
    //Highlight text
    txtURL.selectAll();
    //Assign Inputed text to variable (URL) 
    add(txtURL);
    
   //Create / Add Connect Button
   btngo= new JButton();
   btngo.setForeground(Color.BLACK);
   btngo.setBackground(Color.GREEN);
   btngo.setToolTipText("Connect to a Web page");
   Font myFont = new Font("Arial", Font.BOLD | Font.ITALIC, 20);
   btngo.setText("GO");
     btngo.setFont(myFont);
      btngo.addActionListener(this); 
     // btngo.addKeyListener(this);
     
   // back and forward buttons
   btnback=new JButton("Back");
   btnback.setToolTipText("Takes you to your previous webpage");
   btnback.addActionListener(B);
   btnback.setEnabled(false);
   add(btnback);
 
    add(btngo);
    
  
   //add forward button
   btnforward=new JButton("Forward");
   btnforward.setToolTipText("Advances to the webpage forward");
   btnforward.addActionListener(F);
   btnforward.setEnabled(false);
   add(btnforward);
   
  
  info=new JLabel("By: Niko Marsic");
  add(info);
 
   setVisible(true);
}
@Override
public void actionPerformed(ActionEvent e)
{
  
     CheckBack CheckB=new CheckBack();
     if(!b.toString().isEmpty())
     {
      back.push(b.toString());
      back.ensureCapacity(1);    
      b.delete(0,b.length());
     }
     //Start thread to check for data in back 
     CheckB.start();
     
     Languages();
       
     
  if(!txtURL.getText().startsWith("http://"))
  {
      b.append("http://");
  }
    //JOptionPane.showMessageDialog(null, "Address 1\n"+b.toString());  
  
  
    b.append(txtURL.getText());
    
    if(!txtURL.getText().contains(".com"))
    { 
        DomainFormat();
    }
          
          Fast F= new Fast(b.toString());
          F.start();
    try {
        //Determine Operating System as Windows or not and execute accordingly
        OSDetermination(b.toString());
    } catch (IOException ex) 
    {
       //Error(); 
    } catch (InterruptedException ex) {
        Logger.getLogger(URLFind.class.getName()).log(Level.SEVERE, null, ex);
    }
         
         
   }

    private void WindowsOS(String input) throws  IOException, InterruptedException 
    {
       
            String ChromeW="C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe";
            String ChromeW64="C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";
            String FireFox="C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe";
            String FF64="C:\\Program Files\\Mozilla Firefox\\firefox.exe";
            
            Path fileFF=Paths.get(FireFox);
            Path FireF64=Paths.get(FF64);
            Path fileChrome=Paths.get(ChromeW);
            Path fileChrome64=Paths.get(ChromeW64);
           if (Files.exists(fileChrome))
           {
               
               Runtime rt=Runtime.getRuntime();
               Process p=rt.exec(ChromeW+" "+input);
               
           } else if(Files.exists(fileChrome64))
           {
               Runtime rt=Runtime.getRuntime();
               Process p=rt.exec(ChromeW64+" "+input);
           } else if(Files.exists(fileFF))
           {
               Runtime rt=Runtime.getRuntime();
               Process p=rt.exec(FireFox+" "+input);
               
           } else if (Files.exists(FireF64))
           {
               Runtime rt=Runtime.getRuntime();
               Process p=rt.exec(FF64+" "+input);
            
           }
    }
    private void WebPageCreate(String input) throws MalformedURLException,IOException 
    {
      
           
       URL myURL=new URL(input);
        URLConnection URLConnect = myURL.openConnection();
         
              URLConnect.setReadTimeout(999);
           
             URLConnect.connect();
             setTitle(myURL.toString());
        
                  BufferedReader in = new BufferedReader(new InputStreamReader(myURL.openStream()));
       // While loop reads every line of HTML stores it in a variable and then closes the reader
          Path filepath=Paths.get("WEBPAGE.html");
          BufferedWriter write = new BufferedWriter(new FileWriter(filepath.toFile()));
        while ((content = in.readLine()) != null)
        {
            if(content.equals("")){
                break;
            }
            write.write(content);
            write.newLine();
            contents.add(content);
            System.out.println(content); 
            contents.add("\n");
             
        }
        in.close();
        write.close();
        
      
         if(contents.isEmpty())
         {
             b.delete(0, b.length());
           
             b.append("https://");
             b.append(webaddress);
             
            
            WebPageCreate(b.toString());
         }else
         {
             JOptionPane.showMessageDialog(null,"Web page content has been saved to your computer as WEBPAGE.html !");
             //contents.removeAll(contents);
              btngo.setBackground(Color.GREEN);
             System.out.println("Done!!");
             contents.removeAll(contents);
             txtURL.setText(entry);
         }
         
       
        //contents.removeAll(contents);
    }

    private void DomainFormat() 
    {
       
        
        //JOptionPane.showMessageDialog(null, "IN!!!!!!!!!!!!!!!!");
             String domain=".com";
              int r=domains.length;
         for(String d:domains)
   {
       r--;
      
     
       if(txtURL.getText().contains(d)||txtURL.getText().contains(domains[r]))
       {
           domain="";
           //b.append(domain);
           break;
       }
       }
          b.append(domain);
     webaddress=txtURL.getText()+domain;
     }        
      
     
    

         //System.out.println(domain);
          
           //JOptionPane.showMessageDialog(null, b.toString());
 
     
    private void OSDetermination(String input) throws IOException, InterruptedException 
    {
        //txtURL.setText(b.toString());
       //This method determines which opeerating system is being used and executes  
        String Windows="C:\\Windows";
          Path W=Paths.get(Windows);
          Path Window=Paths.get("C:\\WINDOWS");
         
         if(Files.exists(W)||Files.exists(Window))
         {
          //  JOptionPane.showMessageDialog(null,"You are using Windows operaring system!!");
             
            WindowsOS(input); 
         }else 
         {
          JOptionPane.showMessageDialog(null, "You may be using a variation of Linux or Mac OSX");
         }     
    }

    private void Languages() 
    {
        String http="http://";
         
   
    if(txtURL.getText().equals(entry)||txtURL.getText().isEmpty()||txtURL.getText().equalsIgnoreCase("Home"))
    {
        txtURL.setText(Home);
    }
      if(txtURL.getText().contains("SLO")||txtURL.getText().equalsIgnoreCase("Google.si"))
    {
        btnback.setText("Nazaj");
        btnforward.setText("Naprej");  
        Home="https://Google.si";
        btngo.setText("Najdi");
        txtURL.setText(Home);
    }else if(txtURL.getText().contains("ENG")||txtURL.getText().equalsIgnoreCase("ENG"))
    {
        btnback.setText("Back");
        btnforward.setText("Forward");
        btngo.setText("GO");
        Home="https://Google.com";
        txtURL.setText(Home);
    }else if(txtURL.getText().contains("ITA")||txtURL.getText().equalsIgnoreCase("Google.it"))
    {
        Home="https://Google.it";
        txtURL.setText(Home);
        btnback.setText("Indietro");
         btngo.setText("Trova");
        btnforward.setText("Inoltrare");
    }else if(txtURL.getText().contains("RUS")||txtURL.getText().equalsIgnoreCase("Google.ru"))
    {
        Home="https://Google.ru";
        txtURL.setText(Home);
        btngo.setText("идти");
        btnforward.setText("вперед");
        btnback.setText("назад");
    }
     
    }

    private void Error() 
    {
        btngo.setBackground(Color.YELLOW);
        txtURL.setText(entry);
        this.setTitle("WebPointer");
        JOptionPane.showMessageDialog(null,"Check your Internet Connection");
    }
class Back implements ActionListener
{
   private String last="";
         
        @Override
        public void actionPerformed(ActionEvent e) 
        {
        
          e.setSource(btnback);
          
          
     
          
          if(!last.isEmpty())
          {
            FWD.ensureCapacity(1);   
            FWD.push(last);
          }
            CheckForward CheckF=new CheckForward();
            CheckF.start();
            // Create URL object to connect to a particular webpage using URL variable
       
            try
       {
         
          Fast F=new Fast(back.peek()); 
             F.start();
           OSDetermination(back.peek()); 
         
            
        //contents.removeAll(contents);
      
         last=back.pop();
        if(back.isEmpty())
        {
        
            btnback.setEnabled(false);
        }
        else
        {
            btnback.setEnabled(true);
        }
           
            txtURL.selectAll();
           
             System.gc();
             
            btnback.setSelected(false);
             }catch (MalformedURLException ex) 
            {
                JOptionPane.showMessageDialog(null,ex.getMessage());
                
            } catch (IOException o) 
            {
                JOptionPane.showMessageDialog(null,o.getMessage());
                 txtURL.setText(entry);
                 txtURL.selectAll();
                 btngo.setBackground(Color.YELLOW);
                 btngo.setText("GO");
                 setTitle("WebPointer");
            }catch (Exception t)
            {
                JOptionPane.showMessageDialog(null,t.getMessage());
            }
    
}
}
class Forward implements ActionListener
{
        @Override
        public void actionPerformed(ActionEvent e) 
        {
          btnforward.setSelected(true);
            e.setSource(btnforward);
             System.gc();
        try
       {
          //Fast F=null;
         Fast F =new Fast(FWD.peek());
         F.start();
         OSDetermination(FWD.peek());  
       
        //Check if the stack is empty 
         if(FWD.isEmpty())
            {
                btnforward.setEnabled(false);
            }
         else
         {
             btnforward.setEnabled(true);
         }
         
         System.gc();
         
            }catch (MalformedURLException ex) 
            {
                JOptionPane.showMessageDialog(null, ex.getMessage());
                setTitle("WebPointer");
            } catch (IOException o) 
            {
                JOptionPane.showMessageDialog(null,o.getMessage());
                 txtURL.setText(entry);
                 txtURL.selectAll();
                 btngo.setText("GO");
                 btngo.setBackground(Color.YELLOW);
                 setTitle("WebPointer");
            }catch (Exception t)
            {
                JOptionPane.showMessageDialog(null,t.getMessage());
            }
          }
          }    
 class Fast extends Thread
 {
     private String WA;
     Fast(String aadress)
     {
         WA=aadress;
         
     }
     public String getAddress()
     {
         return WA;
     }
     @Override
     public void run()
     {
        //System.out.println(getAddress());
        //Fast F =new Fast();
             try {
                 WebPageCreate(getAddress()); 
             } catch (MalformedURLException x)
             {
                 JOptionPane.showMessageDialog(null,"Malformed\n"+b.toString() );
                 b.delete(0,b.length());
                 JOptionPane.showMessageDialog(null,"Please retype the webaddress");
             } catch (IOException ex) {
             Logger.getLogger(URLFind.class.getName()).log(Level.SEVERE, null, ex);
         }
         } 
         }
 class CheckBack extends Thread{
     @Override
     public void run(){
          //check if stack contains anything 
            if(!back.isEmpty())
           {
               btnback.setEnabled(true);        
           }
            
     
     }
 }
         
         
class CheckForward extends Thread{
    @Override
    public void run(){
        // Check the Foward stack
         if(!FWD.empty())
                {
                    btnforward.setEnabled(true);
                }
                else
                {
                    btnforward.setEnabled(false);
                } 
    }
}     
}  

