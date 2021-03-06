/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MTMS;
import java.io.*;
import javax.swing.JFrame;

/**
 *
 * @author hguo87
 * comments by Yuan He
 * 
 * This is the login frame, the entry point of the system
 */
public class MTMS_Login extends javax.swing.JFrame {

    /**
     * Creates new form MTMS_Login
     */
    public MTMS_GUI ourGUI;             //Main frame
    public String DBName = "MTMS";      //Database Name
    public String DBAccount = "root";   //Database Account
    //public String DBPwd = "mysql";
    public String DBPwd = "";       //Databse password
    
    public Setup_GUI dbgui = null;  // Databse setup form
    
    
    public MTMS_Login() {
        initComponents();   //Initiate the UI
    }

    /**
     * Initiate the UI by NetBeans
     * 
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        pwfPassword = new javax.swing.JPasswordField();
        btnSubmit = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        lblError = new javax.swing.JLabel();
        btnSetUp = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MTMS Login");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setText("ID:");
        jLabel1.setToolTipText("");

        jLabel2.setText("Password:");
        jLabel2.setToolTipText("");

        btnSubmit.setText("Login");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        lblError.setForeground(new java.awt.Color(0, 153, 51));
        lblError.setText("Something is wrong. Please retry.");

        btnSetUp.setText("DB Setup");
        btnSetUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetUpActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel3.setText("Login");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(14, 14, 14)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(lblError)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(layout.createSequentialGroup()
                            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(jLabel2)
                                .add(jLabel1))
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(txtID, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 144, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(pwfPassword, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 144, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                            .add(btnSubmit, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 99, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                            .add(btnCancel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 99, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(layout.createSequentialGroup()
                        .add(jLabel3)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 90, Short.MAX_VALUE)
                        .add(btnSetUp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 88, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(btnSetUp)
                    .add(jLabel3))
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(txtID, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(pwfPassword, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btnSubmit)
                    .add(btnCancel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(lblError)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    //Submit button event, 
    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        // TODO add your handling code here:
        
        
        //First check whether this user exists.
        lblError.setVisible(false);
        String ID = "";
        String pwd = "";
        if ((ID = txtID.getText()) == "")
        {
            lblError.setText("Please input a user ID.");
            lblError.setVisible(true);
            return;
        }
        pwd = pwfPassword.getText().replace("'", "\\'");
        
        //Initial the user controller module
        User_Controller uc = new User_Controller("localhost", DBName, DBAccount, DBPwd);
        User us = uc.ValidateLogin(ID, pwd);    //validate he ogin info
        
        //If no, return
        if(us==null)
        {
            lblError.setText(uc.err);
            lblError.setVisible(true);
        }
        else if(us.UserID.equals("")||us.UserType == User.U_Types.GUEST)
        {
            lblError.setText("Invalid user ID or password.");
            lblError.setVisible(true);
        }
        else
        {
            //If yes, load main frame MTMS_GUI
            this.setVisible(false);
            ourGUI = new MTMS_GUI();
            ourGUI.DBName = DBName;
            ourGUI.DBAccount = DBAccount;
            ourGUI.DBPwd = DBPwd;
            ourGUI.fromLogin = this;
            ourGUI.ThisUser = us;
            ourGUI.setVisible(true);
        }
    }//GEN-LAST:event_btnSubmitActionPerformed
    
    
    //Event for when frame was opened, initiate the environment
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        lblError.setVisible(false);     //set error lable invisible
        btnSubmit.getRootPane().setDefaultButton(btnSubmit);    //set submit button is clicked when use enter the enter key
        txtID.requestFocus();   // set focus on textID input
        
        DBName = "MTMS";
        DBAccount = "root";
        //public String DBPwd = "mysql";
        DBPwd = "";
        
        //read DB.txt to retrieve the database information
        InputStreamReader reader = null;       
        try
        {
            reader = new InputStreamReader(new FileInputStream("./Data/DB.txt"));  //The database information will saved in the DB.txt
            BufferedReader in = new BufferedReader(reader);
            String temp;
            while((temp = in.readLine())!=null)
            {
                if (temp.contains("DBName:"))
                {
                    DBName = temp.substring(7);
                }
                if (temp.contains("DBAccount:"))
                {
                    DBAccount= temp.substring(10);
                }
                if (temp.contains("DBPwd:"))
                {
                    DBPwd = temp.substring(6);
                }
            }
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    
    }//GEN-LAST:event_formWindowOpened

    // Go to the database frame to setup the database information
    private void btnSetUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetUpActionPerformed
        this.setVisible(false);     
        dbgui = new Setup_GUI();
        dbgui.DBName = DBName;
        dbgui.DBAccount = DBAccount;
        dbgui.DBPwd = DBPwd;
        dbgui.fromMain = this;
        dbgui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dbgui.setVisible(true);
    }//GEN-LAST:event_btnSetUpActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MTMS_Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MTMS_Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MTMS_Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MTMS_Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MTMS_Login().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSetUp;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblError;
    private javax.swing.JPasswordField pwfPassword;
    private javax.swing.JTextField txtID;
    // End of variables declaration//GEN-END:variables
}
