/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kesiswaan;
import guru.*;
import java.sql.Connection;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import koneksi.koneksi;

/**
 *
 * @author User
 */
public class homedashboardguru extends javax.swing.JInternalFrame {
    private Connection conn = new koneksi().getConnection();

    /**
     * Creates new form homedashboardtu
     */
    public homedashboardguru() {
        initComponents();
        
        tkelas.setText(("DIAGRAM KELAS ") + GuruSession.getKelas());
        
        // Panggil method ini untuk mengisi 4 kotak ringkasan di atas
        tampilkanAngkaRingkasan();
        
        // Catatan Imhotep: Jika 4 panel di atas diisi angka, diagram pie sebaiknya
        // dipindah ke panel lain yang lebih besar di area bawah dashboard.
        // tampilkanDiagramSiswa(); 
        // tampilkanDiagramAbsensi(); 
    }
    
    
    public void tampilkanAngkaRingkasan() {
    try {
        if (this.conn == null || this.conn.isClosed()) {
            this.conn = new koneksi().getConnection(); 
        }
        
        String kg = GuruSession.getKelas();
        
        // 1. Menghitung Total Siswa berdasarkan Kelas
        if (kg != null && !kg.trim().isEmpty()) {
            String sqlSiswa = "SELECT COUNT(*) as total FROM tbl_siswa s JOIN tbl_kelas k ON s.kelas = k.id_kelas WHERE k.kelas = ?";
            java.sql.PreparedStatement ps = conn.prepareStatement(sqlSiswa);
            ps.setString(1, kg);
            java.sql.ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                int totalSiswa = rs.getInt("total");
                // Mengubah teks pada label yang kamu buat di tab Design
                lbl_angkaSiswa.setText(totalSiswa + " Siswa"); 
            }
        }
        
        // 2. Menghitung Total Pelanggaran (Contoh, sesuaikan dengan nama tabel aslimu)
        /*
        String sqlKasus = "SELECT COUNT(*) as total_kasus FROM tbl_pelanggaran WHERE kelas = ?";
        java.sql.PreparedStatement psKasus = conn.prepareStatement(sqlKasus);
        psKasus.setString(1, kg);
        java.sql.ResultSet rsKasus = psKasus.executeQuery();
        if (rsKasus.next()) {
             lblAngkaPelanggaran.setText(rsKasus.getInt("total_kasus") + " Kasus");
        }
        */
        
        // Lakukan hal yang sama (query COUNT) untuk Agenda dan Kasus Aktif...

    } catch (Exception e) {
        System.err.println("Gagal load data angka ringkasan: " + e.getMessage());
    }
}

//public void tampilkanDiagramAbsensi() {
//    DefaultPieDataset dataset = new DefaultPieDataset();
//
//    try {
//        if (this.conn == null || this.conn.isClosed()) {
//            this.conn = new koneksi().getConnection(); 
//        }
//        
//        String kg = GuruSession.getKelas();
//        
//        if (kg != null && !kg.trim().isEmpty()) {
//            String sql = "SELECT a.status_hadir, COUNT(*) as total " +
//                         "FROM tbl_absen a " +
//                         "JOIN tbl_siswa s ON a.nisn = s.nisn " +
//                         "JOIN tbl_kelas k ON s.kelas = k.id_kelas " +
//                         "WHERE k.kelas = ? " +
//                         "GROUP BY a.status_hadir";
//                         
//            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setString(1, kg);
//            java.sql.ResultSet res = ps.executeQuery();
//
//            int totalHadir = 0;
//            int totalTidakHadir = 0;
//
//            while (res.next()) {
//                int statusAbsen = res.getInt("status_hadir"); 
//                int total = res.getInt("total");
//
//                if (statusAbsen == 1) {
//                    totalHadir += total;
//                } else {
//                    totalTidakHadir += total;
//                }
//            }
//
//            if (totalHadir == 0 && totalTidakHadir == 0) {
//                dataset.setValue("Belum ada data absensi", 1); 
//            } else {
//                if (totalHadir > 0) dataset.setValue("Hadir (" + totalHadir + ")", totalHadir);
//                if (totalTidakHadir > 0) dataset.setValue("Tidak Hadir (" + totalTidakHadir + ")", totalTidakHadir);
//            }
//
//            JFreeChart chart = ChartFactory.createPieChart(null, dataset, true, true, false);
//
//            chart.setBackgroundPaint(new java.awt.Color(255, 255, 255, 255));
//            chart.getLegend().setBackgroundPaint(new java.awt.Color(255, 255, 255, 255));
//            chart.getLegend().setItemPaint(java.awt.Color.BLACK); 
//
//            PiePlot plot = (PiePlot) chart.getPlot();
//            plot.setBackgroundPaint(null);
//            plot.setOutlineVisible(false);
//            plot.setShadowPaint(null);
//
//            for (Object key : dataset.getKeys()) {
//                String label = key.toString().toLowerCase();
//                
//                if (label.contains("tidak hadir")) {
//                    plot.setSectionPaint((Comparable) key, new java.awt.Color(231, 76, 60));  
//                } else if (label.contains("hadir")) {
//                    plot.setSectionPaint((Comparable) key, new java.awt.Color(46, 204, 113)); 
//                } else {
//                    plot.setSectionPaint((Comparable) key, new java.awt.Color(189, 195, 199)); 
//                }
//            }
//
//            plot.setLabelPaint(java.awt.Color.BLACK);
//            plot.setLabelFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
//            plot.setLabelBackgroundPaint(new java.awt.Color(0, 0, 0, 0));
//            plot.setLabelOutlinePaint(null);
//
//            ChartPanel chartPanel = new ChartPanel(chart);
//            chartPanel.setPreferredSize(new java.awt.Dimension(pnlAbsen.getWidth(), pnlAbsen.getHeight()));
//            chartPanel.setOpaque(false);
//            
//            pnlAbsen.removeAll();
//            pnlAbsen.setLayout(new java.awt.BorderLayout());
//            pnlAbsen.add(chartPanel, java.awt.BorderLayout.CENTER);
//            pnlAbsen.validate();
//            
//        }
//
//    } catch (Exception e) {
//        System.err.println("Gagal load diagram absensi: " + e.getMessage());
//        e.printStackTrace();
//    }
//}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        tkelas = new javax.swing.JLabel();
        panelDiagram = new javax.swing.JPanel();
        lbl_angkaSiswa = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        panelDiagram1 = new javax.swing.JPanel();
        lbl_angkaKasus = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        panelDiagram2 = new javax.swing.JPanel();
        lbl_angkaAgenda = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        panelDiagram3 = new javax.swing.JPanel();
        lbl_angkaPelanggaran = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);

        jLabel3.setFont(new java.awt.Font("Segoe UI Symbol", 3, 12)); // NOI18N
        jLabel3.setText("SISWA TERDATA");

        tkelas.setFont(new java.awt.Font("Segoe UI Symbol", 3, 12)); // NOI18N

        panelDiagram.setPreferredSize(new java.awt.Dimension(400, 300));
        panelDiagram.setLayout(new java.awt.BorderLayout());

        lbl_angkaSiswa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_angkaSiswa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_angkaSiswa.setText("0 Siswa");
        panelDiagram.add(lbl_angkaSiswa, java.awt.BorderLayout.CENTER);

        jLabel4.setFont(new java.awt.Font("Segoe UI Symbol", 3, 12)); // NOI18N
        jLabel4.setText("KASUS AKTIF");

        panelDiagram1.setPreferredSize(new java.awt.Dimension(400, 300));
        panelDiagram1.setLayout(new java.awt.BorderLayout());

        lbl_angkaKasus.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_angkaKasus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_angkaKasus.setText("0 Siswa");
        panelDiagram1.add(lbl_angkaKasus, java.awt.BorderLayout.CENTER);

        jLabel5.setFont(new java.awt.Font("Segoe UI Symbol", 3, 12)); // NOI18N
        jLabel5.setText("AGENDA KONSELING");

        panelDiagram2.setPreferredSize(new java.awt.Dimension(400, 300));
        panelDiagram2.setLayout(new java.awt.BorderLayout());

        lbl_angkaAgenda.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_angkaAgenda.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_angkaAgenda.setText("0 Siswa");
        panelDiagram2.add(lbl_angkaAgenda, java.awt.BorderLayout.CENTER);

        jLabel6.setFont(new java.awt.Font("Segoe UI Symbol", 3, 12)); // NOI18N
        jLabel6.setText("PELANGGARAN BULAN INI");

        panelDiagram3.setPreferredSize(new java.awt.Dimension(400, 300));
        panelDiagram3.setLayout(new java.awt.BorderLayout());

        lbl_angkaPelanggaran.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_angkaPelanggaran.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_angkaPelanggaran.setText("0 Siswa");
        panelDiagram3.add(lbl_angkaPelanggaran, java.awt.BorderLayout.CENTER);

        jLabel7.setFont(new java.awt.Font("Segoe UI Symbol", 3, 18)); // NOI18N
        jLabel7.setText("RINGKASAN HARI INI");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelDiagram, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelDiagram1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelDiagram2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelDiagram3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tkelas, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(624, 624, 624))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelDiagram3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelDiagram2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelDiagram1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelDiagram, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tkelas)
                .addContainerGap(294, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel lbl_angkaAgenda;
    private javax.swing.JLabel lbl_angkaKasus;
    private javax.swing.JLabel lbl_angkaPelanggaran;
    private javax.swing.JLabel lbl_angkaSiswa;
    private javax.swing.JPanel panelDiagram;
    private javax.swing.JPanel panelDiagram1;
    private javax.swing.JPanel panelDiagram2;
    private javax.swing.JPanel panelDiagram3;
    private javax.swing.JLabel tkelas;
    // End of variables declaration//GEN-END:variables
}
