
package chatappl;
import javax.swing.border.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;
import java.net.*;
import java.io.*;
/**
 *
 * @author nvini
 */
public class Server implements ActionListener{
    JTextField text;
    JPanel a1;
    static Box vertical=Box.createVerticalBox();
    static JFrame jf=new JFrame();
    static DataOutputStream dout;
    
    Server()
    {
        jf.setLayout(null);
        
        JPanel p1=new JPanel();
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0,450,70);
        p1.setLayout(null);
        jf.add(p1);
        
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("icons/back.png"));
        Image i2=i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
        JLabel back=new JLabel(i3);
        back.setBounds(5,20,25,25);
        p1.add(back);
        
        back.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent ae)
            {
                System.exit(0);
            }
        });
        
        ImageIcon i4=new ImageIcon(ClassLoader.getSystemResource("icons/profile1.png"));
        Image i5=i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6=new ImageIcon(i5);
        JLabel profile=new JLabel(i6);
        profile.setBounds(40,10,50,50);
        p1.add(profile);
        
        ImageIcon i7=new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8=i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9=new ImageIcon(i8);
        JLabel video=new JLabel(i9);
        video.setBounds(300,20,30,30);
        p1.add(video);
        
        ImageIcon i10=new ImageIcon(ClassLoader.getSystemResource("icons/call.png"));
        Image i11=i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i12=new ImageIcon(i11);
        JLabel call=new JLabel(i12);
        call.setBounds(360,20,35,30);
        p1.add(call);
        
        ImageIcon i13=new ImageIcon(ClassLoader.getSystemResource("icons/more.png"));
        Image i14=i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i15=new ImageIcon(i14);
        JLabel more=new JLabel(i15);
        more.setBounds(420,20,10,25);
        p1.add(more);
        
        JLabel name=new JLabel("Abcd");
        name.setBounds(110,15,100,18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,18));
        p1.add(name);
        
        JLabel status=new JLabel("Active Now");
        status.setBounds(110,35,100,16);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF",Font.BOLD,12));
        p1.add(status);
        
        a1=new JPanel();
        a1.setBounds(5,75,440,570);
        jf.add(a1);
        
        JScrollPane scrollPane = new JScrollPane(a1);
        scrollPane.setBounds(5, 75, 440, 570); // Set the bounds for the scroll pane
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jf.add(scrollPane);
        
        text=new JTextField();
        text.setBounds(5,655,310,40);
        text.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        jf.add(text);
        
        JButton send=new JButton("SEND");
        send.setBounds(320,655,123,40);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        jf.add(send);
        
        jf.setSize(450,700);
        jf.setLocation(200,50);
        jf.setUndecorated(true);
        jf.getContentPane().setBackground(Color.WHITE);
        
        jf.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        try{
            String out=text.getText();
        
       
            JPanel p2=formatLabel(out);
//        p2.add(output);
        
            a1.setLayout(new BorderLayout());
            JPanel right=new JPanel(new BorderLayout());
            right.add(p2,BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            a1.add(vertical,BorderLayout.PAGE_START);
        
            dout.writeUTF(out);
        
            text.setText("");
        
            jf.repaint();
            jf.invalidate();
            jf.validate();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public static JPanel formatLabel(String out)
    {
        JPanel panel=new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        
        JLabel output=new JLabel("<html><p style=\"width: 150px\">" +out+ "</p></html>");
        output.setFont(new Font("Tahoma",Font.PLAIN,16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));
        
        
        panel.add(output);
        
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel time=new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);
        
        time.setText(sdf.format(cal.getTime()));
        
        
        return panel;
    }
    public static void main(String args[])
    {
        new Server();
        
        try
        {
            ServerSocket skt=new ServerSocket(6001);
            while(true)
            {
                Socket s=skt.accept();
                DataInputStream din=new DataInputStream(s.getInputStream());
                dout=new DataOutputStream(s.getOutputStream());
                while(true)
                {
                    String msg=din.readUTF();
                    JPanel panel=formatLabel(msg);
                    
                    JPanel left=new JPanel(new BorderLayout());
                    left.add(panel,BorderLayout.LINE_START);
                    vertical.add(left);
                    jf.validate();
                }
                
            }
            
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
