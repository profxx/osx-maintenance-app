/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package view;

import DAO.ModuloConexao;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
// Importa recursos da rs2xml.jar baixado na sourceforge - findangelsanddemons
import net.proteanit.sql.DbUtils;

/**
 *
 * @author acpim
 */
public class ViewCliente extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form ViewCliente
     */
    public ViewCliente() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    private void limpar_tela() {
        jtfNomeCli.setText(null);
        jtfEndCli.setText(null);
        jtfFoneCli.setText(null);
        jtfEmailCli.setText(null);
        jtfId.setText(null);
        ((DefaultTableModel) jtListaCli.getModel()).setRowCount(0);
    }

    private void adicionar() {
        String sql = "INSERT INTO tbl_cliente (cli_nome, cli_end, cli_fone, cli_email) VALUES (?,?,?,?)";
        if (jtfNomeCli.getText().isEmpty() || jtfEndCli.getText().isEmpty() || jtfFoneCli.getText().isEmpty() || jtfEmailCli.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos os campos deverão ser preenchidos.");
        } else {
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, jtfNomeCli.getText());
                pst.setString(2, jtfEndCli.getText());
                pst.setString(3, jtfFoneCli.getText());
                pst.setString(4, jtfEmailCli.getText());
                // Commit do SQL
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso.");
                limpar_tela();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    private void pesquisar_cliente() {
        String sql = "SELECT * FROM tbl_cliente WHERE cli_nome LIKE ? ORDER BY cli_nome";
        try {
            pst = conexao.prepareStatement(sql);
            // Colocar a "%" para filtrar a pesquisa no SQL acima
            pst.setString(1, jtfPesquisaCli.getText() + "%");
            rs = pst.executeQuery();
            // A linha abaixo é pra usar rs2xml.jar para preencher a tabela
            jtListaCli.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // Método para setar os campos com os elementos da tabela jtListaCli
    public void setar_campos() {
        int setar = jtListaCli.getSelectedRow();
        jtfNomeCli.setText(jtListaCli.getModel().getValueAt(setar, 1).toString());
        jtfEndCli.setText(jtListaCli.getModel().getValueAt(setar, 2).toString());
        jtfFoneCli.setText(jtListaCli.getModel().getValueAt(setar, 4).toString());
        jtfEmailCli.setText(jtListaCli.getModel().getValueAt(setar, 3).toString());
        jtfId.setText(jtListaCli.getModel().getValueAt(setar, 0).toString());
        //Desabilita o btn add
        jbAdd.setEnabled(false);
    }

    private void alterar() {
        String sql = "UPDATE tbl_cliente SET cli_nome=?, cli_end=?, cli_fone=?, cli_email=? WHERE pk_cli_id=?";
        try {
            if (jtfNomeCli.getText() == null || jtfFoneCli.getText() == null || jtfEndCli.getText() == null || jtfEmailCli.getText() == null) {
                JOptionPane.showMessageDialog(null, "Todos os campos precisam ser preenchidos.");
            } else {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, jtfNomeCli.getText());
                pst.setString(2, jtfEndCli.getText());
                pst.setString(3, jtfFoneCli.getText());
                pst.setString(4, jtfEmailCli.getText());
                pst.setString(5, jtfId.getText());

                limpar_tela();
                pst.executeUpdate(); // Commit do BD
                jbAdd.setEnabled(true);
                JOptionPane.showMessageDialog(null, "Alterado com sucesso.");
            }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    // Método para remover um cliente
    private void remover() {
        int confirma = JOptionPane.showConfirmDialog(null, "Realmente excluir o cliente?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM tbl_cliente WHERE pk_cli_id=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, jtfId.getText());
                pst.executeUpdate(); //Commita o bd
                limpar_tela();
                JOptionPane.showMessageDialog(null, "Cliente removido.");
            } catch (SQLException e){
                JOptionPane.showMessageDialog(null, e);
            }
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jtfNomeCli = new javax.swing.JTextField();
        jtfEndCli = new javax.swing.JTextField();
        jtfFoneCli = new javax.swing.JTextField();
        jtfEmailCli = new javax.swing.JTextField();
        jbRemove = new javax.swing.JButton();
        jbSave = new javax.swing.JButton();
        jbAdd = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtListaCli = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jtfPesquisaCli = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jtfId = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Clientes");
        setMaximumSize(new java.awt.Dimension(745, 479));
        setMinimumSize(new java.awt.Dimension(745, 479));
        setPreferredSize(new java.awt.Dimension(745, 479));

        jLabel1.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N
        jLabel1.setText("*Nome");

        jLabel2.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N
        jLabel2.setText("*Endereço");

        jLabel3.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N
        jLabel3.setText("*Telefone");

        jLabel4.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N
        jLabel4.setText("*E-mail");

        jtfNomeCli.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N
        jtfNomeCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfNomeCliActionPerformed(evt);
            }
        });

        jtfEndCli.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N

        jtfFoneCli.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N

        jtfEmailCli.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N

        jbRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/PNG/remove-user-icon64.png"))); // NOI18N
        jbRemove.setActionCommand("jbRemove");
        jbRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRemoveActionPerformed(evt);
            }
        });

        jbSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/PNG/save64.png"))); // NOI18N
        jbSave.setActionCommand("jbSave");
        jbSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSaveActionPerformed(evt);
            }
        });

        jbAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/PNG/add-user-icon64.png"))); // NOI18N
        jbAdd.setActionCommand("jbAdd");
        jbAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAddActionPerformed(evt);
            }
        });

        jtListaCli.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N
        jtListaCli.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "Endereço", "Telefone", "Email"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtListaCli.getTableHeader().setReorderingAllowed(false);
        jtListaCli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtListaCliMouseClicked(evt);
            }
        });
        jtListaCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtListaCliKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jtListaCli);
        if (jtListaCli.getColumnModel().getColumnCount() > 0) {
            jtListaCli.getColumnModel().getColumn(0).setMinWidth(30);
            jtListaCli.getColumnModel().getColumn(0).setPreferredWidth(30);
            jtListaCli.getColumnModel().getColumn(0).setMaxWidth(30);
            jtListaCli.getColumnModel().getColumn(2).setPreferredWidth(80);
            jtListaCli.getColumnModel().getColumn(3).setPreferredWidth(15);
            jtListaCli.getColumnModel().getColumn(4).setPreferredWidth(80);
        }

        jLabel5.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/PNG/search-icon24.png"))); // NOI18N

        jtfPesquisaCli.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N
        jtfPesquisaCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfPesquisaCliKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfPesquisaCliKeyReleased(evt);
            }
        });

        jLabel6.setForeground(new java.awt.Color(204, 0, 0));
        jLabel6.setText("*Campos obrigatórios");

        jLabel7.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N
        jLabel7.setText("ID");

        jtfId.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N
        jtfId.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jtfFoneCli, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfEmailCli))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jtfPesquisaCli, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel5)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jtfEndCli)))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jbRemove)
                .addGap(41, 41, 41)
                .addComponent(jbSave)
                .addGap(36, 36, 36)
                .addComponent(jbAdd)
                .addGap(44, 44, 44))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 709, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jtfNomeCli, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtfId, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jtfPesquisaCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtfNomeCli)
                    .addComponent(jLabel7)
                    .addComponent(jtfId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtfEndCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfFoneCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jtfEmailCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbRemove)
                    .addComponent(jbSave)
                    .addComponent(jbAdd))
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtfNomeCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfNomeCliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfNomeCliActionPerformed

    private void jbAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAddActionPerformed
        // Adiciona um novo cliente
        adicionar();
    }//GEN-LAST:event_jbAddActionPerformed
    // O Evento abaixo é do tipo "Enquanto for digitando" 
    private void jtfPesquisaCliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfPesquisaCliKeyReleased
        // Chamar o método pesquisar clientes
        pesquisar_cliente();
    }//GEN-LAST:event_jtfPesquisaCliKeyReleased
    // Evento para setar os campos da tabela clicando com o mouse
    private void jtListaCliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtListaCliMouseClicked
        // Chamar o método para setar campos
        setar_campos();
    }//GEN-LAST:event_jtListaCliMouseClicked
    // Evento para eu adicionar a seta para setar os campos da tabela tb
    private void jtListaCliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtListaCliKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode() == KeyEvent.VK_UP){
            setar_campos();
        }
    }//GEN-LAST:event_jtListaCliKeyReleased

    private void jtfPesquisaCliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfPesquisaCliKeyPressed
        // Tentativa frustrada de mudar para o jtListaCli
        
    }//GEN-LAST:event_jtfPesquisaCliKeyPressed

    private void jbSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSaveActionPerformed
        // Chamando o método altrerar cliente
        alterar();
    }//GEN-LAST:event_jbSaveActionPerformed

    private void jbRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRemoveActionPerformed
        // Remove o cliente
        remover();
    }//GEN-LAST:event_jbRemoveActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbAdd;
    private javax.swing.JButton jbRemove;
    private javax.swing.JButton jbSave;
    private javax.swing.JTable jtListaCli;
    private javax.swing.JTextField jtfEmailCli;
    private javax.swing.JTextField jtfEndCli;
    private javax.swing.JTextField jtfFoneCli;
    private javax.swing.JTextField jtfId;
    private javax.swing.JTextField jtfNomeCli;
    private javax.swing.JTextField jtfPesquisaCli;
    // End of variables declaration//GEN-END:variables
}
