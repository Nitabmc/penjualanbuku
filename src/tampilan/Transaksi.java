/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampilan;


import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.sql.Statement;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

import controler.koneksi;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author ASUS
 */
public class Transaksi extends javax.swing.JInternalFrame {
    

    static boolean isiVisible() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   DefaultTableModel tabModel;
    ResultSet RsTransaksi=null;
    /**
     * Creates new form data_buku
     */
    public Transaksi() throws SQLException {
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
        nofaktur();
//        txtNoTransaksi.setVisible(false);
        txtBuku.setVisible(false);
    }
    
    //fungsi untuk menampilkan data pegawai ke dalam tabel
    private void tampilData(){
        try{
            Object[] judul_kolom = {"No. Transaksi", "Tanggal", "Nama Pembeli", "Judul Buku", "Harga", "Di Bayar", "Kembali"};
            tabModel=new DefaultTableModel(null,judul_kolom);
     
            TabelTransaksi.setModel(tabModel);
            
            Connection conn=(Connection)koneksi.koneksiDB();
            Statement stt=conn.createStatement();
            tabModel.getDataVector().removeAllElements();
            
            RsTransaksi=stt.executeQuery("SELECT * from tbl_buku, tbl_transaksi WHERE tbl_transaksi.kode_buku=tbl_buku.kode_buku");  
            while(RsTransaksi.next()){
                Object[] data={
                    RsTransaksi.getString("no_transaksi"),
                    RsTransaksi.getString("tanggal"),
                    RsTransaksi.getString("nama_pembeli"),
                    RsTransaksi.getString("judul_buku"),
                    RsTransaksi.getString("harga"),
                    RsTransaksi.getString("dibayar"),
                    RsTransaksi.getString("kembali"),
                    RsTransaksi.getString("kode_buku")

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
            String query = "SELECT * FROM tbl_buku";
            Statement st =conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            ComboBoxBuku.addItem("");
            while (rs.next()) {                
                ComboBoxBuku.addItem(rs.getString("judul_buku"));
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
            Object[] judul_kolom = {"No. Transaksi", "Tanggal", "Nama Pembeli", "Judul Buku", "Harga", "Di Bayar", "Kembali"};
            tabModel=new DefaultTableModel(null,judul_kolom);
            TabelTransaksi.setModel(tabModel);
            
            Connection conn=(Connection)koneksi.koneksiDB();
            Statement stt=conn.createStatement();
            tabModel.getDataVector().removeAllElements();
            
            RsTransaksi=stt.executeQuery("SELECT * from tbl_buku, tbl_transaksi WHERE tbl_transaksi.kode_buku=tbl_buku.kode_buku AND judul_buku LIKE '%"+key+"%'");  
            while(RsTransaksi.next()){
                Object[] data={
                    RsTransaksi.getString("no_transaksi"),
                    RsTransaksi.getString("tanggal"),
                    RsTransaksi.getString("nama_pembeli"),
                    RsTransaksi.getString("judul_buku"),
                    RsTransaksi.getString("harga"),
                    RsTransaksi.getString("dibayar"),
                    RsTransaksi.getString("kembali"),
                    RsTransaksi.getString("kode_buku")
                };
               tabModel.addRow(data);
            }                
        } catch (Exception ex) {
        System.err.println(ex.getMessage());
        }
    }
   
    //menampilkan data ke form saat data pada tabel di klik 
   void tblKeForm() throws ParseException{
        txtNoTransaksi.setText(tabModel.getValueAt(TabelTransaksi.getSelectedRow(),0)+"");
        DateTanggal.setDate(new SimpleDateFormat("yyyy-MM-dd").parse((String)tabModel.getValueAt(TabelTransaksi.getSelectedRow(), 1)));
        txtNamaPembeli.setText(tabModel.getValueAt(TabelTransaksi.getSelectedRow(),2)+"");
        ComboBoxBuku.setSelectedItem(tabModel.getValueAt(TabelTransaksi.getSelectedRow(),3)+"");
        txtHarga.setText(tabModel.getValueAt(TabelTransaksi.getSelectedRow(),4)+"");
        txtDibayar.setText(tabModel.getValueAt(TabelTransaksi.getSelectedRow(),5)+"");
        txtKembali.setText(tabModel.getValueAt(TabelTransaksi.getSelectedRow(),6)+"");
//        txtBuku.setText(tabModel.getValueAt(TabelTransaksi.getSelectedRow(),5)+"");
        
        
        btnupdate.setEnabled(true);
        btndelete.setEnabled(true);
        btncancel.setEnabled(true);
        btnsimpan.setEnabled(false);
    }
   
   //membersihkan form
   private void BersihData(){
//        txtNoTransaksi.setText("");
        txtNamaPembeli.setText("");
        txtHarga.setText("");
        txtDibayar.setText("");
        txtKembali.setText("");
        ComboBoxBuku.setSelectedIndex(0);
        DateTanggal.setDate(null); 
    } 
   
   //disable form
   private void SetEditOff(){
       txtNoTransaksi.setEnabled(false);
        txtNamaPembeli.setEnabled(false);
        txtHarga.setEnabled(false);
        txtDibayar.setEnabled(false);
        txtKembali.setEnabled(false);
        DateTanggal.setEnabled(false);
        ComboBoxBuku.setEnabled(false);
   }
   
   
   private void seteditOn(){
        txtNamaPembeli.setEnabled(true);
        txtHarga.setEnabled(true);
        txtDibayar.setEnabled(true);
        txtKembali.setEnabled(true);
        DateTanggal.setEnabled(true);
        ComboBoxBuku.setEnabled(true);
   }

     private void nofaktur() throws SQLException
    {
        String text;
//        text = (String)ComboBoxBuku.getSelectedItem();
        Connection conn=(Connection)koneksi.koneksiDB();
        try {
            String query = "SELECT * FROM tbl_transaksi order by no_transaksi desc";
            Statement st =conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                String nofak = rs.getString("no_transaksi").substring(1);
                String AN = "" + (Integer.parseInt(nofak) + 1);
                String Nol = "";

                if(AN.length()==1)
                {Nol = "000";}
                else if(AN.length()==2)
                {Nol = "00";}
                else if(AN.length()==3)
                {Nol = "0";}
                else if(AN.length()==4)
                {Nol = "";}

               txtNoTransaksi.setText("T" + Nol + AN);
            } else {
               txtNoTransaksi.setText("T0001");
            }

           }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
           }
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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtNoTransaksi = new javax.swing.JTextField();
        txtNamaPembeli = new javax.swing.JTextField();
        txtHarga = new javax.swing.JTextField();
        txtDibayar = new javax.swing.JTextField();
        txtKembali = new javax.swing.JTextField();
        DateTanggal = new com.toedter.calendar.JDateChooser();
        ComboBoxBuku = new javax.swing.JComboBox<>();
        btntambah = new javax.swing.JButton();
        btnsimpan = new javax.swing.JButton();
        btndelete = new javax.swing.JButton();
        btnupdate = new javax.swing.JButton();
        btncancel = new javax.swing.JButton();
        txtBuku = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelTransaksi = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        txtcari = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();

        setClosable(true);

        jPanel1.setBackground(new java.awt.Color(255, 204, 102));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Transaksi Penjualan Buku");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(293, 293, 293))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(72, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(41, 41, 41))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data Transaksi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("No.Transaksi");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Tanggal");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Nama Pembeli");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Kode Buku");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Harga");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("DiBayar");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Kembali");

        txtNoTransaksi.setText("jTextField1");

        txtNamaPembeli.setText("jTextField2");

        txtHarga.setText("jTextField5");
        txtHarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtHargaKeyReleased(evt);
            }
        });

        txtDibayar.setText("jTextField6");
        txtDibayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDibayarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDibayarKeyReleased(evt);
            }
        });

        txtKembali.setText("jTextField7");

        ComboBoxBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBoxBukuActionPerformed(evt);
            }
        });

        btntambah.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btntambah.setText("Tambah Data");
        btntambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntambahActionPerformed(evt);
            }
        });

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

        txtBuku.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBukuKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(txtBuku))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btntambah)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnsimpan)
                                .addGap(18, 18, 18)
                                .addComponent(btndelete)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnupdate)))
                        .addGap(18, 18, 18)
                        .addComponent(btncancel))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel9)
                                    .addGap(46, 46, 46))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(1, 1, 1)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(jLabel7)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel3)
                                                .addComponent(jLabel2)
                                                .addComponent(jLabel4))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addGap(23, 23, 23)))))))
                        .addGap(78, 78, 78)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ComboBoxBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtNoTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                                .addComponent(txtNamaPembeli, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                                .addComponent(DateTanggal, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                                .addComponent(txtHarga, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                                .addComponent(txtDibayar, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                                .addComponent(txtKembali, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNoTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(DateTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(txtNamaPembeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(ComboBoxBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDibayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtKembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnsimpan)
                    .addComponent(btndelete)
                    .addComponent(btncancel)
                    .addComponent(btnupdate)
                    .addComponent(btntambah))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                .addComponent(txtBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tabel Transaksi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        TabelTransaksi.setModel(new javax.swing.table.DefaultTableModel(
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
        TabelTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelTransaksiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TabelTransaksi);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Cari");

        txtcari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcariKeyPressed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/download.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ComboBoxBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBoxBukuActionPerformed
        // TODO add your handling code here:
        String text;
        text = (String)ComboBoxBuku.getSelectedItem();
        Connection conn = null;
        try {
            conn = (Connection)koneksi.koneksiDB();
        } catch (SQLException ex) {
            Logger.getLogger(Transaksi.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            String query = "SELECT * FROM tbl_buku WHERE judul_buku ='"+text+"'";
            Statement st =conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                txtBuku.setText(rs.getString("kode_buku"));
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }//GEN-LAST:event_ComboBoxBukuActionPerformed

    private void btntambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntambahActionPerformed
        // TODO add your handling code here:
        BersihData();
        DateTanggal.requestFocus();
        btnsimpan.setEnabled(true);
        btnupdate.setEnabled(false);
        btndelete.setEnabled(false);
        seteditOn();
    }//GEN-LAST:event_btntambahActionPerformed

    private void btnsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsimpanActionPerformed
        // TODO add your handling code here:
        String NoTransaksi =txtNoTransaksi.getText();
        String tampilan ="yyyy-MM-dd" ; 
        SimpleDateFormat fm = new SimpleDateFormat(tampilan); 
        String Tanggal = String.valueOf(fm.format(DateTanggal.getDate()));
        
        String NamaPembeli=txtNamaPembeli.getText();
        String Harga=txtHarga.getText();
        String Dibayar=txtDibayar.getText();
        String Kembali=txtKembali.getText();
        String JudulBuku=ComboBoxBuku.getSelectedItem().toString();
        String KodeBuku=txtBuku.getText();

        if (NoTransaksi.isEmpty() ) {
            JOptionPane.showMessageDialog(null,"NoTransaksi tidak boleh kosong");
            txtNoTransaksi.requestFocus();
        }else if (NamaPembeli.isEmpty() ) {
            JOptionPane.showMessageDialog(null,"Nama Pembeli Buku tidak boleh kosong");
            txtNamaPembeli.requestFocus();
        }else if (Harga.isEmpty() ) {
            JOptionPane.showMessageDialog(null,"Harga tidak boleh kosong");
            txtNamaPembeli.requestFocus();
        }else if (Kembali.isEmpty() ) {
            JOptionPane.showMessageDialog(null,"Kembali tidak boleh kosong");
            txtKembali.requestFocus();
        }else if (Integer.parseInt(txtKembali.getText()) <  0 ) {
            JOptionPane.showMessageDialog(null,"Kembali tidak boleh minus");
            txtKembali.requestFocus();
        }else if (Dibayar.isEmpty() ) {
            JOptionPane.showMessageDialog(null,"Dibayar tidak boleh kosong");
            txtDibayar.requestFocus();
        }else if (JudulBuku.isEmpty() ) {
            JOptionPane.showMessageDialog(null,"Jenis Buku tidak boleh kosong");
            ComboBoxBuku.requestFocus();
        }else{
            try{
                Connection conn=(Connection)koneksi.koneksiDB();
                Statement stt=conn.createStatement();
                stt.executeUpdate("insert into tbl_transaksi(no_transaksi,tanggal,nama_pembeli,harga,dibayar,kembali,kode_buku)"+
                    "VALUES('"+NoTransaksi+"','"+Tanggal+"','"+NamaPembeli+"','"+Harga+"','"+Dibayar+"','"+Kembali+"','"+KodeBuku+"')");
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
        String NoTransaksi =txtNoTransaksi.getText();
        String NamaPembeli=txtNamaPembeli.getText();

        if (NoTransaksi.isEmpty() ) {
            JOptionPane.showMessageDialog(null,"NoTransaksi tidak boleh kosong");
            txtNoTransaksi.requestFocus();
        }else if(JOptionPane.showConfirmDialog(null,"Apakah anda yakin akan menghapus data ini?",
            "Informasi",JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE)==JOptionPane.OK_OPTION){
        try{
            Connection conn=(Connection)koneksi.koneksiDB();
            Statement stt=conn.createStatement();
            stt.executeUpdate("DELETE FROM tbl_transaksi WHERE no_transaksi='"+NoTransaksi+"'");
            BersihData();
            tampilData();
            SetEditOff();
            JOptionPane.showMessageDialog(this,"Data berhasil di hapus","Success",JOptionPane.INFORMATION_MESSAGE);
        } catch(SQLException e){
            JOptionPane.showMessageDialog(this,"Delete data gagal\n"+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
        }
    }//GEN-LAST:event_btndeleteActionPerformed

    private void btnupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateActionPerformed
        // TODO add your handling code here:
        String NoTransaksi =txtNoTransaksi.getText();
        String tampilan ="yyyy-MM-dd" ; 
        SimpleDateFormat fm = new SimpleDateFormat(tampilan); 
        String Tanggal = String.valueOf(fm.format(DateTanggal.getDate()));
        
        String NamaPembeli=txtNamaPembeli.getText();
        String Harga=txtHarga.getText();
        String Dibayar=txtDibayar.getText();
        String Kembali=txtKembali.getText();
        String JudulBuku=ComboBoxBuku.getSelectedItem().toString();
        String KodeBuku=txtBuku.getText();

        if (NoTransaksi.isEmpty() ) {
            JOptionPane.showMessageDialog(null,"NoTransaksi tidak boleh kosong");
            txtNoTransaksi.requestFocus();
        }else if (NamaPembeli.isEmpty() ) {
            JOptionPane.showMessageDialog(null,"Nama Pembeli Buku tidak boleh kosong");
            txtNamaPembeli.requestFocus();
        }else if (Harga.isEmpty() ) {
            JOptionPane.showMessageDialog(null,"Harga tidak boleh kosong");
            txtNamaPembeli.requestFocus();
        }else if (Kembali.isEmpty() ) {
            JOptionPane.showMessageDialog(null,"Kembali tidak boleh kosong");
            txtKembali.requestFocus();
        }else if (Integer.parseInt(txtKembali.getText()) <  0 ) {
            JOptionPane.showMessageDialog(null,"Kembali tidak boleh minus");
            txtKembali.requestFocus();
        }else if (Dibayar.isEmpty() ) {
            JOptionPane.showMessageDialog(null,"Dibayar tidak boleh kosong");
            txtDibayar.requestFocus();
        }else if (JudulBuku.isEmpty() ) {
            JOptionPane.showMessageDialog(null,"Jenis Buku tidak boleh kosong");
            ComboBoxBuku.requestFocus();
        }else{
            try{
                Connection conn=(Connection)koneksi.koneksiDB();
                Statement stt=conn.createStatement();
                stt.executeUpdate("UPDATE tbl_transaksi SET tanggal='"+Tanggal+"', nama_pembeli='"+NamaPembeli+"', harga='"+Harga+"', dibayar='"+Dibayar+"', kode_buku='"+KodeBuku+"' WHERE no_transaksi='"+NoTransaksi+"'");
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

    private void txtBukuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBukuKeyPressed

    }//GEN-LAST:event_txtBukuKeyPressed

    private void txtDibayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDibayarKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtDibayarKeyPressed

    private void txtDibayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDibayarKeyReleased
        // TODO add your handling code here:
                // Membuat Variabel
        int nilai1;
        int nilai2;
        int nilai3;
        String Hasil;
        /** mengambil nilai String d jTextField dan diubah ke
        * tipe Integer dan ditampung divariabel nilai1,
        * nilai2, variabel nilai3 digunakan untuk menampung
        * hasil penjumlahan nilai1 dan nilai2
        */
        nilai1=Integer.valueOf(txtDibayar.getText());
        nilai2=Integer.valueOf(txtHarga.getText());
        nilai3=nilai1 - nilai2;
        /** mengubah tipe data ke String agar dapat
        * ditampilkan kembali pada Form
        */
        Hasil=String.valueOf(nilai3);
        txtKembali.setText(Hasil);
    }//GEN-LAST:event_txtDibayarKeyReleased

    private void txtHargaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHargaKeyReleased
        // TODO add your handling code here:
                // Membuat Variabel
        int nilai1;
        int nilai2;
        int nilai3;
        String Hasil;
        /** mengambil nilai String d jTextField dan diubah ke
        * tipe Integer dan ditampung divariabel nilai1,
        * nilai2, variabel nilai3 digunakan untuk menampung
        * hasil penjumlahan nilai1 dan nilai2
        */
        nilai1=Integer.valueOf(txtDibayar.getText());
        nilai2=Integer.valueOf(txtHarga.getText());
        nilai3=nilai1 - nilai2;
        /** mengubah tipe data ke String agar dapat
        * ditampilkan kembali pada Form
        */
        Hasil=String.valueOf(nilai3);
        txtKembali.setText(Hasil);
    }//GEN-LAST:event_txtHargaKeyReleased

    private void TabelTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelTransaksiMouseClicked
        // TODO add your handling code here:       
        seteditOn();
        try {
            //        txtNoTransaksi.setEnabled(true);
            tblKeForm();
        } catch (ParseException ex) {
            Logger.getLogger(Transaksi.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_TabelTransaksiMouseClicked

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
                                               

    private void ComboBoxNJudulBukuActionPerformed(java.awt.event.ActionEvent evt) throws SQLException {                                                   
        // TODO add your handling code here:
        String text;
        text = (String)ComboBoxBuku.getSelectedItem();
        Connection conn=(Connection)koneksi.koneksiDB();
        try {
            String query = "SELECT * FROM tbl_buku WHERE judul_buku ='"+text+"'";
            Statement st =conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                txtBuku.setText(rs.getString("kode_buku"));
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }                                                  
                               

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboBoxBuku;
    private com.toedter.calendar.JDateChooser DateTanggal;
    private javax.swing.JTable TabelTransaksi;
    private javax.swing.JButton btncancel;
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btnsimpan;
    private javax.swing.JButton btntambah;
    private javax.swing.JButton btnupdate;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtBuku;
    private javax.swing.JTextField txtDibayar;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtKembali;
    private javax.swing.JTextField txtNamaPembeli;
    private javax.swing.JTextField txtNoTransaksi;
    private javax.swing.JTextField txtcari;
    // End of variables declaration//GEN-END:variables
}
