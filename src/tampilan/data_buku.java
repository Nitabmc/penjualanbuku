/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampilan;
import controler.koneksi;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS
 */
public class data_buku extends javax.swing.JInternalFrame {

    DefaultTableModel tabModel;
    ResultSet RsBuku=null;
    /**
     * Creates new form data_buku
     */
    public data_buku() throws SQLException {
        initComponents();
        dataFromDataBaseToComboBox();
        
//        Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
//        Dimension frameSize=this.getSize();
//        if(frameSize.height > screenSize.height){
//        frameSize.height=screenSize.height;
//        }
//        if(frameSize.width > screenSize.width){
//        frameSize.width=screenSize.width;
//        }
//        this.setLocation((screenSize.width - frameSize.width) / 2,
//        (screenSize.height = screenSize.height) / 20); 
        
        //fungsi Tampil data dan set edit off dijalankan saat aplikasi di run
        tampilData();
        SetEditOff();
//        txtKodeBuku.setVisible(false);
        txtkodejenis.setVisible(false);
    }
    
    //fungsi untuk menampilkan data pegawai ke dalam tabel
    private void tampilData(){
        try{
            Object[] judul_kolom = {"Kode Buku", "Jenis Buku", "Judul Buku", "Pengarang", "Penerbit"};
            tabModel=new DefaultTableModel(null,judul_kolom);
     
            TabelBuku.setModel(tabModel);
            
            Connection conn=(Connection)koneksi.koneksiDB();
            Statement stt=conn.createStatement();
            tabModel.getDataVector().removeAllElements();
            
            RsBuku=stt.executeQuery("SELECT * from tbl_jenisbuku, tbl_buku WHERE tbl_buku.kode_jenisbuku=tbl_jenisbuku.kode_jenisbuku");  
            while(RsBuku.next()){
                Object[] data={
                    RsBuku.getString("kode_buku"),
                    RsBuku.getString("nama_jenisbuku"),
                    RsBuku.getString("judul_buku"),
                    RsBuku.getString("pengarang"),
                    RsBuku.getString("penerbit"),
                    RsBuku.getString("kode_jenisbuku")

                };
                
               tabModel.addRow(data);
            }                
        } catch (Exception ex) {
        System.err.println(ex.getMessage());
        }
        
        // disable button
        btnsimpan.setEnabled(false);
        btnupdate.setEnabled(false);
        btndelete.setEnabled(false);
        btncancel.setEnabled(false);
    }
    
    // menampilkan data di
    public void dataFromDataBaseToComboBox() throws SQLException{
            Connection conn=(Connection)koneksi.koneksiDB();
        try {
            String query = "SELECT * FROM tbl_jenisbuku";
            Statement st =conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            ComboBoxNJenisBuku.addItem("");
            while (rs.next()) {                
                ComboBoxNJenisBuku.addItem(rs.getString("nama_jenisbuku"));
            }
            
            rs.last();
            int jumlahdata = rs.getRow();
            rs.first();
            
        } catch (SQLException e) {
        }        
    }

    
    //fungsi untuk menampilkan data pegawai ke dalam tabel pencarian
    private void cariData(String key){
        try{
            Object[] judul_kolom = {"Kode Buku", "Jenis Buku", "Judul Buku", "Pengarang", "Penerbit"};
            tabModel=new DefaultTableModel(null,judul_kolom);
            TabelBuku.setModel(tabModel);
            
            Connection conn=(Connection)koneksi.koneksiDB();
            Statement stt=conn.createStatement();
            tabModel.getDataVector().removeAllElements();
            
            RsBuku=stt.executeQuery("SELECT * from tbl_jenisbuku, tbl_buku WHERE tbl_buku.kode_jenisbuku=tbl_jenisbuku.kode_jenisbuku AND judul_buku LIKE '%"+key+"%'");  
            while(RsBuku.next()){
                Object[] data={
                    RsBuku.getString("kode_buku"),
                    RsBuku.getString("nama_jenisbuku"),
                    RsBuku.getString("judul_buku"),
                    RsBuku.getString("pengarang"),
                    RsBuku.getString("penerbit"),
                    RsBuku.getString("kode_jenisbuku")
                };
               tabModel.addRow(data);
            }                
        } catch (Exception ex) {
        System.err.println(ex.getMessage());
        }
    }
   
    //menampilkan data ke form saat data pada tabel di klik 
   void tblKeForm(){
        txtKodeBuku.setText(tabModel.getValueAt(TabelBuku.getSelectedRow(),0)+"");
        ComboBoxNJenisBuku.setSelectedItem(tabModel.getValueAt(TabelBuku.getSelectedRow(),1)+"");
        txtJudulBuku.setText(tabModel.getValueAt(TabelBuku.getSelectedRow(),2)+"");
        txtPengarang.setText(tabModel.getValueAt(TabelBuku.getSelectedRow(),3)+"");
        txtPenerbit.setText(tabModel.getValueAt(TabelBuku.getSelectedRow(),4)+"");
//        txtkodejenis.setText(tabModel.getValueAt(TabelBuku.getSelectedRow(),5)+"");
        
        
        btnupdate.setEnabled(true);
        btndelete.setEnabled(true);
        btncancel.setEnabled(true);
        btnsimpan.setEnabled(false);
    }
   
   //membersihkan form
   private void BersihData(){
        txtKodeBuku.setText("");
        txtJudulBuku.setText("");
        txtPengarang.setText("");
        txtPenerbit.setText("");
        ComboBoxNJenisBuku.setSelectedIndex(0);
    } 
   
   //disable form
   private void SetEditOff(){
        txtJudulBuku.setEnabled(false);
        txtPengarang.setEnabled(false);
        txtPenerbit.setEnabled(false);
        ComboBoxNJenisBuku.setEnabled(false);
   }
   
   
   private void seteditOn(){
        txtJudulBuku.setEnabled(true);
        txtPengarang.setEnabled(true);
        txtPenerbit.setEnabled(true);
        ComboBoxNJenisBuku.setEnabled(true);
   }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtKodeBuku = new javax.swing.JTextField();
        txtPengarang = new javax.swing.JTextField();
        txtPenerbit = new javax.swing.JTextField();
        btnsimpan = new javax.swing.JButton();
        btndelete = new javax.swing.JButton();
        btnupdate = new javax.swing.JButton();
        btncancel = new javax.swing.JButton();
        btntambah = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        ComboBoxNJenisBuku = new javax.swing.JComboBox<>();
        txtkodejenis = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtJudulBuku = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelBuku = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtcari = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

        setClosable(true);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 51, 153));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Pengelolaan Data Buku");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(307, 307, 307))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(36, 36, 36))
        );

        jPanel2.setBackground(new java.awt.Color(255, 51, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data Buku", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Kode Buku");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Pengarang ");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Penerbit");

        btnsimpan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnsimpan.setText("Save");
        btnsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsimpanActionPerformed(evt);
            }
        });

        btndelete.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btndelete.setText("Delete");
        btndelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteActionPerformed(evt);
            }
        });

        btnupdate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnupdate.setText("update");
        btnupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdateActionPerformed(evt);
            }
        });

        btncancel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btncancel.setText("Cancel");
        btncancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancelActionPerformed(evt);
            }
        });

        btntambah.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btntambah.setText("Tambah Data");
        btntambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntambahActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Jenis Buku");

        ComboBoxNJenisBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBoxNJenisBukuActionPerformed(evt);
            }
        });

        txtkodejenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtkodejenisKeyPressed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Judul Buku");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(txtkodejenis))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btntambah)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnsimpan)
                                .addGap(18, 18, 18)
                                .addComponent(btndelete)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnupdate)))
                        .addGap(18, 18, 18)
                        .addComponent(btncancel))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel8))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(105, 105, 105)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtJudulBuku, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                                    .addComponent(txtKodeBuku)
                                    .addComponent(txtPenerbit)
                                    .addComponent(txtPengarang))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ComboBoxNJenisBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtKodeBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(ComboBoxNJenisBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtJudulBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(txtPengarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPenerbit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(41, 41, 41)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnsimpan)
                    .addComponent(btndelete)
                    .addComponent(btncancel)
                    .addComponent(btnupdate)
                    .addComponent(btntambah))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                .addComponent(txtkodejenis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76))
        );

        jPanel3.setBackground(new java.awt.Color(255, 51, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tabel Data Buku", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        TabelBuku.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        TabelBuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelBukuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TabelBuku);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Cari");

        txtcari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcariKeyPressed(evt);
            }
        });

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/download.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addGap(45, 45, 45))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateActionPerformed
        // TODO add your handling code here:
        String KodeBuku =txtKodeBuku.getText();
        String JudulBuku=txtJudulBuku.getText();
        String Pengarang=txtPengarang.getText();
        String Penerbit=txtPenerbit.getText();
        String JenisBuku=ComboBoxNJenisBuku.getSelectedItem().toString();
        String kodejenis=txtkodejenis.getText();
        
        

        if (KodeBuku.isEmpty() ) {
            JOptionPane.showMessageDialog(null,"KodeBuku tidak boleh kosong");
            txtKodeBuku.requestFocus();
        }else if (JudulBuku.isEmpty() ) {
            JOptionPane.showMessageDialog(null,"Judul Buku tidak boleh kosong");
            txtJudulBuku.requestFocus();
        }else if (Pengarang.isEmpty() ) {
            JOptionPane.showMessageDialog(null,"Pengarang tidak boleh kosong");
            txtJudulBuku.requestFocus();
        }else if (Pengarang.isEmpty() ) {
            JOptionPane.showMessageDialog(null,"Pengarang tidak boleh kosong");
            txtPengarang.requestFocus();
        }else if (Penerbit.isEmpty() ) {
            JOptionPane.showMessageDialog(null,"Penerbit tidak boleh kosong");
            txtPenerbit.requestFocus();
        }else if (JenisBuku.isEmpty() ) {
            JOptionPane.showMessageDialog(null,"Jenis Buku tidak boleh kosong");
            ComboBoxNJenisBuku.requestFocus();
        }else{
            try{
                Connection conn=(Connection)koneksi.koneksiDB();
                Statement stt=conn.createStatement();
                stt.executeUpdate("UPDATE tbl_buku SET judul_buku='"+JudulBuku+"', pengarang='"+Pengarang+"', penerbit='"+Penerbit+"', kode_jenisbuku='"+kodejenis+"' WHERE kode_buku='"+KodeBuku+"'");
                BersihData();
                tampilData();
                SetEditOff();
                JOptionPane.showMessageDialog(this,"Data berhasil diubah","Success",JOptionPane.INFORMATION_MESSAGE);
            } catch(SQLException e){
                JOptionPane.showMessageDialog(this,"Ubah data gagal\n"+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnupdateActionPerformed

    private void btncancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelActionPerformed
        // TODO add your handling code here:
        BersihData();
        btnsimpan.setEnabled(false);
        btnupdate.setEnabled(false);
        btndelete.setEnabled(false);
        SetEditOff();
    }//GEN-LAST:event_btncancelActionPerformed

    private void btntambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntambahActionPerformed
        // TODO add your handling code here:
        BersihData();
        txtkodejenis.requestFocus();
        btnsimpan.setEnabled(true);
        btnupdate.setEnabled(false);
        btndelete.setEnabled(false);
        seteditOn();
    }//GEN-LAST:event_btntambahActionPerformed

    private void ComboBoxNJenisBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBoxNJenisBukuActionPerformed
        // TODO add your handling code here:
        String text;
        text = (String)ComboBoxNJenisBuku.getSelectedItem();
        Connection conn = null;
        try {
            conn = (Connection)koneksi.koneksiDB();
        } catch (SQLException ex) {
            Logger.getLogger(data_buku.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            String query = "SELECT * FROM tbl_jenisbuku WHERE nama_jenisbuku ='"+text+"'";
            Statement st =conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                txtkodejenis.setText(rs.getString("kode_jenisbuku"));
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }//GEN-LAST:event_ComboBoxNJenisBukuActionPerformed

    private void txtkodejenisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtkodejenisKeyPressed

    }//GEN-LAST:event_txtkodejenisKeyPressed

    private void btnsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsimpanActionPerformed
        // TODO add your handling code here:
        String KodeBuku =txtKodeBuku.getText();
        String JudulBuku=txtJudulBuku.getText();
        String Pengarang=txtPengarang.getText();
        String Penerbit=txtPenerbit.getText();
        String JenisBuku=ComboBoxNJenisBuku.getSelectedItem().toString();
        String kodejenis=txtkodejenis.getText();
        
        

        if (KodeBuku.isEmpty() ) {
            JOptionPane.showMessageDialog(null,"KodeBuku tidak boleh kosong");
            txtKodeBuku.requestFocus();
        }else if (JudulBuku.isEmpty() ) {
            JOptionPane.showMessageDialog(null,"Judul Buku tidak boleh kosong");
            txtJudulBuku.requestFocus();
        }else if (Pengarang.isEmpty() ) {
            JOptionPane.showMessageDialog(null,"Pengarang tidak boleh kosong");
            txtJudulBuku.requestFocus();
        }else if (Pengarang.isEmpty() ) {
            JOptionPane.showMessageDialog(null,"Pengarang tidak boleh kosong");
            txtPengarang.requestFocus();
        }else if (Penerbit.isEmpty() ) {
            JOptionPane.showMessageDialog(null,"Penerbit tidak boleh kosong");
            txtPenerbit.requestFocus();
        }else if (JenisBuku.isEmpty() ) {
            JOptionPane.showMessageDialog(null,"Jenis Buku tidak boleh kosong");
            ComboBoxNJenisBuku.requestFocus();
        }else{
            try{
                Connection conn=(Connection)koneksi.koneksiDB();
                Statement stt=conn.createStatement();
                stt.executeUpdate("insert into tbl_buku(kode_buku,judul_buku,pengarang,penerbit,kode_jenisbuku)"+
                    "VALUES('"+KodeBuku+"','"+JudulBuku+"','"+Pengarang+"','"+Penerbit+"','"+kodejenis+"')");
                BersihData();
                tampilData();
                SetEditOff();
                JOptionPane.showMessageDialog(this,"Data berhasil disimpan","Success",JOptionPane.INFORMATION_MESSAGE);
            } catch(SQLException e){
                JOptionPane.showMessageDialog(this,"Simpan data gagal\n"+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnsimpanActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
        // TODO add your handling code here:
        String KodeBuku =txtKodeBuku.getText();
        String JudulBuku=txtJudulBuku.getText();

        if (KodeBuku.isEmpty() ) {
            JOptionPane.showMessageDialog(null,"Kode Buku tidak boleh kosong");
            txtKodeBuku.requestFocus();
        }else if(JOptionPane.showConfirmDialog(null,"Apakah anda yakin akan menghapus data ini?",
            "Informasi",JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE)==JOptionPane.OK_OPTION){
        try{
            Connection conn=(Connection)koneksi.koneksiDB();
            Statement stt=conn.createStatement();
            stt.executeUpdate("DELETE FROM tbl_buku WHERE kode_buku='"+KodeBuku+"'");
            BersihData();
            tampilData();
            SetEditOff();
            JOptionPane.showMessageDialog(this,"Data berhasil di hapus","Success",JOptionPane.INFORMATION_MESSAGE);
        } catch(SQLException e){
            JOptionPane.showMessageDialog(this,"Delete data gagal\n"+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
        }
    }//GEN-LAST:event_btndeleteActionPerformed

    private void TabelBukuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelBukuMouseClicked
        // TODO add your handling code here:
        seteditOn();
        txtKodeBuku.setEnabled(true);
        tblKeForm();
    }//GEN-LAST:event_TabelBukuMouseClicked

    private void txtcariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyPressed
        // TODO add your handling code here:
                String key=txtcari.getText();
        System.out.println(key);  
        
            if(key!=""){
                cariData(key);
            }else{
                tampilData();
            }
    }//GEN-LAST:event_txtcariKeyPressed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentShown


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboBoxNJenisBuku;
    private javax.swing.JTable TabelBuku;
    private javax.swing.JButton btncancel;
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btnsimpan;
    private javax.swing.JButton btntambah;
    private javax.swing.JButton btnupdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtJudulBuku;
    private javax.swing.JTextField txtKodeBuku;
    private javax.swing.JTextField txtPenerbit;
    private javax.swing.JTextField txtPengarang;
    private javax.swing.JTextField txtcari;
    private javax.swing.JTextField txtkodejenis;
    // End of variables declaration//GEN-END:variables
}
