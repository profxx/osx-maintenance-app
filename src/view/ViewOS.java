/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package view;

import java.sql.*;
import DAO.ModuloConexao;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class ViewOS extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    // Armazenar variável do radioButton
    private String tipo;

    /**
     * Creates new form ViewOS
     */
    public ViewOS() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    private void pesquisar_os_clientes() {
        String sql = "SELECT pk_os_id as OS, os_equip as EQ, date_format(os_data, '%d/%m/%Y') as DATA, os_situacao as STAT FROM tbl_os WHERE fk_cli_id = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, jtfId.getText());
            rs = pst.executeQuery();
            jtOS.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void pesquisar_cliente() {
        String sql = "SELECT pk_cli_id as ID, cli_nome as Nome, cli_fone as Contato FROM tbl_cliente WHERE cli_nome LIKE ? ORDER BY cli_nome";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, jtfPesquisaCli.getText() + "%");
            rs = pst.executeQuery();
            jtCli.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void pesquisar_os() {
        String num_os = JOptionPane.showInputDialog("Nº da OS: ");
        String sql = "SELECT pk_os_id, date_format(os_data, '%d/%m/%Y - %H:%i'), os_tipo, os_situacao, os_equip, os_defeito, os_serv, os_tecnico, os_valor, fk_cli_id FROM tbl_os WHERE pk_os_id=" + num_os;
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                jtfOS.setText(rs.getString(1));
                jtfData.setText(rs.getString(2));
                // Setando RADIO BUTTONS
                String jrbText = rs.getString(3);
                if (jrbText.equals("OS")) {
                    jrbOS.setSelected(true);
                    tipo = "OS";
                } else {
                    jrbOrcamento.setSelected(true);
                    tipo = "Orçamento";
                }
                jcbSituacao.setSelectedItem(rs.getString(4));
                jtfEquip.setText(rs.getString(5));
                jtfDefeito.setText(rs.getString(6));
                jtfServico.setText(rs.getString(7));
                jtfTecnico.setText(rs.getString(8));
                jtfValor.setText(rs.getString(9));
                jtfId.setText(rs.getString(10));

                // Evitando problemas
                // Desabilitar botões
                jbAddOS.setEnabled(false);
                jtfPesquisaCli.setEnabled(false);
                // Esvaziar tabela de Clientes
                ((DefaultTableModel) jtCli.getModel()).setRowCount(0);
                // Ativar esses botões
                jbSaveOS.setEnabled(true);
                jbPrintOS.setEnabled(true);
                jbRemoveOS.setEnabled(true);

            } else {
                JOptionPane.showMessageDialog(null, "OS não cadastrada");
            }
        } catch (SQLSyntaxErrorException e) {
            JOptionPane.showMessageDialog(null, "OS Inválida.");
        } catch (Exception e2) {
            JOptionPane.showMessageDialog(null, e2);
        }
    }

    private void preencher_os_clientes() {
        int selecionada = jtOS.getSelectedRow();
        String num_os = jtOS.getModel().getValueAt(selecionada, 0).toString();
        String sql = "SELECT pk_os_id, date_format(os_data, '%d/%m/%Y - %H:%i'), os_tipo, os_situacao, os_equip, os_defeito, os_serv, os_tecnico, os_valor, fk_cli_id FROM tbl_os WHERE pk_os_id=" + num_os;
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                jtfOS.setText(rs.getString(1));
                jtfData.setText(rs.getString(2));
                // Setando RADIO BUTTONS
                String jrbText = rs.getString(3);
                if (jrbText.equals("OS")) {
                    jrbOS.setSelected(true);
                    tipo = "OS";
                } else {
                    jrbOrcamento.setSelected(true);
                    tipo = "Orçamento";
                }
                jcbSituacao.setSelectedItem(rs.getString(4));
                jtfEquip.setText(rs.getString(5));
                jtfDefeito.setText(rs.getString(6));
                jtfServico.setText(rs.getString(7));
                jtfTecnico.setText(rs.getString(8));
                jtfValor.setText(rs.getString(9));
                jtfId.setText(rs.getString(10));

                // Evitando problemas
                // Desabilitar botões
                jbAddOS.setEnabled(false);
                jtfPesquisaCli.setEnabled(false);
                // Esvaziar tabela de Clientes
                ((DefaultTableModel) jtCli.getModel()).setRowCount(0);
                // Ativar esses botões
                jbSaveOS.setEnabled(true);
                jbPrintOS.setEnabled(true);
                jbRemoveOS.setEnabled(true);

            } else {
                JOptionPane.showMessageDialog(null, "OS não cadastrada");
            }
        } catch (SQLSyntaxErrorException e) {
            JOptionPane.showMessageDialog(null, "OS Inválida.");
        } catch (Exception e2) {
            JOptionPane.showMessageDialog(null, e2);
        }
    }

    private void setar_campos() {
        int setar = jtCli.getSelectedRow();
        jtfId.setText(jtCli.getModel().getValueAt(setar, 0).toString());
    }

    private void recuperar_os(){
        String sql = "SELECT max(pk_os_id) FROM tbl_os";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()){
                jtfOS.setText(rs.getString(1));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void limpar_campos() { // Limpar os campos e gerenciar os botões
        jrbOrcamento.setSelected(true);
        jcbSituacao.setSelectedItem(" ");
        jtfId.setText(null);
        jtfPesquisaCli.setText(null);
        jtfEquip.setText(null);
        jtfDefeito.setText(null);
        jtfServico.setText(null);
        jtfTecnico.setText(null);
        jtfValor.setText(null);
        jtfOS.setText(null);
        jtfData.setText(null);
        ((DefaultTableModel) jtCli.getModel()).setRowCount(0);
        ((DefaultTableModel) jtOS.getModel()).setRowCount(0);
        // Desabilitar os botões
        jbPrintOS.setEnabled(false);
        jbRemoveOS.setEnabled(false);
        jbSaveOS.setEnabled(false);
        // Habilitar os objetos
        jbPesquisarOS.setEnabled(true);
        jbAddOS.setEnabled(true);
        jtCli.setVisible(true);
        jtfPesquisaCli.setEnabled(true);

    }

    private void emitir_os() {
        String sql = "INSERT INTO tbl_os (os_tipo, os_situacao, os_equip, os_defeito, os_serv, os_tecnico, os_valor,fk_cli_id) values (?,?,?,?,?,?,?,?);";
        if (jtfEquip.getText().isEmpty() || jtfDefeito.getText().isEmpty() || jtfId.getText().isEmpty() || jcbSituacao.getSelectedItem().equals(" ")) {
            JOptionPane.showMessageDialog(null, "Todos os campos com * devem ser preenchidos.");
        } else {
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, tipo);
                pst.setString(2, jcbSituacao.getSelectedItem().toString());
                pst.setString(3, jtfEquip.getText());
                pst.setString(4, jtfDefeito.getText());
                pst.setString(5, jtfServico.getText());
                pst.setString(6, jtfTecnico.getText());
                pst.setString(7, jtfValor.getText().replace(",", "."));
                pst.setString(8, jtfId.getText());
                pst.executeUpdate(); //Commit no SQL
                JOptionPane.showMessageDialog(null, "OS cadastrada com sucesso.");
                recuperar_os();
                jbAddOS.setEnabled(false);
                jbPesquisarOS.setEnabled(false);
                jbPrintOS.setEnabled(true);
                //limpar_campos();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    private void alterar_os() {
        String sql = "UPDATE tbl_os SET os_tipo=?, os_situacao=?, os_equip=?, os_defeito=?, os_serv=?, os_tecnico=?, os_valor=? WHERE pk_os_id=?;";
        if (jtfEquip.getText().isEmpty() || jtfDefeito.getText().isEmpty() || jtfId.getText().isEmpty() || jcbSituacao.getSelectedItem().equals(" ")) {
            JOptionPane.showMessageDialog(null, "Todos os campos com * devem ser preenchidos.");
        } else {
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, tipo);
                pst.setString(2, jcbSituacao.getSelectedItem().toString());
                pst.setString(3, jtfEquip.getText());
                pst.setString(4, jtfDefeito.getText());
                pst.setString(5, jtfServico.getText());
                pst.setString(6, jtfTecnico.getText());
                pst.setString(7, jtfValor.getText().replace(",", "."));
                pst.setString(8, jtfOS.getText());
                pst.executeUpdate(); //Commit no SQL
                JOptionPane.showMessageDialog(null, "OS alterada com sucesso.");
                limpar_campos();
                // Habilitar botões
                jbAddOS.setEnabled(true);
                jtCli.setVisible(true);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    private void excluir_os() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM tbl_os WHERE pk_os_id=?;";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, jtfOS.getText());
                pst.executeUpdate(); // Commit no SQL
                JOptionPane.showMessageDialog(null, "OS excluída com sucesso.");
                limpar_campos();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    private void imprimir_os(){
        // Imprimir a OS selecionada
        try {
            // Usando HashMap para criar um filtro baseado no numero da OS
            HashMap filtro = new HashMap();
            filtro.put("os", Integer.parseInt(jtfOS.getText()));
            // Usando JasperPrint para preparar relatório
            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/report/os.jasper"),filtro,conexao);
            JasperViewer.viewReport(print, false);
        } catch (Exception e) {
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jtfOS = new javax.swing.JTextField();
        jtfData = new javax.swing.JTextField();
        jrbOrcamento = new javax.swing.JRadioButton();
        jrbOS = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        jcbSituacao = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jtfPesquisaCli = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jtfId = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtCli = new javax.swing.JTable();
        jbNovoCli = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jtfEquip = new javax.swing.JTextField();
        jtfDefeito = new javax.swing.JTextField();
        jtfServico = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtOS = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jtfTecnico = new javax.swing.JTextField();
        jtfValor = new javax.swing.JTextField();
        jbRemoveOS = new javax.swing.JButton();
        jbPesquisarOS = new javax.swing.JButton();
        jbSaveOS = new javax.swing.JButton();
        jbAddOS = new javax.swing.JButton();
        jbPrintOS = new javax.swing.JButton();

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable2);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Ordem de Serviço");
        setMaximumSize(new java.awt.Dimension(745, 479));
        setMinimumSize(new java.awt.Dimension(745, 479));
        setPreferredSize(new java.awt.Dimension(745, 479));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Nº OS");

        jLabel2.setText("Data");

        jtfOS.setEnabled(false);
        jtfOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfOSActionPerformed(evt);
            }
        });

        jtfData.setEnabled(false);

        buttonGroup1.add(jrbOrcamento);
        jrbOrcamento.setText("Orçamento");
        jrbOrcamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbOrcamentoActionPerformed(evt);
            }
        });

        buttonGroup1.add(jrbOS);
        jrbOS.setText("Ordem de Serviço");
        jrbOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbOSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jtfOS, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jtfData)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jrbOrcamento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jrbOS)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jrbOrcamento)
                    .addComponent(jrbOS))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jLabel3.setText("Situação");

        jcbSituacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Na Bancada", "Entrega OK", "Orçamento REPROVADO", "Aguardando Aprovação", "Aguardando Peças", "Abandonado Pelo Cliente", "Retornou" }));

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jtfPesquisaCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfPesquisaCliKeyReleased(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/PNG/search-icon24.png"))); // NOI18N

        jLabel5.setText("*ID");

        jtfId.setEnabled(false);

        jtCli.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Nome", "Contato"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtCli.getTableHeader().setReorderingAllowed(false);
        jtCli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtCliMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtCli);
        if (jtCli.getColumnModel().getColumnCount() > 0) {
            jtCli.getColumnModel().getColumn(0).setResizable(false);
            jtCli.getColumnModel().getColumn(0).setPreferredWidth(20);
            jtCli.getColumnModel().getColumn(1).setResizable(false);
            jtCli.getColumnModel().getColumn(2).setResizable(false);
            jtCli.getColumnModel().getColumn(2).setPreferredWidth(50);
        }

        jbNovoCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/PNG/add-user-icon24.png"))); // NOI18N
        jbNovoCli.setText("Novo Cliente");
        jbNovoCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbNovoCliActionPerformed(evt);
            }
        });

        jLabel12.setText("Clientes");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfPesquisaCli, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbNovoCli)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfId)))
                .addGap(19, 19, 19))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfPesquisaCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jbNovoCli)
                                .addComponent(jLabel5)
                                .addComponent(jtfId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel6.setText("*Equipamento");

        jLabel7.setText("*Defeito");

        jLabel8.setText("Serviço");

        jLabel9.setText("Técnico");

        jLabel10.setText("Valor Total");

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jtOS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nº OS", "Equipamento", "Data", "Situação"
            }
        ));
        jtOS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtOSMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jtOS);
        if (jtOS.getColumnModel().getColumnCount() > 0) {
            jtOS.getColumnModel().getColumn(0).setMinWidth(50);
            jtOS.getColumnModel().getColumn(0).setPreferredWidth(50);
            jtOS.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        jLabel11.setText("Ordens de Serviços do Cliente");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel11)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(69, Short.MAX_VALUE))
        );

        jtfValor.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtfValor.setText("0,00");

        jbRemoveOS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/PNG/remove48.png"))); // NOI18N
        jbRemoveOS.setToolTipText("Apagar OS");
        jbRemoveOS.setBorderPainted(false);
        jbRemoveOS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbRemoveOS.setEnabled(false);
        jbRemoveOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRemoveOSActionPerformed(evt);
            }
        });

        jbPesquisarOS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/PNG/Search48a.png"))); // NOI18N
        jbPesquisarOS.setToolTipText("Busca OS");
        jbPesquisarOS.setBorderPainted(false);
        jbPesquisarOS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbPesquisarOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbPesquisarOSActionPerformed(evt);
            }
        });

        jbSaveOS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/PNG/Save48a.png"))); // NOI18N
        jbSaveOS.setToolTipText("Altera OS");
        jbSaveOS.setBorderPainted(false);
        jbSaveOS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbSaveOS.setEnabled(false);
        jbSaveOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSaveOSActionPerformed(evt);
            }
        });

        jbAddOS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/PNG/add48.png"))); // NOI18N
        jbAddOS.setToolTipText("Nova OS");
        jbAddOS.setBorderPainted(false);
        jbAddOS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbAddOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAddOSActionPerformed(evt);
            }
        });

        jbPrintOS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/PNG/print-icon48.png"))); // NOI18N
        jbPrintOS.setToolTipText("Imprimir OS");
        jbPrintOS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbPrintOS.setEnabled(false);
        jbPrintOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbPrintOSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel3)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcbSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jtfTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jtfValor, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE))
                                    .addComponent(jtfEquip)
                                    .addComponent(jtfDefeito)
                                    .addComponent(jtfServico))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jbRemoveOS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbPesquisarOS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbSaveOS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbAddOS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbPrintOS)
                        .addGap(14, 14, 14)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jcbSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jtfEquip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jtfDefeito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jtfServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jtfTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbAddOS)
                        .addComponent(jbSaveOS)
                        .addComponent(jbPesquisarOS)
                        .addComponent(jbRemoveOS))
                    .addComponent(jbPrintOS))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        setBounds(0, 0, 745, 479);
    }// </editor-fold>//GEN-END:initComponents

    private void jtfOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfOSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfOSActionPerformed

    private void jrbOrcamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbOrcamentoActionPerformed
        // Atribuindo um texto a variável tipo caso seja selecionado
        tipo = "Orçamento";
    }//GEN-LAST:event_jrbOrcamentoActionPerformed

    private void jbNovoCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNovoCliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbNovoCliActionPerformed

    private void jbAddOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAddOSActionPerformed
        // Insere nova OS
        emitir_os();
    }//GEN-LAST:event_jbAddOSActionPerformed

    private void jbSaveOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSaveOSActionPerformed
        // Chama o método alterar OS
        alterar_os();
    }//GEN-LAST:event_jbSaveOSActionPerformed

    private void jtfPesquisaCliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfPesquisaCliKeyReleased
        // Chamando o método pesquisar clientes
        pesquisar_cliente();
    }//GEN-LAST:event_jtfPesquisaCliKeyReleased

    private void jtCliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtCliMouseClicked
        // Setar o valor do ID no campo ID
        setar_campos();
        pesquisar_os_clientes();
    }//GEN-LAST:event_jtCliMouseClicked

    private void jrbOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbOSActionPerformed
        // Atribui o texto a variável tipo caso selecionado
        tipo = "OS";
    }//GEN-LAST:event_jrbOSActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // Ao abrir o form marcar o radio button orçamento
        jrbOrcamento.setSelected(true);
        tipo = "Orçamento";
    }//GEN-LAST:event_formInternalFrameOpened

    private void jbPesquisarOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPesquisarOSActionPerformed
        // Chamar o método pesquisar_os
        pesquisar_os();
    }//GEN-LAST:event_jbPesquisarOSActionPerformed

    private void jbRemoveOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRemoveOSActionPerformed
        // Chamar o método excluir_os
        excluir_os();
    }//GEN-LAST:event_jbRemoveOSActionPerformed

    private void jtOSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtOSMouseClicked
        // Preencher os dados da OS conforme selecionado pelo nome do cliente
        preencher_os_clientes();
    }//GEN-LAST:event_jtOSMouseClicked

    private void jbPrintOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPrintOSActionPerformed
        // Imprime a OS
        imprimir_os();
    }//GEN-LAST:event_jbPrintOSActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable2;
    private javax.swing.JButton jbAddOS;
    private javax.swing.JButton jbNovoCli;
    private javax.swing.JButton jbPesquisarOS;
    private javax.swing.JButton jbPrintOS;
    private javax.swing.JButton jbRemoveOS;
    private javax.swing.JButton jbSaveOS;
    private javax.swing.JComboBox<String> jcbSituacao;
    private javax.swing.JRadioButton jrbOS;
    private javax.swing.JRadioButton jrbOrcamento;
    private javax.swing.JTable jtCli;
    private javax.swing.JTable jtOS;
    private javax.swing.JTextField jtfData;
    private javax.swing.JTextField jtfDefeito;
    private javax.swing.JTextField jtfEquip;
    private javax.swing.JTextField jtfId;
    private javax.swing.JTextField jtfOS;
    private javax.swing.JTextField jtfPesquisaCli;
    private javax.swing.JTextField jtfServico;
    private javax.swing.JTextField jtfTecnico;
    private javax.swing.JTextField jtfValor;
    // End of variables declaration//GEN-END:variables
}
